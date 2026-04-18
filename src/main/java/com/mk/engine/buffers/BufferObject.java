package com.mk.engine.buffers;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public abstract class BufferObject<T extends BufferData>
{
    public int type;

    private int id = glGenBuffers();
    private VertexArrayObject vertexArrayObject = null;
    private T data = null;
    private List<Integer> strides = new ArrayList<>(List.of(1));
    private boolean dirtyData = false;
    private boolean dirtyStrides = false;

    public BufferObject(int type)
    {
        this.type = type;
    }

    public BufferObject(int type, T data)
    {
        this.type = type;
        this.data = data;

        this.dirtyData = true;
    }

    public BufferObject(int type, T data, List<Integer> strides)
    {
        this.type = type;
        this.data = data;
        this.strides = strides;

        this.dirtyData = true;
        this.dirtyStrides = true;
    }

    public BufferObject(int type, List<Integer> strides)
    {
        this.type = type;
        this.strides = strides;

        this.dirtyStrides = true;
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

        this.dirtyData = true;
    }

    public BufferObject(VertexArrayObject vertexArrayObject, int type, T data, List<Integer> strides)
    {
        this.vertexArrayObject = vertexArrayObject;
        this.type = type;
        this.data = data;
        this.strides = strides;

        this.dirtyData = true;
        this.dirtyStrides = true;
    }

    public BufferObject(VertexArrayObject vertexArrayObject, int type, List<Integer> strides)
    {
        this.vertexArrayObject = vertexArrayObject;
        this.type = type;
        this.strides = strides;

        this.dirtyStrides = true;
    }

    public void update(int drawType)
    {
        this.use();
        this.data.use(this.type, drawType);

        this.dirtyData = false;
        this.dirtyStrides = false; // weird, but needed to, well, u know, make it false at some point in the code
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
        
        this.dirtyData = true;
    }

    public ArrayList<Integer> getStrides()
    {
        return new ArrayList<>(this.strides);
    }

    public void setStrides(ArrayList<Integer> strides)
    {
        this.strides = new ArrayList<>(strides);

        this.dirtyStrides = true;
    }

    public boolean hasDirtyData()
    {
        return this.dirtyData;
    }

    public boolean hasDirtyStrides()
    {
        return this.dirtyStrides;
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

    public int getFullElementStrides()
    {
        int fullElementStrides = 0;
        for (int stride:this.strides)
        {
            fullElementStrides += stride;
        }

        return fullElementStrides;
    }

    public void use()
    {
        glBindBuffer(this.type, this.id);
    }
}