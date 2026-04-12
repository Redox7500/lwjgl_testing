package com.mk.engine.buffers;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class DoubleBufferData implements SignedBufferData
{
    protected double[] data;

    public DoubleBufferData(double[] data)
    {
        this.data = data;
    }

    @Override
    public DoubleBufferData copy()
    {
        return new DoubleBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_DOUBLE;
    }

    @Override
    public int getTypeBytes()
    {
        return Double.BYTES;
    }

    @Override
    public int getLength()
    {
        return this.data.length;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}