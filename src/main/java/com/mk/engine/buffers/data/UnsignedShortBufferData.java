package com.mk.engine.buffers.data;

import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class UnsignedShortBufferData implements UnsignedBufferData
{
    protected short[] data;

    public UnsignedShortBufferData(short[] data)
    {
        this.data = data;
    }

    @Override
    public UnsignedShortBufferData copy()
    {
        return new UnsignedShortBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_UNSIGNED_SHORT;
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