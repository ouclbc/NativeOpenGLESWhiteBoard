/**
 //上传顶点数据到显卡 CPU->GPU
// void glVertexAttribPointer(GLuint index,GLint size,GLenum type,GLboolean normalized,GLsizei stride,const void *ptr)
// index: 着色器脚本对应变量ID
// size : 此类型数据的个数,点，2个数据，三角形，3个数据
// type : 此类型的sizeof值
// normalized : 是否对非float类型数据转化到float时候进行归一化处理
// stride : 此类型数据在数组中的重复间隔宽度，byte类型计数 2*4 1float = 4byte
// ptr    : 数据指针， 这个值受到VBO的影响
 */

#include "Shape.h"
#include "GLUtil.h"
#include <math.h>
//not used
void Shape::initVertex(){

    mVertexArray = new GLfloat[3*8];
    mColorArray = new GLfloat[8*4];
     int j = 0, k = 0;
     mVertexArray[j++] = 0;
     mVertexArray[j++] = 0;
     mVertexArray[j++] = 0;

     mColorArray[k++] = 1;
     mColorArray[k++] = 1;
     mColorArray[k++] = 1;
     mColorArray[k++] = 0;
     for (int angle = 0; angle <= 360; angle += 60) {
         mVertexArray[j++] = (GLfloat) (cos(PI * angle / 180));
         mVertexArray[j++] = (GLfloat) (sin(PI * angle / 180));
         mVertexArray[j++] = 0;

         mColorArray[k++] = 1;
         mColorArray[k++] = 0;
         mColorArray[k++] = 0;
         mColorArray[k++] = 0;
     }
}


void Shape::initGL(const char *vertexShaderCode, const char *fragmentShaderCode) {
    mProgram = GLUtil::createProgram(vertexShaderCode, fragmentShaderCode);
    mUMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");
    mAPositionHandle = glGetAttribLocation(mProgram, "aPosition");
    //mAColorHandle = glGetAttribLocation(mProgram, "aColor");
    mVColorHandle  = glGetAttribLocation(mProgram, "vColor");
}

void Shape::draw(float mvpMatrix[], float *pointArray,int length) {
    glUseProgram(mProgram);
    // 将顶点数据传递到管线，顶点着色器
    glUniformMatrix4fv(mUMVPMatrixHandle, 1, GL_FALSE, mvpMatrix);
    glVertexAttribPointer(mAPositionHandle, 2, GL_FLOAT, GL_FALSE, 2 * 4, pointArray);
    //更改fragment着色器颜色
    glUniform4f(mVColorHandle, 1, 1, 1, 1);//RGBA格式

    glEnableVertexAttribArray(mAPositionHandle);
    //glEnableVertexAttribArray(mAColorHandle);

    //add for whiteboard
    glEnable(GL_BLEND);
    glLineWidth(5);
    glDrawArrays(GL_LINE_STRIP, 0, length/2);
    // 绘制图元
    //glDrawArrays(GL_TRIANGLE_FAN, 0, 8);

}
Shape::Shape() {
    initVertex();
}
Shape::~Shape() {
    delete [] mVertexArray;
    delete [] mColorArray;
}
