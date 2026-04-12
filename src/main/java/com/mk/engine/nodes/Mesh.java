package com.mk.engine.nodes;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

import com.mk.engine.buffers.ElementBufferObject;
import com.mk.engine.buffers.VertexBufferObject;

public class Mesh extends Node
{
    public List<VertexBufferObject> vertexBufferObjects = new ArrayList<>();
    public ElementBufferObject elementBufferObject = null;

    private int vertexArrayObjectId = glGenVertexArrays();
    private int toBeDrawn = 0;

    public Mesh()
    {
        super();

        this.updateVertexArrayObject();
    }

    public Mesh(List<VertexBufferObject> vertexBufferObjects)
    {
        super();

        this.vertexBufferObjects = vertexBufferObjects;

        this.updateVertexArrayObject();
    }

    public Mesh(Transform transform, List<VertexBufferObject> vertexBufferObjects)
    {
        super(transform);

        this.vertexBufferObjects = vertexBufferObjects;

        this.updateVertexArrayObject();
    }

    public void updateVertexArrayObject()
    {
        int maxAttributes = glGetInteger(GL_MAX_VERTEX_ATTRIBS);

        glBindVertexArray(this.vertexArrayObjectId);

        toBeDrawn = Integer.MAX_VALUE;

        int attributeLocation = 0;
        for (int i = 0; i < this.vertexBufferObjects.size(); i++)
        {
            VertexBufferObject currentVertexBufferObject = this.vertexBufferObjects.get(i);
            currentVertexBufferObject.use();

            int currentDataType = currentVertexBufferObject.getDataType();
            int currentFullElementStrides = currentVertexBufferObject.getFullElementStrides();

            int currentBytesPerElement = currentVertexBufferObject.getDataTypeBytes();
            int currentFullByteStrides = currentFullElementStrides * currentBytesPerElement;
            int totalByteStrides = 0;
            for (int stride:currentVertexBufferObject.strides)
            {
                if (attributeLocation >= maxAttributes)
                {
                    throw new IllegalStateException("More vertex attributes than GL_MAX_VERTEX_ATTRIBS, which is " + maxAttributes);
                }

                switch (currentDataType)
                {
                    case GL_INT -> glVertexAttribIPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                    case GL_DOUBLE -> glVertexAttribLPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                    default -> glVertexAttribPointer(attributeLocation, stride, currentDataType, false, currentFullByteStrides, totalByteStrides);
                }
                glEnableVertexAttribArray(attributeLocation);

                attributeLocation++;
                totalByteStrides += stride * currentBytesPerElement;
            }

            int currentVertexCount = currentVertexBufferObject.getDataLength() / currentFullElementStrides;
            if (currentVertexCount < toBeDrawn)
            {
                toBeDrawn = currentVertexCount;
            }
        }

        if (this.elementBufferObject != null)
        {
            this.elementBufferObject.use();
            toBeDrawn = Math.min(this.elementBufferObject.getDataLength(), toBeDrawn);
        }

        glBindVertexArray(0);
    }

    @Override
    public void draw()
    {
        if (!this.shouldDraw)
        {
            return;
        }

        glBindVertexArray(this.vertexArrayObjectId);

        if (this.elementBufferObject != null)
        {
            glDrawElements(GL_TRIANGLES, toBeDrawn, this.elementBufferObject.getDataType(), 0);
        }
        else
        {
            glDrawArrays(GL_TRIANGLES, 0, toBeDrawn);
        }

        super.draw();
    }
}