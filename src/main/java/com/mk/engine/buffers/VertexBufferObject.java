package com.mk.engine.buffers;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public class VertexBufferObject extends BufferObject<BufferData>
{
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
        super(GL_ARRAY_BUFFER, data.copy(), new ArrayList<>(strides));
    }

    public VertexBufferObject(List<Integer> strides)
    {
        super(GL_ARRAY_BUFFER, new ArrayList<>(strides));
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