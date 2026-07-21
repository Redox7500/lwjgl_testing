package com.mk.engine.buffers.data;

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

// public class BufferData<T extends Number>
// {

// }

// enum BufferDataType
// {
//     BYTE,
//     SHORT,
//     INT,
//     FLOAT,
//     DOUBLE
// }

// public class BufferData
// {
//     private byte[]   byteData;
//     private short[]  shortData;
//     private int[]    intData;
//     private float[]  floatData;
//     private double[] doubleData;
//     private BufferDataType type;

//     public BufferData(byte[] data)
//     {
//         this.setData(data);
//     }

//     public BufferData(short[] data)
//     {
//         this.setData(data);
//     }

//     public BufferData(int[] data)
//     {
//         this.setData(data);
//     }

//     public BufferData(float[] data)
//     {
//         this.setData(data);
//     }

//     public BufferData(double[] data)
//     {
//         this.setData(data);
//     }

//     public void clearCurrentData()
//     {
//         switch (this.type)
//         {
//             case BufferDataType.BYTE   -> this.byteData   = null;
//             case BufferDataType.SHORT  -> this.shortData  = null;
//             case BufferDataType.INT    -> this.intData    = null;
//             case BufferDataType.FLOAT  -> this.floatData  = null;
//             case BufferDataType.DOUBLE -> this.doubleData = null;
//         }
//     }

//     public  getData()
//     {

//     }

//     public void setData(byte[] data)
//     {
//         this.clearCurrentData();

//         this.byteData = data.clone();
//         this.type = BufferDataType.BYTE;
//     }

//     public void setData(short[] data)
//     {
//         this.clearCurrentData();

//         this.shortData = data.clone();
//         this.type = BufferDataType.SHORT;
//     }

//     public void setData(int[] data)
//     {
//         this.clearCurrentData();

//         this.intData = data.clone();
//         this.type = BufferDataType.INT;
//     }

//     public void setData(float[] data)
//     {
//         this.clearCurrentData();

//         this.floatData = data.clone();
//         this.type = BufferDataType.FLOAT;
//     }

//     public void setData(double[] data)
//     {
//         this.clearCurrentData();

//         this.doubleData = data.clone();
//         this.type = BufferDataType.DOUBLE;
//     }
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