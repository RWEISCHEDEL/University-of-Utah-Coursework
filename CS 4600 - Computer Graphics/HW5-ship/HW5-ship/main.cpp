#include <cstdlib>
#include <cstdio>
#include <cmath>
#include <fstream>
#include <vector>
#include <iostream>
#include <cassert>
#include <random>
#include <algorithm>
#include <Eigen>

using namespace Eigen;

// image background color
Vector3f bgcolor(1.0f, 1.0f, 1.0f);

// lights in the scene
std::vector<Vector3f> lightPositions = { Vector3f(  0.0, 60, 60)
                                       , Vector3f(-60.0, 60, 60)
                                       , Vector3f( 60.0, 60, 60) };

class Sphere
{
public:
	Vector3f center;  // position of the sphere
	float radius;  // sphere radius
	Vector3f surfaceColor; // surface color
	
  Sphere(
		const Vector3f &c,
		const float &r,
		const Vector3f &sc) :
		center(c), radius(r), surfaceColor(sc)
	{
	}

    // line vs. sphere intersection (note: this is slightly different from ray vs. sphere intersection!)
	bool intersect(const Vector3f &rayOrigin, const Vector3f &rayDirection, float &t0, float &t1) const
	{
		Vector3f l = center - rayOrigin;
		float tca = l.dot(rayDirection);
		if (tca < 0) return false;
		float d2 = l.dot(l) - tca * tca;
		if (d2 > (radius * radius)) return false;
        float thc = sqrt(radius * radius - d2);
		t0 = tca - thc;
		t1 = tca + thc;

		return true;
	}
};

// diffuse reflection model
Vector3f diffuse(const Vector3f &L, // direction vector from the point on the surface towards a light source
	const Vector3f &N, // normal at this point on the surface
	const Vector3f &diffuseColor,
	const float kd // diffuse reflection constant
	)
{
	Vector3f resColor = Vector3f::Zero();

	// TODO: implement diffuse shading model

	if (L.dot(N) > 0) {
		resColor = 0.333 * kd * L.dot(N) * diffuseColor;
	}

	return resColor;
}

// Phong reflection model
Vector3f phong(const Vector3f &L, // direction vector from the point on the surface towards a light source
               const Vector3f &N, // normal at this point on the surface
               const Vector3f &V, // direction pointing towards the viewer
               const Vector3f &diffuseColor, 
               const Vector3f &specularColor, 
               const float kd, // diffuse reflection constant
               const float ks, // specular reflection constant
               const float alpha) // shininess constant
{
	Vector3f resColor = Vector3f::Zero();

	Vector3f R = 2 * N * N.dot(L) - L; 

	float max;

	if (R.dot(V) > 0) {
		max = std::pow(R.dot(V), alpha);
	}
	else {
		max = std::pow(0, alpha);
	}

	resColor = diffuse(L, N, diffuseColor, kd) + 0.333 * specularColor * ks * max;

	return resColor;
}

Vector3f trace(
	const Vector3f &rayOrigin,
	const Vector3f &rayDirection,
	const std::vector<Sphere> &spheres)
{
	//Vector3f pixelColor = Vector3f::Zero();
	Vector3f pixelColor = bgcolor;

	Vector3f B = Vector3f::Zero();

	float t0 = INFINITY, t1 = INFINITY;
	float ct0, ct1;
	int closestSphere = -1;

	Vector3f t0Point;

	// TODO: implement ray tracing as described in the homework description
	for (unsigned int s = 0; s < spheres.size(); s++) {

		bool doesIntersect = spheres[s].intersect(rayOrigin, rayDirection, ct0, ct1);

		if (doesIntersect) {
			if (t0 > ct0) {
				t0 = ct0;
				closestSphere = s;
				t0Point = rayOrigin + (t0 * rayDirection);
			}
		}

	}

	float l0, l1;

	for (unsigned int l = 0; l < lightPositions.size(); l++) {

		Vector3f lightVector = lightPositions[l] - t0Point;
		lightVector.normalize();

		bool doesIntersect = false;

		for (unsigned int s = 0; s < spheres.size(); s++) {

			if (spheres[s].intersect(t0Point, lightVector, l0, l1)) {
				doesIntersect = true;
			}
		}
		if (!doesIntersect) {

			// This is for the orginal color image shadowed
			//B += 0.333 * spheres[closestSphere].surfaceColor;

			Vector3f surfaceNorm = t0Point - spheres[closestSphere].center;
			surfaceNorm.normalize();

			// This is for the diffuse 
			//B += diffuse(lightVector, surfaceNorm, spheres[closestSphere].surfaceColor, 1);

			Vector3f rayNorm = rayDirection * -1;
			Vector3f specularColor = Vector3f::Ones();

			// This is for phong shading
			B += phong(lightVector, surfaceNorm, rayNorm, spheres[closestSphere].surfaceColor, specularColor, 1, 3, 100);
		}
	}

	if (closestSphere != -1) {
		pixelColor = B;
	}

	return pixelColor;
}

void render(const std::vector<Sphere> &spheres)
{
  unsigned width = 640;
  unsigned height = 480;
  Vector3f *image = new Vector3f[width * height];
  Vector3f *pixel = image;
  float invWidth  = 1 / float(width);
  float invHeight = 1 / float(height);
  float fov = 30;
  float aspectratio = width / float(height);
	float angle = tan(M_PI * 0.5f * fov / 180.f);
	
	// Trace rays
	for (unsigned y = 0; y < height; ++y) 
	{
		for (unsigned x = 0; x < width; ++x) 
		{
			float rayX = (2 * ((x + 0.5f) * invWidth) - 1) * angle * aspectratio;
			float rayY = (1 - 2 * ((y + 0.5f) * invHeight)) * angle;
			Vector3f rayDirection(rayX, rayY, -1);
			rayDirection.normalize();
			*(pixel++) = trace(Vector3f::Zero(), rayDirection, spheres);
		}
	}
	
	// Save result to a PPM image
	std::ofstream ofs("./render.ppm", std::ios::out | std::ios::binary);
	ofs << "P6\n" << width << " " << height << "\n255\n";
	for (unsigned i = 0; i < width * height; ++i) 
	{
		const float x = image[i](0);
		const float y = image[i](1);
		const float z = image[i](2);

		ofs << (unsigned char)(std::min(float(1), x) * 255) 
			  << (unsigned char)(std::min(float(1), y) * 255) 
			  << (unsigned char)(std::min(float(1), z) * 255);
	}
	
	ofs.close();
	delete[] image;
}

int main(int argc, char **argv)
{
	std::vector<Sphere> spheres;
	// position, radius, surface color
	spheres.push_back(Sphere(Vector3f(0.0, -10004, -20), 10000, Vector3f(0.50, 0.50, 0.50)));
	spheres.push_back(Sphere(Vector3f(0.0, 0, -20), 4, Vector3f(1.00, 0.32, 0.36)));
	spheres.push_back(Sphere(Vector3f(5.0, -1, -15), 2, Vector3f(0.90, 0.76, 0.46)));
	spheres.push_back(Sphere(Vector3f(5.0, 0, -25), 3, Vector3f(0.65, 0.77, 0.97)));
	spheres.push_back(Sphere(Vector3f(-5.5, 0, -13), 3, Vector3f(0.90, 0.90, 0.90)));

	render(spheres);

	return 0;
}
