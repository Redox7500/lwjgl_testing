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
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL15.glBufferData;

// public class BufferData<T> extends Object
// {
//     private final UnaryOperator<T> copier = array -> array.clone();
//     private T data;

//     public T getData()
//     {
//         return copier.apply(data);
//     }

//     public void setData(T data)
//     {
//         this.data = copier.apply(data);
//     }
// }

// public class BufferData
// {
//     private byte[] byteArray;
//     private short[] shortArray;
//     private int[] intArray;
//     private float[] floatArray;
//     private double[] doubleArray;

//     public BufferData(byte[] byteArray)
//     {
//         this.setData(byteArray);
//     }

//     public Object
// }

public sealed interface BufferData
    permits ByteBufferData, ShortBufferData, IntBufferData, FloatBufferData, DoubleBufferData, UnsignedByteBufferData, UnsignedShortBufferData, UnsignedIntBufferData
{
    public static BufferData of(byte[] value) {return new ByteBufferData(value);}
    public static BufferData of(short[] value) {return new ShortBufferData(value);}
    public static BufferData of(int[] value) {return new IntBufferData(value);}
    public static BufferData of(float[] value) {return new FloatBufferData(value);}
    public static BufferData of(double[] value) {return new DoubleBufferData(value);}
    public static BufferData ofUnsigned(byte[] value) {return new UnsignedByteBufferData(value);}
    public static BufferData ofUnsigned(short[] value) {return new UnsignedShortBufferData(value);}
    public static BufferData ofUnsigned(int[] value) {return new UnsignedIntBufferData(value);}
    
    public BufferData copy();
    public int getType();
    public int getTypeBytes();
    public int getLength();
    public void use(int bufferObjectType, int drawType);
}

// somehow make it so that you can more easily change the buffer data instead of making a whole new buffer?

// somehow add dirty flag for regenerating buffer?

final class ByteBufferData implements BufferData
{
    byte[] data;

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

final class ShortBufferData implements BufferData
{
    short[] data;

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

final class IntBufferData implements BufferData
{
    int[] data;

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

final class FloatBufferData implements BufferData
{
    float[] data;

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

final class DoubleBufferData implements BufferData
{
    double[] data;

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

final class UnsignedByteBufferData implements BufferData
{
    byte[] data;

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

final class UnsignedShortBufferData implements BufferData
{
    short[] data;

    public UnsignedShortBufferData(short[] data)
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

final class UnsignedIntBufferData implements BufferData
{
    int[] data;

    public UnsignedIntBufferData(int[] data)
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