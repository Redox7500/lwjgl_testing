package com.mk.engine.buffers;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;
// import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
// import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

// enum ArrayBufferType
// {
//     VERTEX(GL_ARRAY_BUFFER),
//     ELEMENT(GL_ELEMENT_ARRAY_BUFFER);

//     private final int type;

//     ArrayBufferType(int type)
//     {
//         this.type = type;
//     }


//     public int get()
//     {
//         return this.type;
//     }
// };

public class BufferObject
{
    public BufferObjectType type;
    public List<Integer> strides = new ArrayList<>(List.of(1));

    private int id = glGenBuffers();
    private BufferData data;

    public BufferObject(BufferObjectType type)
    {
        this.type = type;
    }

    public BufferObject(BufferObjectType type, BufferData data, int drawType)
    {
        this.type = type;
        this.setData(data, drawType);
    }

    public BufferObject(BufferObjectType type, List<Integer> strides)
    {
        this.type = type;
        this.strides = strides;
    }

    public BufferObject(BufferObjectType type, BufferData data, int drawType, List<Integer> strides)
    {
        this.type = type;
        this.setData(data, drawType);
        this.strides = strides;
    }

    private void updateBufferData(int drawType)
    {
        this.use();
        this.data.use(this.type.value(), drawType);
    }

    public BufferData getData()
    {
        return this.data.copy();
    }

    public void setData(BufferData data, int drawType)
    {
        this.data = data.copy();
        this.updateBufferData(drawType);
    }

    public int getDataType()
    {
        return this.data.getType();
    }

    public int getFullElementStrides()
    {
        int fullElementStrides = 0;
        for (int stride:this.strides)
        {
            fullElementStrides += stride;
        }

        return fullElementStrides;
    }

    public void use()
    {
        glBindBuffer(this.type.value(), this.id);
    }
}