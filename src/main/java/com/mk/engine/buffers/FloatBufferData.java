package com.mk.engine.buffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.glBufferData;

public final class FloatBufferData implements SignedBufferData
{
    protected float[] data;

    public FloatBufferData(float[] data)
    {
        this.data = data;
    }

    @Override
    public FloatBufferData copy()
    {
        return new FloatBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_FLOAT;
    }

    @Override
    public int getTypeBytes()
    {
        return Float.BYTES;
    }

    @Override
    public int getLength()
    {
        return this.data.length;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}