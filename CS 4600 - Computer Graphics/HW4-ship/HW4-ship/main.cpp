#include "GL/glew.h"
#include "GLFW/glfw3.h"
#include <fstream>
#include <vector>
#include <iostream>
#include <eigen>

using namespace Eigen;

class Triangle 
{
public:
	int indices[3];
	inline int &operator[](const unsigned int &i) { return indices[i]; };
	inline int operator[](const unsigned int &i) const { return indices[i]; };
	inline void set(int x, int y, int z) { indices[0] = x; indices[1] = y; indices[2] = z; };
	inline Triangle(int x, int y, int z) { indices[0] = x; indices[1] = y; indices[2] = z; };
	inline Triangle() {};
	inline Triangle &operator=(const Triangle &that);
};

// ----------------------------------------------------------------------------

unsigned int g_windowWidth = 800;
unsigned int g_windowHeight = 600;
char* g_windowName = "HW4-Skinning";

GLFWwindow* g_window;

std::vector<Triangle> g_triangles;
std::vector<Vector3f> g_vertices;
std::vector<Vector3f> g_normals;
std::vector<std::vector<float> > g_weights; // [jointID][vertexID]
std::vector<std::vector<float> > g_poses;   // [poseID][jointID]

// joints
unsigned int g_numJoints;
std::vector<int> g_jointParent;	// indices of parent joints

// local transformations
std::vector<Matrix4f> g_jointRot;			// joint local rotation
std::vector<Matrix4f> g_jointOffset;		// joint local offset
std::vector<Matrix4f> g_jointRotRest;		// joint local rest-pose rotation

// ----------------------------------------------------------------------------

// global transformations
std::vector<Matrix4f> g_jointTrans;			// joint global transformation
std::vector<Matrix4f> g_jointTransRestInv;	// joint global rest-pose inverse transformation

// deformed vertices
std::vector<Vector3f> g_deformedVertices;

// ----------------------------------------------------------------------------

int g_enableAnimate = 0;
bool g_enableRenderSkeleton = 0;
bool g_enableRenderSkinningWeights = 0;

void animate();

float getTime()
{
	return (float) glfwGetTime();
}

void glfwErrorCallback(int error, const char* description)
{
	std::cerr << "GLFW Error " << error << ": " << description << std::endl;
	exit(1);
}

void glfwKeyCallback(GLFWwindow* p_window, int p_key, int p_scancode, int p_action, int p_mods)
{
	if (p_key == GLFW_KEY_ESCAPE && p_action == GLFW_PRESS)
	{
		glfwSetWindowShouldClose(g_window, GL_TRUE);
	}

	if (p_key == GLFW_KEY_A && p_action == GLFW_PRESS)
	{
		g_enableAnimate = (g_enableAnimate + 1) % 3;
	}

	if (p_key == GLFW_KEY_S && p_action == GLFW_PRESS)
	{
		g_enableRenderSkeleton = !(g_enableRenderSkeleton);
	}

	if (p_key == GLFW_KEY_W && p_action == GLFW_PRESS)
	{
		g_enableRenderSkinningWeights = !(g_enableRenderSkinningWeights);
	}
}

void initWindow()
{
	// initialize GLFW
	glfwSetErrorCallback(glfwErrorCallback);
	if (!glfwInit())
	{
		std::cerr << "GLFW Error: Could not initialize GLFW library" << std::endl;
		exit(1);
	}

	g_window = glfwCreateWindow(g_windowWidth, g_windowHeight, g_windowName, NULL, NULL);
	if (!g_window)
	{
		glfwTerminate();
		std::cerr << "GLFW Error: Could not initialize window" << std::endl;
		exit(1);
	}

	// callbacks
	glfwSetKeyCallback(g_window, glfwKeyCallback);

	// Make the window's context current
	glfwMakeContextCurrent(g_window);

	// turn on VSYNC
	glfwSwapInterval(1);
}

void initGL()
{
	glClearColor(1.f, 1.f, 1.f, 1.0f);

	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_DEPTH_TEST);
	glShadeModel(GL_SMOOTH);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(45.0f, (GLfloat)g_windowWidth / (GLfloat)g_windowHeight, 0.1f, 10.0f);
}

