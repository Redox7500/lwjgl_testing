package com.mk.engine.buffers;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

public enum BufferObjectType
{
    VERTEX(GL_ARRAY_BUFFER),
    ELEMENT(GL_ELEMENT_ARRAY_BUFFER);

    private final int type;

    BufferObjectType(int type)
    {
        this.type = type;
    }

    public int value()
    {
        return this.type;
    }
}