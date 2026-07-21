package com.mk.engine.buffers.bufferobjects;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;

public abstract class ElementBufferObject extends BufferObject
{
    public ElementBufferObject()              {}
    public ElementBufferObject(int dataUsage) {this.dataUsage = dataUsage;}

    public void use()
    {
        // if (this.type == GL_ELEMENT_ARRAY_BUFFER)
        // {

        // }
        super.use(GL_ELEMENT_ARRAY_BUFFER);
    }
}

// enum ElementBufferDataType
// {
//     UNSIGNED_BYTE,
//     UNSIGNED_SHORT,
//     UNSIGNED_INT
// }

// public class ElementBufferObject
// {
//     public int type;
//     public int dataUsage = GL_STATIC_DRAW;

//     protected int id = glGenBuffers();
//     protected VertexArrayObject vertexArrayObject = null;

//     private byte[]  unsignedByteData;
//     private byte[]  unsignedShortData;
//     private short[] unsignedIntData;
//     private ElementBufferDataType dataType;

//     public ElementBufferObject(int type) {this.type = type;}

//     public ElementBufferObject(int type, byte[]  data)                          {this(type); this.setData(data);}
//     public ElementBufferObject(int type, byte[]  data, boolean isUnsignedShort) {this(type); this.setData(data);}
//     public ElementBufferObject(int type, short[] data)                          {this(type); this.setData(data);}

//     public ElementBufferObject(int type, int dataUsage) {this(type); this.dataUsage = dataUsage;}

//     public ElementBufferObject(int type, byte[]  data, int dataUsage)                          {this(type, data); this.dataUsage = dataUsage;}
//     public ElementBufferObject(int type, byte[]  data, int dataUsage, boolean isUnsignedShort) {this(type, data); this.dataUsage = dataUsage;}
//     public ElementBufferObject(int type, short[] data, int dataUsage)                          {this(type, data); this.dataUsage = dataUsage;}

//     public ElementBufferObject(int type, VertexArrayObject vertexArrayObject) {this(type); this.setVertexArrayObject(vertexArrayObject);}

//     public ElementBufferObject(int type, byte[]  data, VertexArrayObject vertexArrayObject)                          {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public ElementBufferObject(int type, byte[]  data, VertexArrayObject vertexArrayObject, boolean isUnsignedShort) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public ElementBufferObject(int type, short[] data, VertexArrayObject vertexArrayObject)                          {this(type, data); this.setVertexArrayObject(vertexArrayObject);}

//     public ElementBufferObject(int type, byte[]  data, int dataUsage, VertexArrayObject vertexArrayObject)                          {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public ElementBufferObject(int type, byte[]  data, int dataUsage, VertexArrayObject vertexArrayObject, boolean isUnsignedShort) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public ElementBufferObject(int type, short[] data, int dataUsage, VertexArrayObject vertexArrayObject)                          {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}

//     public void setVertexArrayObject(VertexArrayObject vertexArrayObject)
//     {
//         this.vertexArrayObject = vertexArrayObject;
//     }

//     public void clearCurrentData()
//     {
//         switch (this.dataType)
//         {
//             case ElementBufferDataType.UNSIGNED_BYTE   -> this.unsignedByteData   = null;
//             case ElementBufferDataType.UNSIGNED_SHORT  -> this.unsignedShortData  = null;
//             case ElementBufferDataType.UNSIGNED_INT    -> this.unsignedIntData    = null;
//         }
//     }

//     public void setData(byte[]  data)                          {this.clearCurrentData(); this.unsignedByteData   = data; this.dataType = ElementBufferDataType.UNSIGNED_BYTE;}
//     public void setData(byte[]  data, boolean isUnsignedShort) {this.clearCurrentData(); this.unsignedShortData  = data; this.dataType = ElementBufferDataType.UNSIGNED_SHORT;}
//     public void setData(short[] data)                          {this.clearCurrentData(); this.unsignedIntData    = data; this.dataType = ElementBufferDataType.UNSIGNED_INT;}

//     public void use()
//     {
//         if (this.type == GL_ELEMENT_ARRAY_BUFFER)
//         {

//         }
//         glBindBuffer(this.type, this.id);
//     }
// }