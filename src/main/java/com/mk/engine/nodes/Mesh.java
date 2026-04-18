package com.mk.engine.nodes;

import com.mk.engine.buffers.VertexArrayObject;

public class Mesh extends Node
{
    // add transform usage

    public VertexArrayObject vertexArrayObject = null;

    public Mesh(VertexArrayObject vertexArrayObject)
    {
        super();

        this.vertexArrayObject = vertexArrayObject;
    }

    public Mesh(Transform transform, VertexArrayObject vertexArrayObject)
    {
        super(transform);

        this.vertexArrayObject = vertexArrayObject;
    }

    @Override
    public void draw()
    {
        if (!this.shouldDraw)
        {
            return;
        }
        
        this.vertexArrayObject.draw();
    }
    // public List<VertexBufferObject> vertexBufferObjects = new ArrayList<>();
    // public ElementBufferObject elementBufferObject = null;

    // private VertexArrayObject vertexArrayObject = null;

    // public Mesh()
    // {
    //     super();

    //     this.updateVertexArrayObject();
    // }

    // public Mesh(List<VertexBufferObject> vertexBufferObjects)
    // {
    //     super();

    //     this.vertexBufferObjects = vertexBufferObjects;

    //     this.updateVertexArrayObject();
    // }

    // public Mesh(Transform transform, List<VertexBufferObject> vertexBufferObjects)
    // {
    //     super(transform);

    //     this.vertexBufferObjects = vertexBufferObjects;

    //     this.updateVertexArrayObject();
    // }

    // public void updateVertexArrayObject()
    // {
    //     int maxAttributes = glGetInteger(GL_MAX_VERTEX_ATTRIBS);

    //     glBindVertexArray(this.vertexArrayObjectId);

    //     toBeDrawn = Integer.MAX_VALUE;

    //     int attributeLocation = 0;
    //     for (int i = 0; i < this.vertexBufferObjects.size(); i++)
    //     {
    //         VertexBufferObject currentVertexBufferObject = this.vertexBufferObjects.get(i);
    //         currentVertexBufferObject.use();

    //         int currentDataType = currentVertexBufferObject.getDataType();
    //         int currentFullElementStrides = currentVertexBufferObject.getFullElementStrides();

    //         int currentBytesPerElement = currentVertexBufferObject.getDataTypeBytes();
    //         int currentFullByteStrides = currentFullElementStrides * currentBytesPerElement;
    //         int totalByteStrides = 0;
    //         for (int stride:currentVertexBufferObject.strides)
    //         {
    //             if (attributeLocation >= maxAttributes)
    //             {
    //                 throw new IllegalStateException("More vertex attributes than GL_MAX_VERTEX_ATTRIBS, which is " + maxAttributes);
    //             }

    //             switch (currentDataType)
    //             {
    //                 case GL_INT -> glVertexAttribIPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
    //                 case GL_DOUBLE -> glVertexAttribLPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
    //                 default -> glVertexAttribPointer(attributeLocation, stride, currentDataType, false, currentFullByteStrides, totalByteStrides);
    //             }
    //             glEnableVertexAttribArray(attributeLocation);

    //             attributeLocation++;
    //             totalByteStrides += stride * currentBytesPerElement;
    //         }

    //         int currentVertexCount = currentVertexBufferObject.getDataLength() / currentFullElementStrides;
    //         if (currentVertexCount < toBeDrawn)
    //         {
    //             toBeDrawn = currentVertexCount;
    //         }
    //     }

    //     if (this.elementBufferObject != null)
    //     {
    //         this.elementBufferObject.use();
    //         toBeDrawn = Math.min(this.elementBufferObject.getDataLength(), toBeDrawn);
    //     }

    //     glBindVertexArray(0);
    // }

    // @Override
    // public void draw()
    // {
    //     if (!this.shouldDraw)
    //     {
    //         return;
    //     }

    //     glBindVertexArray(this.vertexArrayObjectId);

    //     if (this.elementBufferObject != null)
    //     {
    //         glDrawElements(GL_TRIANGLES, toBeDrawn, this.elementBufferObject.getDataType(), 0);
    //     }
    //     else
    //     {
    //         glDrawArrays(GL_TRIANGLES, 0, toBeDrawn);
    //     }

    //     super.draw();
    // }
}