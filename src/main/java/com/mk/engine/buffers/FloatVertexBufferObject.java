package com.mk.engine.buffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferData;

public class FloatVertexBufferObject extends VertexBufferObject
{
    private float[] data;

    public FloatVertexBufferObject()                            {super();}
    public FloatVertexBufferObject(int[] strides)               {super(strides);}
    public FloatVertexBufferObject(float[] data)                {super(); this.setData(data);}
    public FloatVertexBufferObject(float[] data, int[] strides) {super(strides); this.setData(data);}

    public float[] getData()
    {
        return this.data.clone();
    }

    public void setData(float[] data)
    {
        this.data = data.clone();
    }

    @Override
    public void use()
    {
        super.use();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(this.data.length).put(this.data).flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, this.dataUsage);
    }
    
    @Override public int getDataLength() {return this.data.length;}
    @Override public int getDataType() {return GL_FLOAT;}
    @Override public int getDataTypeBytes() {return Float.BYTES;}
}