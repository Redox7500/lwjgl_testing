package com.mk.engine.buffers;

import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

public class ElementBufferObject extends BufferObject<UnsignedBufferData>
{
    public ElementBufferObject()
    {
        super(GL_ELEMENT_ARRAY_BUFFER);
    }

    public ElementBufferObject(UnsignedBufferData data, int drawType)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, data.copy(), drawType);
    }

    public ElementBufferObject(int type, UnsignedBufferData data, int drawType, List<Integer> strides)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, data.copy(), drawType, strides);
    }

    public ElementBufferObject(List<Integer> strides)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, strides);
    }

    @Override
    public UnsignedBufferData getData()
    {
        return this.data.copy();
    }

    @Override
    public void setData(UnsignedBufferData data, int drawType)
    {
        super.setData(data, drawType);
    }
}