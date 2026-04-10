// package com.mk.engine.buffers;

// import java.nio.IntBuffer;

// import org.lwjgl.BufferUtils;

// public final class IntBufferData implements BufferData
// {
//     private int[] data;
//     private IntBuffer buffer;
//     private boolean dirtyBuffer = false;

//     public IntBufferData(int[] data)
//     {
//         this.setData(data);
//     }

//     public int[] getData()
//     {
//         return this.data;
//     }

//     public void setData(int[] data)
//     {
//         this.data = data;
//         this.dirtyBuffer = true;
//     }

//     public IntBuffer getBuffer()
//     {
//         if (this.dirtyBuffer)
//         {
//             this.buffer = BufferUtils.createIntBuffer(this.data.length);
//             this.buffer.put(this.data).flip();

//             this.dirtyBuffer = false;
//         }

//         return buffer;
//     }
// }