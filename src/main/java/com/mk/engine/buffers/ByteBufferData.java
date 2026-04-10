// package com.mk.engine.buffers;

// import java.nio.ByteBuffer;

// import org.lwjgl.BufferUtils;

// public final class ByteBufferData implements BufferData
// {
//     private byte[] data;
//     private ByteBuffer buffer;
//     private boolean dirtyBuffer = false;

//     public ByteBufferData(byte[] data)
//     {
//         this.setData(data);
//     }

//     public byte[] getData()
//     {
//         return this.data;
//     }

//     public void setData(byte[] data)
//     {
//         this.data = data;
//         this.dirtyBuffer = true;
//     }

//     public ByteBuffer getBuffer()
//     {
//         if (this.dirtyBuffer)
//         {
//             this.buffer = BufferUtils.createByteBuffer(this.data.length);
//             this.buffer.put(this.data).flip();

//             this.dirtyBuffer = false;
//         }

//         return buffer;
//     }
// }