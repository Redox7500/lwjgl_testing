package com.mk.engine.buffers;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_BYTE;
import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL15.glBufferData;

public sealed interface BufferData
    permits FloatBufferData, DoubleBufferData, ByteBufferData, ShortBufferData, IntBufferData
{
    public static BufferData of(float[] value) {return new FloatBufferData(value);}
    public static BufferData of(double[] value) {return new DoubleBufferData(value);}
    public static BufferData of(byte[] value) {return new ByteBufferData(value);}
    public static BufferData of(short[] value) {return new ShortBufferData(value);}
    public static BufferData of(int[] value) {return new IntBufferData(value);}

    public static int getDataLength(BufferData bufferData)
    {
        return switch (bufferData)
        {
            case FloatBufferData data -> data.data.length;
            case DoubleBufferData data -> data.data.length;
            case ByteBufferData data -> data.data.length;
            case ShortBufferData data -> data.data.length;
            case IntBufferData data -> data.data.length;
            default -> 0;
        };
    }

    public static int bytesOfType(int type)
    {
        return switch (type)
        {
            case GL_FLOAT -> Float.BYTES;
            case GL_DOUBLE -> Double.BYTES;
            case GL_BYTE -> Byte.BYTES; // dumb
            case GL_SHORT -> Short.BYTES;
            case GL_INT -> Integer.BYTES;
            default -> 0; // wtf
        };
    }
    
    public BufferData copy();
    public int getType();
    public void use(int bufferObjectType, int drawType);
}

// somehow make it so that you can more easily change the buffer data instead of making a whole new buffer?

// somehow add dirty flag for regenerating buffer?

final class FloatBufferData implements BufferData
{
    public float[] data;

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
    public void use(int bufferObjectType, int drawType)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}

final class DoubleBufferData implements BufferData
{
    public double[] data;

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
    public void use(int bufferObjectType, int drawType)
    {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}

final class ByteBufferData implements BufferData
{
    public byte[] data;

    public ByteBufferData(byte[] data)
    {
        this.data = data;
    }

    @Override
    public ByteBufferData copy()
    {
        return new ByteBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_BYTE;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        ByteBuffer buffer = BufferUtils.createByteBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}

final class ShortBufferData implements BufferData
{
    public short[] data;

    public ShortBufferData(short[] data)
    {
        this.data = data;
    }

    @Override
    public ShortBufferData copy()
    {
        return new ShortBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_SHORT;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        ShortBuffer buffer = BufferUtils.createShortBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}

final class IntBufferData implements BufferData
{
    public int[] data;

    public IntBufferData(int[] data)
    {
        this.data = data;
    }

    @Override
    public IntBufferData copy()
    {
        return new IntBufferData(this.data.clone());
    }

    @Override
    public int getType()
    {
        return GL_INT;
    }

    @Override
    public void use(int bufferObjectType, int drawType)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(this.data.length);
        glBufferData(bufferObjectType, buffer.put(this.data).flip(), drawType);
    }
}