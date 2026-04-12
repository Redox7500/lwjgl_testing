package com.mk.engine.buffers;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class UnsignedIntBufferData implements UnsignedBufferData
{
    protected int[] data;

    public UnsignedIntBufferData(int[] data)
    {
        this.data = data;
    }

    @Override
    public UnsignedIntBufferData copy()
    {
        return new UnsignedIntBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_UNSIGNED_INT;
    }

    @Override
    public int getTypeBytes()
    {
        return Integer.BYTES;
    }

    @Override
    public int getLength()
    {
        return this.data.length;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}