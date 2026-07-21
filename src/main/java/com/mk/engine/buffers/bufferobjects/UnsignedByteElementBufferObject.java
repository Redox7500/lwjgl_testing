package com.mk.engine.buffers.bufferobjects;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferData;

public class UnsignedByteElementBufferObject extends ElementBufferObject
{
    byte[] data;

    public UnsignedByteElementBufferObject()                            {super();}
    public UnsignedByteElementBufferObject(int dataUsage)               {super(dataUsage);}

    public UnsignedByteElementBufferObject(byte[] data)                 {super(); this.setData(data);}
    public UnsignedByteElementBufferObject(byte[] data, int dataUsage)  {super(dataUsage); this.setData(data);}

    public UnsignedByteElementBufferObject(short[] data)                {super(); this.setData(data);}
    public UnsignedByteElementBufferObject(short[] data, int dataUsage) {super(dataUsage); this.setData(data);}

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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, this.dataUsage);
    }

    @Override public int getDataLength() {return this.data.length;}
    @Override public int getDataType() {return GL_UNSIGNED_BYTE;}
    @Override public int getDataTypeBytes() {return Byte.BYTES;}
}