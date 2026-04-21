package com.mk.engine.buffers.bufferobjects;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import com.mk.engine.buffers.data.BufferData;
import com.mk.engine.buffers.objects.VertexArrayObject;

public abstract class BufferObject<T extends BufferData>
{
    public int type;
    public int dataUsage = GL_STATIC_DRAW;

    protected int id = glGenBuffers();
    protected VertexArrayObject vertexArrayObject = null;
    protected T data = null;

    public BufferObject(int type)
    {
        this.type = type;
    }

    public BufferObject(int type, T data)
    {
        this.type = type;
        this.data = data;
    }

    public BufferObject(VertexArrayObject vertexArrayObject, int type)
    {
        this.vertexArrayObject = vertexArrayObject;
        this.type = type;
    }

    public BufferObject(VertexArrayObject vertexArrayObject, int type, T data)
    {
        this.vertexArrayObject = vertexArrayObject;
        this.type = type;
        this.data = data;
    }

    public BufferObject(int type, int dataUsage)
    {
        this.type = type;
        this.dataUsage = dataUsage;
    }

    public BufferObject(int type, T data, int dataUsage)
    {
        this.type = type;
        this.data = data;
        this.dataUsage = dataUsage;
    }

    public BufferObject(VertexArrayObject vertexArrayObject, int type, int dataUsage)
    {
        this.vertexArrayObject = vertexArrayObject;
        this.type = type;
        this.dataUsage = dataUsage;
    }

    public BufferObject(VertexArrayObject vertexArrayObject, int type, T data, int dataUsage)
    {
        this.vertexArrayObject = vertexArrayObject;
        this.type = type;
        this.data = data;
        this.dataUsage = dataUsage;
    }

    public VertexArrayObject getVertexArrayObject()
    {
        return this.vertexArrayObject;
    }

    public void setVertexArrayObject(VertexArrayObject vertexArrayObject)
    {
        this.vertexArrayObject = vertexArrayObject;
    }

    public T getData()
    {
        return this.data;
    }

    public void setData(T data, int drawType)
    {
        this.data = data;
    }

    public int getDataType()
    {
        return this.data.getType();
    }

    public int getDataTypeBytes()
    {
        return this.data.getTypeBytes();
    }

    public int getDataLength()
    {
        return this.data.getLength();
    }

    public void use()
    {
        glBindBuffer(this.type, this.id);
    }
}