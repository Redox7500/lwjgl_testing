// package com.mk.engine.buffers;

// import java.nio.DoubleBuffer;

// import org.lwjgl.BufferUtils;

// public final class DoubleBufferData implements BufferData<double[]>
// {
//     private double[] data;
//     private DoubleBuffer buffer;
//     private boolean dirtyBuffer = false;

//     public DoubleBufferData(double[] data)
//     {
//         this.setData(data);
//     }

//     public double[] getData()
//     {
//         return this.data;
//     }

//     public void setData(double[] data)
//     {
//         this.data = data;
//         this.dirtyBuffer = true;
//     }

//     public DoubleBuffer getBuffer()
//     {
//         if (this.dirtyBuffer)
//         {
//             this.buffer = BufferUtils.createDoubleBuffer(this.data.length);
//             this.buffer.put(this.data).flip();

//             this.dirtyBuffer = false;
//         }

//         return buffer;
//     }
// }