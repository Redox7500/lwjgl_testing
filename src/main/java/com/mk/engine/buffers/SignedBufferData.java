package com.mk.engine.buffers;

public sealed interface SignedBufferData extends BufferData
    permits SignedByteBufferData, SignedShortBufferData, SignedIntBufferData, FloatBufferData, DoubleBufferData
{
    public static SignedByteBufferData of(byte[] value) {return new SignedByteBufferData(value);}
    public static SignedShortBufferData of(short[] value) {return new SignedShortBufferData(value);}
    public static SignedIntBufferData of(int[] value) {return new SignedIntBufferData(value);}
    public static FloatBufferData of(float[] value) {return new FloatBufferData(value);}
    public static DoubleBufferData of(double[] value) {return new DoubleBufferData(value);}

    @Override
    public SignedBufferData copy();
}