void setModelViewMatrix()
{
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	if (g_enableAnimate == 0)
		gluLookAt(sin(getTime() * 0.1f + 0.2f) * 3.0f, 1.0f, cos(getTime() * 0.1f + 0.2f) * 3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	else
		gluLookAt(2.0f, 1.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
}

void renderSkeletonRig()
{
	Vector4f v1, v2;
	glDisable(GL_DEPTH_TEST);
	glDisable(GL_LIGHTING);
	glLineWidth(2.0f);
	glPointSize(6.0f);	

	for (unsigned int jointID = 0; jointID < g_numJoints; ++jointID)
	{		
		const int parentJointID = g_jointParent[jointID];

		v1.setZero();
		v1(3) = 1.0f;
		v1 = g_jointTrans[jointID] * v1;
		
		glColor3f(1.0f, 0.0f, 1.0f);

		glBegin(GL_POINTS);
		glVertex3f(v1(0), v1(1), v1(2));
		glEnd();
		
		if (parentJointID != -1)
		{			
			v2.setZero();
			v2(3) = 1.0f;			
			v2 = g_jointTrans[parentJointID] * v2;			

			if (parentJointID % 3 == 0) glColor3f(1.0f, 0.0f, 0.0f);
			if (parentJointID % 3 == 1) glColor3f(0.0f, 1.0f, 0.0f);
			if (parentJointID % 3 == 2) glColor3f(0.0f, 0.0f, 1.0f);

			glBegin(GL_LINES);
			glVertex3f(v1(0), v1(1), v1(2));
			glVertex3f(v2(0), v2(1), v2(2));
			glEnd();
		}
	}

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	glColor3f(1.0f, 1.0f, 1.0f);
}

void renderMesh()
{
	glEnable(GL_COLOR_MATERIAL);			
	glBegin(GL_TRIANGLES);

	for (auto& triangle : g_triangles)
	{
		for (int i = 0; i < 3; ++i)
		{
			const int vidx = triangle[i];
			const Vector3f v = g_deformedVertices[vidx];
			const Vector3f vn = g_normals[vidx];
			glNormal3f(vn(0), vn(1), vn(2));

			if (g_enableRenderSkinningWeights)
			{
				// weight color
				float r = 0.4f, g = 0.4f, b = 0.4f;

				for (unsigned int jointID = 0; jointID < g_numJoints - 1; ++jointID)
				{
					const float w = g_weights[jointID][vidx];
					if (jointID % 3 == 0) r += 0.4f * w;
					if (jointID % 3 == 1) g += 0.4f * w;
					if (jointID % 3 == 2) b += 0.4f * w;
				}

				glColor3f(r, g, b);
			}
			else 
			{
				glColor3f(0.8f, 0.8f, 0.8f);
			}
			glVertex3f(v(0), v(1), v(2));
		}
	}

	glEnd();
}

void render()
{
	setModelViewMatrix();	
	renderMesh();
	if (g_enableRenderSkeleton) renderSkeletonRig();
}

void renderLoop()
{
	while (!glfwWindowShouldClose(g_window))
	{
		animate();

		// clear buffers
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		render();

		// Swap front and back buffers
		glfwSwapBuffers(g_window);

		// Poll for and process events
		glfwPollEvents();
	}
}

// ----------------------------------------------------------------------------

bool loadObj(std::string fname, std::vector<Vector3f> &pts, std::vector<Vector3f> &pts_n, std::vector<Triangle> &triangles)
{
	char c[500];

	int numVertices = 0, numFaces = 0;
	bool normals = false, texture = false;
	int tint;
	char ch;
	int p, q, r;
	float x, y, z;
	std::vector<Vector3f>::iterator v;
	std::vector<Vector3f>::iterator vn;
	std::vector<Triangle>::iterator t;
	std::ifstream in1(fname.c_str(), std::ios::in);
	if (!in1.is_open()) {
		return false;
	}
	in1.flags(in1.flags() & ~std::ios::skipws);

	while (in1 >> ch) {
		if (ch == 'v') {
			in1 >> ch;
			if (ch == ' ') numVertices++;
			else if (ch == 'n') normals = true;
			else if (ch == 't') texture = true;
			else std::cerr << "error \'" << ch << "\'" << std::endl;
		}
		else if (ch == '#') {
			while (in1 >> ch && ch != '\n'); // Read to the end of the line.
		}
		else if (ch == 'f') numFaces++;
	}
	in1.close();

	std::cout << "Mesh vertices: " << numVertices << std::endl;

	pts.resize(numVertices);
	pts_n.resize(numVertices);
	triangles.resize(numFaces);
	v = pts.begin();
	vn = pts_n.begin();
	t = triangles.begin();

	std::ifstream in(fname, std::ios::in);
	if (!in.is_open()) {
		return false;
	}

	while (in >> ch) {
		if (ch == '#') {
			in.getline(c, 500);
			continue;
		}
		if (ch == 'g') {
			in.getline(c, 500);
			continue;
		}
		if (ch == 's') {
			in.getline(c, 500);
			continue;
		}
		if (ch == 'm') {
			in.getline(c, 500);
			continue;
		}
		if (ch == 'u') {
			in.getline(c, 500);
			continue;
		}
		if (ch == 'v') {
			ch = in.peek();
			if (ch != 't' && ch != 'n') {
				in >> x >> y >> z;
				(*v) << x, y, z;
				v++;
			}
			else if (ch == 'n') {
				in >> ch;
				in >> x >> y >> z;
				(*vn) << x, y, z;
				//(*vn).normalize();
				vn++;
			}
			else {
				in.getline(c, 500);
			}
			continue;
		}
		if (ch == 'f') {
			if (normals && texture) {
				in >> p >> ch >> tint >> ch >> tint >> q >> ch >> tint >> ch >> tint >> r >> ch >> tint >> ch >> tint;
			}
			else if (normals) {
				in >> p >> ch >> ch >> tint >> q >> ch >> ch >> tint >> r >> ch >> ch >> tint;
			}
			else if (texture) {
				in >> p >> ch >> tint >> q >> ch >> tint >> r >> ch >> tint;
			}
			else {
				in >> p >> q >> r;
			}
			(*t)[0] = p - 1;
			(*t)[1] = q - 1;
			(*t)[2] = r - 1;
			t++;
			continue;
		}
	}
	in.close();
	return true;
}

void loadSkeleton(std::string fname) 
{
	std::ifstream in(fname.c_str(), std::ios::in);
	int jointID, parent;
	float offsetX, offsetY, offsetZ;

	while (in >> jointID >> parent >> offsetX >> offsetY >> offsetZ)
	{				
		g_jointParent.push_back(parent);

		Matrix4f offset;
		offset.setIdentity();
		offset(0, 3) = offsetX;
		offset(1, 3) = offsetY;
		offset(2, 3) = offsetZ;
		g_jointOffset.push_back(offset);
	}

	g_numJoints = (unsigned int) g_jointParent.size();

	std::cout << "Loaded " << g_numJoints << " joints" << std::endl;
}

void loaddmat(std::string fname, std::vector<std::vector<float> > &mat)
{
	std::ifstream in(fname.c_str(), std::ios::in);
	int cols, rows;
	in >> cols >> rows;
	mat.resize(cols);
	for (int i = 0; i < cols; i++) {
		mat[i].resize(rows);
		for (int j = 0; j < rows; j++) {
			in >> mat[i][j];
		}
	}
}

void setJointRotations(float t)
{
	for (unsigned int jointID = 0; jointID < g_numJoints; ++jointID)
	{
		Quaternionf qB = Quaternionf(g_poses[1][4 * jointID], g_poses[1][4 * jointID + 1], g_poses[1][4 * jointID + 2], g_poses[1][4 * jointID + 3]);

		Quaternionf qA;
		qA.setIdentity();
		Quaternionf q = qA.slerp(t, qB);

		Matrix4f R;
		R.setIdentity();
		R.block<3, 3>(0, 0) = q.toRotationMatrix();

		g_jointRot[jointID] = R;
	}
}

void loadData(std::string p_inputData)
{
	// load data
	std::cout << "Loading obj" << std::endl;
	loadObj("data/" + p_inputData + "/mesh.obj", g_vertices, g_normals, g_triangles);

	std::cout << "Loading skeleton" << std::endl;
	loadSkeleton("data/" + p_inputData + "/skeleton.bf");

	std::cout << "Loading weights" << std::endl;
	loaddmat("data/" + p_inputData + "/weights.dmat", g_weights);

	std::cout << "Loading poses" << std::endl;
	loaddmat("data/" + p_inputData + "/pose.dmat", g_poses);

	// initialize data structures
	Matrix4f ident;
	ident.setIdentity();
	g_deformedVertices.resize(g_vertices.size());
	g_jointRot.resize(g_numJoints, ident);
	g_jointRotRest.resize(g_numJoints, ident);
	g_jointTrans.resize(g_numJoints, ident);
	g_jointTransRestInv.resize(g_numJoints, ident);

	// set rest-pose rotation matrices
	setJointRotations(0.0f);
	g_jointRotRest = g_jointRot;
}

// ----------------------------------------------------------------------------

Vector3f fromHomog(const Vector4f& homog)
{
	return homog.block<3, 1>(0, 0);
}

Vector4f toHomog(const Vector3f& homog)
{
	Vector4f v;
	v << homog, 1.0f;
	return v;
}

// ----------------------------------------------------------------------------

void computeJointTransformations(
	const std::vector<Matrix4f>& p_local, 
	const std::vector<Matrix4f>& p_offset, 
	const std::vector<int>& p_jointParent, 
	const unsigned int p_numJoints, 
	std::vector<Matrix4f>& p_global)
{
	// TASK 1 comes here

	// Calculate for the root vertex
	p_global[0] = p_offset[0] * p_local[0];

	// Calulate for all the rest of the vertices that have parents.
	for (unsigned int i = 1; i < p_numJoints; i++) {
		
		p_global[i] = p_global[p_jointParent[i]] * p_offset[i] * p_local[i];
		
	}
}

void skinning(
		const std::vector<Vector3f>& p_vertices, 
		const unsigned int p_numJoints,
		const std::vector<Matrix4f>& p_jointTrans,
		const std::vector<Matrix4f>& p_jointTransRestInv,
		const std::vector<std::vector<float>>& p_weights,
		std::vector<Vector3f>& p_deformedVertices)
{
	// The following code simply copies rest pose vertex positions
	// You will need to replace this by your solution of TASK 2
	for (unsigned int v = 0; v < p_vertices.size(); v++)
	{
		// Homog the p_deformed and p_vertices
		Vector4f vertexHomog = toHomog(p_vertices[v]);
		Vector4f defHomog;

		// Set all the values in defHomog to 0.
		for (unsigned int j = 0; j < 4; j++) {
			defHomog[j] = 0;
		}

		// Perform the summation.
		for (unsigned int i = 0; i < p_numJoints; i++) {
			defHomog += p_weights[i][v] * p_jointTrans[i] * p_jointTransRestInv[i] * vertexHomog;
		}

		// De-Homog the value and place it into p_deformed Vertices, this is now our V'
		p_deformedVertices[v] = fromHomog(defHomog);
	}
}

void initRestPose()
{
	computeJointTransformations(g_jointRotRest, g_jointOffset, g_jointParent, g_numJoints, g_jointTrans);
	for (unsigned int jointID = 0; jointID < g_numJoints; ++jointID)
	{
		g_jointTransRestInv[jointID] = g_jointTrans[jointID].inverse();
	}
}

void animate()
{
	const float t = std::fabsf(sinf(getTime()));
	switch (g_enableAnimate)
	{
		case 0: setJointRotations(t); break;
		case 1: setJointRotations(0.0f); break;
		case 2: setJointRotations(1.0f); break;
	}	
	computeJointTransformations(g_jointRot, g_jointOffset, g_jointParent, g_numJoints, g_jointTrans);
	skinning(g_vertices, g_numJoints, g_jointTrans, g_jointTransRestInv, g_weights, g_deformedVertices);
}

int main(int argc, char *argv[]) 
{
	loadData("capsule"); // replace this with the following line to load the Ogre instead
	//loadData("ogre");
	
	initRestPose();

	std::cout << std::endl << "Controls:" << std::endl
		<< "Press key A to switch animation control" << std::endl
		<< "Press key S to show skeleton rig" << std::endl
		<< "Press key W to show skinning weights" << std::endl;

	initWindow();
	initGL();
	renderLoop();
}
