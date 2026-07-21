package com.mk.engine.buffers.bufferobjects;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferData;

public class UnsignedByteVertexBufferObject extends VertexBufferObject
{
    byte[] data;

    public UnsignedByteVertexBufferObject()                                           {super();}
    public UnsignedByteVertexBufferObject(int dataUsage)                              {super(dataUsage);}
    public UnsignedByteVertexBufferObject(int[] strides)                              {super(strides);}
    public UnsignedByteVertexBufferObject(int dataUsage, int[] strides)               {super(dataUsage, strides);}

    public UnsignedByteVertexBufferObject(byte[] data)                                {super(); this.setData(data);}
    public UnsignedByteVertexBufferObject(byte[] data, int dataUsage)                 {super(dataUsage); this.setData(data);}
    public UnsignedByteVertexBufferObject(byte[] data, int[] strides)                 {super(strides); this.setData(data);}
    public UnsignedByteVertexBufferObject(byte[] data, int dataUsage, int[] strides)  {super(dataUsage, strides); this.setData(data);}

    public UnsignedByteVertexBufferObject(short[] data)                               {super(); this.setData(data);}
    public UnsignedByteVertexBufferObject(short[] data, int dataUsage)                {super(dataUsage); this.setData(data);}
    public UnsignedByteVertexBufferObject(short[] data, int[] strides)                {super(strides); this.setData(data);}
    public UnsignedByteVertexBufferObject(short[] data, int dataUsage, int[] strides) {super(dataUsage, strides); this.setData(data);}

    public byte[] getData()
    {
        return this.data.clone();
    }

    public void setData(byte[] data)
    {
        this.data = data.clone();
    }

    public void setData(short[] data)
    {
        int length = data.length;

        byte[] byteData = new byte[length];
        for (int i = 0; i < length; i++)
        {
            byteData[i] = (byte)data[i];
        }

        this.setData(byteData);
    }

    @Override
    public void use()
    {
        super.use();
        ByteBuffer buffer = BufferUtils.createByteBuffer(this.data.length).put(this.data).flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, this.dataUsage);
    }

    @Override public int getDataLength() {return this.data.length;}
    @Override public int getDataType() {return GL_UNSIGNED_BYTE;}
    @Override public int getDataTypeBytes() {return Byte.BYTES;}
}