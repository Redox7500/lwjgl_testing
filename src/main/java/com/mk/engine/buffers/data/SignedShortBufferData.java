package com.mk.engine.buffers.data;

import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class SignedShortBufferData implements SignedBufferData
{
    protected short[] data;

    public SignedShortBufferData(short[] data)
    {
        this.data = data;
    }

    @Override
    public SignedShortBufferData copy()
    {
        return new SignedShortBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_SHORT;
    }

    @Override
    public int getTypeBytes()
    {
        return Short.BYTES;
    }

    @Override
    public int getLength()
    {
        return this.data.length;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        ShortBuffer buffer = BufferUtils.createShortBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}