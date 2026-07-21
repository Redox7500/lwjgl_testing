package com.mk.engine.buffers.bufferobjects;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

public abstract class ElementBufferObject extends BufferObject
{
    public ElementBufferObject()              {}
    public ElementBufferObject(int dataUsage) {this.dataUsage = dataUsage;}

    public void use()
    {
        super.use(GL_ELEMENT_ARRAY_BUFFER);
    }
}