package com.mk.engine.buffers;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public abstract class BufferObject
{
    public int dataUsage = GL_STATIC_DRAW;

    private int id = glGenBuffers();

    public BufferObject() {}

    public void use(int type) {glBindBuffer(type, this.id);}

    public abstract int getDataLength();
    public abstract int getDataType();
    public abstract int getDataTypeBytes();
}