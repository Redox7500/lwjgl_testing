package com.mk.engine.buffers;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

public class ElementBufferObject extends BufferObject<UnsignedBufferData>
{
    public ElementBufferObject()
    {
        super(GL_ELEMENT_ARRAY_BUFFER);
    }

    public ElementBufferObject(UnsignedBufferData data)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, data.copy());
    }

    public ElementBufferObject(int type, UnsignedBufferData data, List<Integer> strides)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, data.copy(), new ArrayList<>(strides));
    }

    public ElementBufferObject(List<Integer> strides)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, new ArrayList<>(strides));
    }

    @Override
    public UnsignedBufferData getData()
    {
        return super.getData().copy();
    }

    @Override
    public void setData(UnsignedBufferData data, int drawType)
    {
        super.setData(data, drawType);
    }

    @Override
    public void setVertexArrayObject(VertexArrayObject vertexArrayObject)
    {
        VertexArrayObject previousVertexArrayObject = this.getVertexArrayObject();
        if (previousVertexArrayObject != null)
        {
            previousVertexArrayObject.setElementBufferObject(null);
        }

        super.setVertexArrayObject(vertexArrayObject);
    }
}