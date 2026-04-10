// package com.mk.engine.buffers;

// import java.nio.FloatBuffer;

// import org.lwjgl.BufferUtils;

// public final class FloatBufferData implements BufferData<float[]>
// {
//     private float[] data;
//     private FloatBuffer buffer;
//     private boolean dirtyBuffer = false;

//     public FloatBufferData(float[] data)
//     {
//         this.setData(data);
//     }

//     public float[] getData()
//     {
//         return this.data;
//     }

//     public void setData(float[] data)
//     {
//         this.data = data;
//         this.dirtyBuffer = true;
//     }

//     public FloatBuffer getBuffer()
//     {
//         if (this.dirtyBuffer)
//         {
//             this.buffer = BufferUtils.createFloatBuffer(this.data.length);
//             this.buffer.put(this.data).flip();

//             this.dirtyBuffer = false;
//         }

//         return buffer;
//     }
// }