#include "GL/glew.h"
#include "GLFW/glfw3.h"
#include <string>
#include <vector>
#include <iostream>
#include <fstream>

unsigned int g_windowWidth = 800;
unsigned int g_windowHeight = 600;
char* g_windowName = "HW1-OpenGL-Basics";

GLFWwindow* g_window;

// model data
std::vector<float> g_meshVertices;
std::vector<float> g_meshNormals;
std::vector<unsigned int> g_meshIndices;

GLfloat g_modelViewMatrix[16];

void computeNormals()
{
	g_meshNormals.resize(g_meshVertices.size());

	for (int i = 0; i < g_meshIndices.size() / 3; ++i) {

		// Get all Indicies (Points) Of Face (Triangle)
		int p1 = g_meshIndices[3 * i];
		int p2 = g_meshIndices[3 * i + 1];
		int p3 = g_meshIndices[3 * i + 2];

		// Get Vertices of P1
		float p1x = g_meshVertices[3 * p1];
		float p1y = g_meshVertices[3 * p1 + 1];
		float p1z = g_meshVertices[3 * p1 + 2];

		// Get Vertices of P2
		float p2x = g_meshVertices[3 * p2];
		float p2y = g_meshVertices[3 * p2 + 1];
		float p2z = g_meshVertices[3 * p2 + 2];

		// Get Vertices of P3
		float p3x = g_meshVertices[3 * p3];
		float p3y = g_meshVertices[3 * p3 + 1];
		float p3z = g_meshVertices[3 * p3 + 2];

		// Perform Cross Product
		float ux = p2x - p1x;
		float uy = p2y - p1y;
		float uz = p2z - p1z;

		float vx = p3x - p1x;
		float vy = p3y - p1y;
		float vz = p3z - p1z;

		float nx = (uy * vz) - (uz * vy);
		float ny = (uz * vx) - (ux * vz);
		float nz = (ux * vy) - (uy * vx);
	
		// Calcualte the Length
		float length = sqrt(pow(nx, 2) + pow(ny, 2) + pow(nz, 2));

		// Normalize the Cross Product Value by dividing the Length
		float nX = nx / length;
		float nY = ny / length;
		float nZ = nz / length;

		// Add the Normal
		g_meshNormals[3* p1] += nX;
		g_meshNormals[3 * p1 + 1] += nY;
		g_meshNormals[3 * p1 + 2] += nZ;

		g_meshNormals[3 * p2] += nX;
		g_meshNormals[3 * p2 + 1] += nY;
		g_meshNormals[3 * p2 + 2] += nZ;

		g_meshNormals[3 * p3] += nX;
		g_meshNormals[3 * p3 + 1] += nY;
		g_meshNormals[3 * p3 + 2] += nZ;

	}

	// Iterate through all the Normals and divide it by the number of Indicies attached to each Point
	for (int v = 0; v < g_meshNormals.size() / 3; ++v){

		g_meshNormals[3 * v] = g_meshNormals[3 * v] / 6;
		g_meshNormals[3 * v + 1] = g_meshNormals[3 * v + 1] / 6;
		g_meshNormals[3 * v + 2] = g_meshNormals[3 * v + 2] / 6;

	}
}

void loadObj(std::string p_path)
{
	std::ifstream nfile;
	nfile.open(p_path);
	std::string s;

	while (nfile >> s)
	{
		if (s.compare("v") == 0)
		{
			float x, y, z;
			nfile >> x >> y >> z;
			g_meshVertices.push_back(x);
			g_meshVertices.push_back(y);
			g_meshVertices.push_back(z);
		}		
		else if (s.compare("f") == 0)
		{
			std::string sa, sb, sc;
			unsigned int a, b, c;
			nfile >> sa >> sb >> sc;

			a = std::stoi(sa);
			b = std::stoi(sb);
			c = std::stoi(sc);

			g_meshIndices.push_back(a - 1);
			g_meshIndices.push_back(b - 1);
			g_meshIndices.push_back(c - 1);
		}
		else
		{
			std::getline(nfile, s);
		}
	}

	computeNormals();

	std::cout << p_path << " loaded. Vertices: " << g_meshVertices.size() / 3 << " Triangles: " << g_meshIndices.size() / 3 << std::endl;
}

double getTime()
{
	return glfwGetTime();
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
	gluPerspective(45.0f, (GLfloat) g_windowWidth / (GLfloat)g_windowHeight, 0.1f, 10.0f);
}

void clearModelViewMatrix()
{
	for (int i = 0; i < 4; ++i)
	{
		for (int j = 0; j < 4; ++j)
		{
			g_modelViewMatrix[4 * i + j] = 0.0f;
		}
	}
}

void updateModelViewMatrix()
{	
	clearModelViewMatrix();

	// Get the Current Time
	double time = getTime();

	// Add to the View Matrix the angle the camera is supposed to turn at for the object to rotate
	g_modelViewMatrix[0] = cos(time);
	g_modelViewMatrix[2] = -sin(time);
	g_modelViewMatrix[5] = 1.0f;
	g_modelViewMatrix[8] = sin(time);
	g_modelViewMatrix[10] = cos(time);

	g_modelViewMatrix[14] = -5.0f;
	g_modelViewMatrix[15] = 1.0f;
}

void setModelViewMatrix()
{
	glMatrixMode(GL_MODELVIEW);
	updateModelViewMatrix();
	glLoadMatrixf(g_modelViewMatrix);
}

void render()
{
	setModelViewMatrix();

	glBegin(GL_TRIANGLES);

	for (size_t f = 0; f < g_meshIndices.size(); ++f)
	{
		const float scale = 0.1f;
		const unsigned int idx = g_meshIndices[f];
		const float x = scale * g_meshVertices[3 * idx + 0];
		const float y = scale * g_meshVertices[3 * idx + 1];
		const float z = scale * g_meshVertices[3 * idx + 2];

		const float nx = g_meshNormals[3 * idx + 0];
		const float ny = g_meshNormals[3 * idx + 1];
		const float nz = g_meshNormals[3 * idx + 2];
				
		glNormal3f(nx, ny, nz);
		glVertex3f(x, y, z);
	}

	glEnd();
	
}

void renderLoop()
{
	while (!glfwWindowShouldClose(g_window))
	{
		// clear buffers
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		render();

		// Swap front and back buffers
		glfwSwapBuffers(g_window);

		// Poll for and process events
		glfwPollEvents();
	}
}

int main()
{
	initWindow();
	initGL();
	loadObj("data/teapot.obj");
	renderLoop();
}
