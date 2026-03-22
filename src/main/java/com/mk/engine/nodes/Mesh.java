package com.mk.engine.nodes;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


public class Mesh extends Node
{
    private float[] vertices;
    private boolean hasUv = true;

    private int vao = glGenVertexArrays();
    private int vbo = glGenBuffers();

    public Mesh(float[] vertices)
    {
        super();

        this.vertices = vertices;
    }

    public Mesh(float[] vertices, boolean hasUv)
    {
        super();

        this.vertices = vertices;
        this.hasUv = hasUv;
    }

    public Mesh(Matrix4f transform, float[] vertices)
    {
        super(transform);

        this.vertices = vertices;
    }

    public Mesh(Matrix4f transform, float[] vertices, boolean hasUv)
    {
        super(transform);

        this.vertices = vertices;
        this.hasUv = hasUv;
    }

    public float[] getVertices()
    {
        return this.vertices.clone();
    }

    @Override
    public void draw()
    {
        if (!this.shouldDraw)
        {
            return;
        }
        
        glBindVertexArray(this.vao);
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(this.vertices.length);
        buffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glDrawArrays(GL_TRIANGLES, 0, this.vertices.length / ((this.hasUv)? 5:3));

        super.draw();
    }
}