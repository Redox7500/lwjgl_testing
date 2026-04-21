package com.mk.engine.buffers.data;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class SignedIntBufferData implements SignedBufferData
{
    protected int[] data;

    public SignedIntBufferData(int[] data)
    {
        this.data = data;
    }

    @Override
    public SignedIntBufferData copy()
    {
        return new SignedIntBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_INT;
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