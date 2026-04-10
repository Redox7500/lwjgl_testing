package com.mk.engine.nodes;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;

import com.mk.engine.buffers.BufferData;
import com.mk.engine.buffers.BufferObject;

public class Mesh extends Node
{
    public List<BufferObject> vertexBufferObjects = new ArrayList<>();

    private int vertexArrayObject = glGenVertexArrays();

    public Mesh()
    {
        super();
    }

    public Mesh(List<BufferObject> vertexBufferObjects)
    {
        super();

        this.vertexBufferObjects = vertexBufferObjects;
    }

    public Mesh(Transform transform, List<BufferObject> vertexBufferObjects)
    {
        super(transform);

        this.vertexBufferObjects = vertexBufferObjects;
    }

    @Override
    public void draw()
    {
        if (!this.shouldDraw)
        {
            return;
        }

        glBindVertexArray(this.vertexArrayObject);

        // only need to set the buffer stuff when buffers are updated
        int minVertexCount = -1;

        int attributeIndex = 0;
        int totalByteStrides = 0;
        for (int i = 0; i < this.vertexBufferObjects.size(); i++)
        {
            BufferObject currentVertexBufferObject = this.vertexBufferObjects.get(i);
            currentVertexBufferObject.use();

            int currentDataType = currentVertexBufferObject.getDataType();
            int currentFullElementStrides = currentVertexBufferObject.getFullElementStrides();

            int currentBytesPerElement = BufferData.bytesOfType(currentDataType);
            int currentFullByteStrides = currentFullElementStrides * currentBytesPerElement;
            for (int stride:currentVertexBufferObject.strides)
            {
                // check total byte stride thing
                // should have double attributes, not available for OpenGL < 4.1
                if (currentVertexBufferObject.getDataType() != GL_INT)
                {
                    glVertexAttribPointer(attributeIndex, stride, currentDataType, false, currentFullByteStrides, totalByteStrides);
                }
                else
                {
                    glVertexAttribIPointer(attributeIndex, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                }
                glEnableVertexAttribArray(attributeIndex);

                attributeIndex += stride;
                totalByteStrides += stride * currentBytesPerElement;
            }

            int vertexCount = BufferData.getDataLength(currentVertexBufferObject.getData()) / currentFullElementStrides;
            if (minVertexCount == -1 || vertexCount < minVertexCount)
            {
                minVertexCount = vertexCount;
            }

            attributeIndex++;
        }

        // change to the other drawing function later for ebos?
        glDrawArrays(GL_TRIANGLES, 0, minVertexCount);
        
        // glBindVertexArray(this.vao);
        // glBindBuffer(GL_ARRAY_BUFFER, this.vbo);

        // FloatBuffer buffer = BufferUtils.createFloatBuffer(this.vertices.length);
        // buffer.put(vertices).flip();
        // glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        // glVertexAttribPointer(0, 3, GL_FLOAT, false, this.vertexSize * Float.BYTES, 0);
        // glEnableVertexAttribArray(0);
        // if (this.hasNormals)
        // {
        //     glVertexAttribPointer(1, 3, GL_FLOAT, )
        // }
        // if (this.hasUVs)
        // {
        //     glVertexAttribPointer(1, 2, GL_FLOAT, false, this.vertexSize * Float.BYTES, 3 * Float.BYTES);
        //     glEnableVertexAttribArray(1);
        // }

        // glDrawArrays(GL_TRIANGLES, 0, this.vertexPositions.length);

        super.draw();
    }
}