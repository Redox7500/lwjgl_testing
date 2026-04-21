package com.mk.engine.buffers.data;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class UnsignedByteBufferData implements UnsignedBufferData
{
    protected byte[] data;

    public UnsignedByteBufferData(byte[] data)
    {
        this.data = data;
    }

    @Override
    public UnsignedByteBufferData copy()
    {
        return new UnsignedByteBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_UNSIGNED_BYTE;
    }

    @Override
    public int getTypeBytes()
    {
        return Byte.BYTES;
    }

    @Override
    public int getLength()
    {
        return this.data.length;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        ByteBuffer buffer = BufferUtils.createByteBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}