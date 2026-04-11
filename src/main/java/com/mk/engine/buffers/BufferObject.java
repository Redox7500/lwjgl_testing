package com.mk.engine.buffers;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class BufferObject
{
    public int type;
    public List<Integer> strides = new ArrayList<>(List.of(1));

    private int id = glGenBuffers();
    private BufferData data;

    public BufferObject(int type)
    {
        this.type = type;
    }

    public BufferObject(int type, BufferData data, int drawType)
    {
        this.type = type;
        this.setData(data, drawType);
    }

    public BufferObject(int type, BufferData data, int drawType, List<Integer> strides)
    {
        this.type = type;
        this.setData(data, drawType);
        this.strides = strides;
    }

    public BufferObject(int type, List<Integer> strides)
    {
        this.type = type;
        this.strides = strides;
    }

    private void updateBufferData(int drawType)
    {
        this.use();
        this.data.use(this.type, drawType);
    }

    public BufferData getData()
    {
        return this.data.copy();
    }

    public void setData(BufferData data, int drawType)
    {
        this.data = data.copy();
        this.updateBufferData(drawType);
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