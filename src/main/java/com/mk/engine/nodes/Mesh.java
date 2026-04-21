package com.mk.engine.nodes;

import com.mk.engine.buffers.objects.VertexArrayObject;

public class Mesh extends Node
{
    // add transform usage

    public VertexArrayObject vertexArrayObject = null;

    public Mesh(VertexArrayObject vertexArrayObject)
    {
        super();

        this.vertexArrayObject = vertexArrayObject;
    }

    public Mesh(Transform transform, VertexArrayObject vertexArrayObject)
    {
        super(transform);

        this.vertexArrayObject = vertexArrayObject;
    }

    @Override
    public void draw()
    {
        if (!this.shouldDraw)
        {
            return;
        }
        
        this.vertexArrayObject.draw();
    }
}