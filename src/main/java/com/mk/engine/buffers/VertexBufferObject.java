package com.mk.engine.buffers;

import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public class VertexBufferObject extends BufferObject
{
    public VertexBufferObject()
    {
        super(GL_ARRAY_BUFFER);
    }

    public VertexBufferObject(BufferData data, int drawType)
    {
        super(GL_ARRAY_BUFFER, data, drawType);
    }

    public VertexBufferObject(BufferData data, int drawType, List<Integer> strides)
    {
        super(GL_ARRAY_BUFFER, data, drawType, strides);
    }

    public VertexBufferObject(List<Integer> strides)
    {
        super(GL_ARRAY_BUFFER, strides);
    }
}