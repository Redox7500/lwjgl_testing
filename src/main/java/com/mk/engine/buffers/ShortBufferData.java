// package com.mk.engine.buffers;

// import java.nio.ShortBuffer;

// import org.lwjgl.BufferUtils;

// public final class ShortBufferData implements BufferData
// {
//     private short[] data;
//     private ShortBuffer buffer;
//     private boolean dirtyBuffer = false;

//     public ShortBufferData(short[] data)
//     {
//         this.setData(data);
//     }

//     public short[] getData()
//     {
//         return this.data;
//     }

//     public void setData(short[] data)
//     {
//         this.data = data;
//         this.dirtyBuffer = true;
//     }

//     public ShortBuffer getBuffer()
//     {
//         if (this.dirtyBuffer)
//         {
//             this.buffer = BufferUtils.createShortBuffer(this.data.length);
//             this.buffer.put(this.data).flip();

//             this.dirtyBuffer = false;
//         }

//         return buffer;
//     }
// }