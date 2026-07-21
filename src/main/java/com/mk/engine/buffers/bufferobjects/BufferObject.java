package com.mk.engine.buffers.bufferobjects;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import com.mk.engine.buffers.vertexarrayobjects.VertexArrayObject;

public abstract class BufferObject
{
    public int dataUsage = GL_STATIC_DRAW;

    protected int id = glGenBuffers();
    protected VertexArrayObject vertexArrayObject = null;

    public BufferObject()              {}
    public BufferObject(int dataUsage) {this.dataUsage = dataUsage;}

    public void use(int type) {glBindBuffer(type, this.id);}

    public abstract int getDataLength();
    public abstract int getDataType();
    public abstract int getDataTypeBytes();
}

// enum BufferDataType
// {
//     BYTE,
//     SHORT,
//     INT,
//     FLOAT,
//     DOUBLE,
//     // UNSIGNED_BYTE,
//     // UNSIGNED_SHORT,
//     // UNSIGNED_INT
// }

// public abstract class BufferObject
// {
//     public int type;
//     public int dataUsage = GL_STATIC_DRAW;

//     protected int id = glGenBuffers();
//     protected VertexArrayObject vertexArrayObject = null;

//     private byte[]   byteData;
//     private short[]  shortData;
//     private int[]    intData;
//     private float[]  floatData;
//     private double[] doubleData;
//     // private byte[]   unsignedByteData;
//     // private byte[]   unsignedShortData;
//     // private short[]  unsignedIntData;
//     private BufferDataType dataType;

//     public BufferObject(int type) {this.type = type;}

//     public BufferObject(int type, byte[]   data) {this(type); this.setData(data);}
//     public BufferObject(int type, short[]  data) {this(type); this.setData(data);}
//     public BufferObject(int type, int[]    data) {this(type); this.setData(data);}
//     public BufferObject(int type, float[]  data) {this(type); this.setData(data);}
//     public BufferObject(int type, double[] data) {this(type); this.setData(data);}

//     public BufferObject(int type, int dataUsage) {this(type); this.dataUsage = dataUsage;}

//     public BufferObject(int type, byte[]   data, int dataUsage) {this(type, data); this.dataUsage = dataUsage;}
//     public BufferObject(int type, short[]  data, int dataUsage) {this(type, data); this.dataUsage = dataUsage;}
//     public BufferObject(int type, int[]    data, int dataUsage) {this(type, data); this.dataUsage = dataUsage;}
//     public BufferObject(int type, float[]  data, int dataUsage) {this(type, data); this.dataUsage = dataUsage;}
//     public BufferObject(int type, double[] data, int dataUsage) {this(type, data); this.dataUsage = dataUsage;}

//     public BufferObject(int type, VertexArrayObject vertexArrayObject) {this(type); this.setVertexArrayObject(vertexArrayObject);}

//     public BufferObject(int type, byte[]   data, VertexArrayObject vertexArrayObject) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, short[]  data, VertexArrayObject vertexArrayObject) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, int[]    data, VertexArrayObject vertexArrayObject) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, float[]  data, VertexArrayObject vertexArrayObject) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, double[] data, VertexArrayObject vertexArrayObject) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}

//     public BufferObject(int type, byte[]   data, int dataUsage, VertexArrayObject vertexArrayObject) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, short[]  data, int dataUsage, VertexArrayObject vertexArrayObject) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, int[]    data, int dataUsage, VertexArrayObject vertexArrayObject) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, float[]  data, int dataUsage, VertexArrayObject vertexArrayObject) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public BufferObject(int type, double[] data, int dataUsage, VertexArrayObject vertexArrayObject) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}

//     public void setVertexArrayObject(VertexArrayObject vertexArrayObject)
//     {
//         this.vertexArrayObject = vertexArrayObject;
//     }

//     public void clearCurrentData()
//     {
//         switch (this.dataType)
//         {
//             case BufferDataType.BYTE   -> this.byteData   = null;
//             case BufferDataType.SHORT  -> this.shortData  = null;
//             case BufferDataType.INT    -> this.intData    = null;
//             case BufferDataType.FLOAT  -> this.floatData  = null;
//             case BufferDataType.DOUBLE -> this.doubleData = null;
//         }
//     }

//     public void setData(byte[]   data) {this.clearCurrentData(); this.byteData   = data; this.dataType = BufferDataType.BYTE;}
//     public void setData(short[]  data) {this.clearCurrentData(); this.shortData  = data; this.dataType = BufferDataType.SHORT;}
//     public void setData(int[]    data) {this.clearCurrentData(); this.intData    = data; this.dataType = BufferDataType.INT;}
//     public void setData(float[]  data) {this.clearCurrentData(); this.floatData  = data; this.dataType = BufferDataType.FLOAT;}
//     public void setData(double[] data) {this.clearCurrentData(); this.doubleData = data; this.dataType = BufferDataType.DOUBLE;}

//     public void use()
//     {
//         if (this.type == GL_ELEMENT_ARRAY_BUFFER)
//         {

//         }
//         glBindBuffer(this.type, this.id);
//     }
// }