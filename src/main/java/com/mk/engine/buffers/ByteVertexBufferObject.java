package com.mk.engine.buffers;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferData;

public class ByteVertexBufferObject extends VertexBufferObject
{
    byte[] data;

    public ByteVertexBufferObject()                                           {super();}
    public ByteVertexBufferObject(int[] strides)                              {super(strides);}
    public ByteVertexBufferObject(byte[] data)                                {super(); this.setData(data);}
    public ByteVertexBufferObject(byte[] data, int[] strides)                 {super(strides); this.setData(data);}

    public byte[] getData()
    {
        return this.data.clone();
    }

    public void setData(byte[] data)
    {
        this.data = data.clone();
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