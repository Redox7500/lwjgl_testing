package com.mk.engine.buffers.bufferobjects;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public abstract class VertexBufferObject extends BufferObject
{
    private int[] strides = {1};

    public VertexBufferObject()                             {}
    public VertexBufferObject(int dataUsage)                {this.dataUsage = dataUsage;}
    public VertexBufferObject(int[] strides)                {this.strides = strides;}
    public VertexBufferObject(int dataUsage, int[] strides) {this(dataUsage); this.strides = strides;}

    public void use()
    {
        // if (this.type == GL_ELEMENT_ARRAY_BUFFER)
        // {

        // }
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

// public class VertexBufferObject
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
//     private byte[]   unsignedByteData;
//     private byte[]   unsignedShortData;
//     private short[]  unsignedIntData;
//     private VertexBufferDataType dataType;

//     public VertexBufferObject(int type) {this.type = type;}

//     public VertexBufferObject(int type, byte[]   data)                                              {this(type); this.setData(data);}
//     public VertexBufferObject(int type, short[]  data)                                              {this(type); this.setData(data);}
//     public VertexBufferObject(int type, int[]    data)                                              {this(type); this.setData(data);}
//     public VertexBufferObject(int type, float[]  data)                                              {this(type); this.setData(data);}
//     public VertexBufferObject(int type, double[] data)                                              {this(type); this.setData(data);}
//     public VertexBufferObject(int type, byte[]   data, boolean isUnsigned)                          {this(type); this.setData(data);}
//     public VertexBufferObject(int type, byte[]   data, boolean isUnsigned, boolean isUnsignedShort) {this(type); this.setData(data);}
//     public VertexBufferObject(int type, int[]    data, boolean isUnsigned)                          {this(type); this.setData(data);}

//     public VertexBufferObject(int type, int dataUsage) {this(type); this.dataUsage = dataUsage;}

//     public VertexBufferObject(int type, byte[]   data, int dataUsage)                                              {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, short[]  data, int dataUsage)                                              {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, int[]    data, int dataUsage)                                              {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, float[]  data, int dataUsage)                                              {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, double[] data, int dataUsage)                                              {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, byte[]   data, int dataUsage, boolean isUnsigned)                          {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, byte[]   data, int dataUsage, boolean isUnsigned, boolean isUnsignedShort) {this(type, data); this.dataUsage = dataUsage;}
//     public VertexBufferObject(int type, short[]  data, int dataUsage, boolean isUnsigned)                          {this(type, data); this.dataUsage = dataUsage;}

//     public VertexBufferObject(int type, VertexArrayObject vertexArrayObject) {this(type); this.setVertexArrayObject(vertexArrayObject);}

//     public VertexBufferObject(int type, byte[]   data, VertexArrayObject vertexArrayObject)                                              {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, short[]  data, VertexArrayObject vertexArrayObject)                                              {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, int[]    data, VertexArrayObject vertexArrayObject)                                              {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, float[]  data, VertexArrayObject vertexArrayObject)                                              {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, double[] data, VertexArrayObject vertexArrayObject)                                              {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, byte[]   data, VertexArrayObject vertexArrayObject, boolean isUnsigned)                          {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, byte[]   data, VertexArrayObject vertexArrayObject, boolean isUnsigned, boolean isUnsignedShort) {this(type, data); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, short[]  data, VertexArrayObject vertexArrayObject, boolean isUnsigned)                          {this(type, data); this.setVertexArrayObject(vertexArrayObject);}

//     public VertexBufferObject(int type, byte[]   data, int dataUsage, VertexArrayObject vertexArrayObject)                                              {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, short[]  data, int dataUsage, VertexArrayObject vertexArrayObject)                                              {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, int[]    data, int dataUsage, VertexArrayObject vertexArrayObject)                                              {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, float[]  data, int dataUsage, VertexArrayObject vertexArrayObject)                                              {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, double[] data, int dataUsage, VertexArrayObject vertexArrayObject)                                              {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, byte[]   data, int dataUsage, VertexArrayObject vertexArrayObject, boolean isUnsigned)                          {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, byte[]   data, int dataUsage, VertexArrayObject vertexArrayObject, boolean isUnsigned, boolean isUnsignedShort) {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}
//     public VertexBufferObject(int type, short[]  data, int dataUsage, VertexArrayObject vertexArrayObject, boolean isUnsigned)                          {this(type, data, dataUsage); this.setVertexArrayObject(vertexArrayObject);}

//     public void setVertexArrayObject(VertexArrayObject vertexArrayObject)
//     {
//         this.vertexArrayObject = vertexArrayObject;
//     }

//     public void setData(byte[]   data) {this.clearCurrentData(); this.byteData   = data; this.dataType = VertexBufferDataType.BYTE;}
//     public void setData(short[]  data) {this.clearCurrentData(); this.shortData  = data; this.dataType = VertexBufferDataType.SHORT;}
//     public void setData(int[]    data) {this.clearCurrentData(); this.intData    = data; this.dataType = VertexBufferDataType.INT;}
//     public void setData(float[]  data) {this.clearCurrentData(); this.floatData  = data; this.dataType = VertexBufferDataType.FLOAT;}
//     public void setData(double[] data) {this.clearCurrentData(); this.doubleData = data; this.dataType = VertexBufferDataType.DOUBLE;}

//     public void use()
//     {
//         if (this.type == GL_ELEMENT_ARRAY_BUFFER)
//         {

//         }
//         glBindBuffer(this.type, this.id);
//     }
// }