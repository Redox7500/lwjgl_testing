package com.mk.engine.buffers;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public abstract class VertexBufferObject extends BufferObject
{
    private int[] strides = {1};

    public VertexBufferObject()              {}
    public VertexBufferObject(int[] strides) {this.strides = strides;}

    public void use()
    {
        super.use(GL_ARRAY_BUFFER);
    }
    
    public int[] getStrides() {return this.strides.clone();}
    public int getTotalStrides()
    {
        int totalStrides = 0;
        for (int i:this.strides)
        {
            totalStrides += i;
        }
        return totalStrides;
    }
}