package com.mk.engine.buffers.vertexarrayobjects;

import static org.lwjgl.opengl.GL11.GL_BYTE;
import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
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

import com.mk.engine.buffers.bufferobjects.ElementBufferObject;
import com.mk.engine.buffers.bufferobjects.VertexBufferObject;

public class VertexArrayObject
{
    private int id = glGenVertexArrays();
    private VertexBufferObject[] vertexBufferObjects;
    private ElementBufferObject elementBufferObject;
    private int toBeDrawn = 0;

    public VertexArrayObject(VertexBufferObject[] vertexBufferObjects)
    {
        this.vertexBufferObjects = vertexBufferObjects.clone();
        this.initialize();
    }

    public VertexArrayObject(VertexBufferObject[] vertexBufferObjects, ElementBufferObject elementBufferObject)
    {
        this.vertexBufferObjects = vertexBufferObjects.clone();
        this.elementBufferObject = elementBufferObject;
        this.initialize();
    }

    public void use() {glBindVertexArray(this.id);}
    public void unuse() {glBindVertexArray(0);}

    private void initialize()
    {
        int maxAttributes = glGetInteger(GL_MAX_VERTEX_ATTRIBS);

        this.use();

        this.toBeDrawn = 0;

        int attributeLocation = 0;
        for (VertexBufferObject vertexBufferObject:this.vertexBufferObjects)
        {
            vertexBufferObject.use();

            int currentDataType = vertexBufferObject.getDataType();
            int currentFullElementStrides = vertexBufferObject.getTotalStrides();

            int currentBytesPerElement = vertexBufferObject.getDataTypeBytes();
            int currentFullByteStrides = currentFullElementStrides * currentBytesPerElement;
            int totalByteStrides = 0;
            for (int stride:vertexBufferObject.getStrides())
            {
                if (attributeLocation >= maxAttributes)
                {
                    throw new IllegalStateException("More vertex attributes than GL_MAX_VERTEX_ATTRIBS, which is " + maxAttributes);
                }

                switch (currentDataType)
                {
                    case GL_BYTE, GL_SHORT, GL_INT, GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT, GL_UNSIGNED_INT -> glVertexAttribIPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                    case GL_DOUBLE -> glVertexAttribLPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                    default -> glVertexAttribPointer(attributeLocation, stride, currentDataType, false, currentFullByteStrides, totalByteStrides);
                }
                glEnableVertexAttribArray(attributeLocation);

                attributeLocation++;
                totalByteStrides += stride * currentBytesPerElement;
            }

            int currentVertexCount = vertexBufferObject.getDataLength() / currentFullElementStrides;
            if (currentVertexCount < this.toBeDrawn || currentVertexCount == 0)
            {
                throw new IllegalStateException("VBO has an invalid number of vertex attributes");
            }

            this.toBeDrawn = currentVertexCount;
        }

        if (this.elementBufferObject != null)
        {
            this.elementBufferObject.use();
            this.toBeDrawn = Math.min(this.elementBufferObject.getDataLength(), this.toBeDrawn);
        }

        this.unuse();
    }

    public void draw()
    {
        this.use();

        // if (dirty)
        // {
        //     // make this better hopefully, maybe put in BufferObject class itself
        //     // edit: this just needs to be better by not updating all of the vertex attributes for evertyihng, i think, but maybe i need to? idk
        //     this.update();
        // }

        if (this.elementBufferObject != null)
        {
            glDrawElements(GL_TRIANGLES, this.toBeDrawn, this.elementBufferObject.getDataType(), 0);
        }
        else
        {
            glDrawArrays(GL_TRIANGLES, 0, this.toBeDrawn);
        }

        this.unuse();
    }
}