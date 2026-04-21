package com.mk.engine.buffers.bufferobjects;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

import com.mk.engine.buffers.data.BufferData;
import com.mk.engine.buffers.objects.VertexArrayObject;

public class VertexBufferObject extends BufferObject<BufferData>
{
    private List<Integer> strides = new ArrayList<>(List.of(1));

    public VertexBufferObject()
    {
        super(GL_ARRAY_BUFFER);
    }

    public VertexBufferObject(BufferData data)
    {
        super(GL_ARRAY_BUFFER, data.copy());
    }

    public VertexBufferObject(BufferData data, List<Integer> strides)
    {
        super(GL_ARRAY_BUFFER, data.copy());

        this.setStrides(strides);
    }

    public VertexBufferObject(List<Integer> strides)
    {
        super(GL_ARRAY_BUFFER);

        this.setStrides(strides);
    }

    public VertexBufferObject(int dataUsage)
    {
        super(GL_ARRAY_BUFFER, dataUsage);
    }

    public VertexBufferObject(BufferData data, int dataUsage)
    {
        super(GL_ARRAY_BUFFER, data.copy(), dataUsage);
    }

    public VertexBufferObject(BufferData data, List<Integer> strides, int dataUsage)
    {
        super(GL_ARRAY_BUFFER, data.copy(), dataUsage);

        this.setStrides(strides);
    }

    public VertexBufferObject(List<Integer> strides, int dataUsage)
    {
        super(GL_ARRAY_BUFFER, dataUsage);

        this.setStrides(strides);
    }

    public void update(int drawType)
    {
        this.use();
        this.data.use(this.type, drawType);
    }

    public ArrayList<Integer> getStrides()
    {
        return new ArrayList<>(this.strides);
    }

    public void setStrides(List<Integer> strides)
    {
        this.strides = new ArrayList<>(strides);

        if (this.vertexArrayObject != null)
        {
            this.vertexArrayObject.updateVertexBufferObjectStrides(this);
        }
    }

    public int getTotalStrides()
    {
        int totalStrides = 0;
        for (int stride:this.strides)
        {
            totalStrides += stride;
        }

        return totalStrides;
    }

    @Override
    public BufferData getData()
    {
        return super.getData().copy();
    }

    @Override
    public void setData(BufferData data, int drawType)
    {
        super.setData(data.copy(), drawType);

        if (this.vertexArrayObject != null)
        {
            this.vertexArrayObject.updateVertexBufferObjectData(this);
        }
    }

    @Override
    public void setVertexArrayObject(VertexArrayObject vertexArrayObject)
    {
        VertexArrayObject previousVertexArrayObject = this.getVertexArrayObject();
        if (previousVertexArrayObject != null)
        {
            previousVertexArrayObject.removeVertexBufferObject(this);
        }
        
        super.setVertexArrayObject(vertexArrayObject);
    }
}