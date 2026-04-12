package com.mk.engine.buffers;

public sealed interface UnsignedBufferData extends BufferData
    permits UnsignedByteBufferData, UnsignedShortBufferData, UnsignedIntBufferData
{
    public static UnsignedByteBufferData of(byte[] value) {return new UnsignedByteBufferData(value);}
    public static UnsignedShortBufferData of(short[] value) {return new UnsignedShortBufferData(value);}
    public static UnsignedIntBufferData of(int[] value) {return new UnsignedIntBufferData(value);}

    @Override
    public UnsignedBufferData copy();
}