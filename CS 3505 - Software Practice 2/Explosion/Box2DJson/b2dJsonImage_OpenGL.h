/*
* Author: Chris Campbell - www.iforce2d.net
*
* This software is provided 'as-is', without any express or implied
* warranty.  In no event will the authors be held liable for any damages
* arising from the use of this software.
* Permission is granted to anyone to use this software for any purpose,
* including commercial applications, and to alter it and redistribute it
* freely, subject to the following restrictions:
* 1. The origin of this software must not be misrepresented; you must not
* claim that you wrote the original software. If you use this software
* in a product, an acknowledgment in the product documentation would be
* appreciated but is not required.
* 2. Altered source versions must be plainly marked as such, and must not be
* misrepresented as being the original software.
* 3. This notice may not be removed or altered from any source distribution.
*/

#ifndef B2DJSONIMAGE_OPENGL_H
#define B2DJSONIMAGE_OPENGL_H

#ifdef __APPLE__
	#include <GLUT/glut.h>
#else
	#include "freeglut/freeglut.h"
#endif

#include "b2dJsonImage.h"

class b2dJsonImage_OpenGL : public b2dJsonImage {
protected:
    GLuint m_textureId;
public:
    b2dJsonImage_OpenGL( const b2dJsonImage* other );
    virtual bool loadImage();
    virtual void render();
    void renderUsingCorners();
    void renderUsingArrays();
};

#endif // B2DJSONIMAGE_OPENGL_H
