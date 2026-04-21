package com.mk.engine.buffers.bufferobjects;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

import com.mk.engine.buffers.data.UnsignedBufferData;
import com.mk.engine.buffers.objects.VertexArrayObject;

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

    public ElementBufferObject(int dataUsage)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, dataUsage);
    }

    public ElementBufferObject(UnsignedBufferData data, int dataUsage)
    {
        super(GL_ELEMENT_ARRAY_BUFFER, data.copy(), dataUsage);
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

        if (this.vertexArrayObject != null)
        {
            this.vertexArrayObject.updateElementBufferObjectData();
        }
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