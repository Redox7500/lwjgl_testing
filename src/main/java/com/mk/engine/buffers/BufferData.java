package com.mk.engine.buffers;
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
    permits SignedBufferData, UnsignedBufferData
{
    public static SignedByteBufferData ofSigned(byte[] value) {return new SignedByteBufferData(value);}
    public static SignedShortBufferData ofSigned(short[] value) {return new SignedShortBufferData(value);}
    public static SignedIntBufferData ofSigned(int[] value) {return new SignedIntBufferData(value);}
    public static FloatBufferData ofSigned(float[] value) {return new FloatBufferData(value);}
    public static DoubleBufferData ofSigned(double[] value) {return new DoubleBufferData(value);}
    public static UnsignedByteBufferData ofUnsigned(byte[] value) {return new UnsignedByteBufferData(value);}
    public static UnsignedShortBufferData ofUnsigned(short[] value) {return new UnsignedShortBufferData(value);}
    public static UnsignedIntBufferData ofUnsigned(int[] value) {return new UnsignedIntBufferData(value);}
    
    public BufferData copy();
    public int getType();
    public int getTypeBytes();
    public int getLength();
    public void use(int bufferObjectType, int drawType);
}

// somehow make it so that you can more easily change the buffer data instead of making a whole new buffer?

// somehow add dirty flag for regenerating buffer?