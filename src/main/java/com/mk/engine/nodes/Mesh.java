package com.mk.engine.nodes;

import com.mk.engine.buffers.VertexArrayObject;

public class Mesh extends Node
{
    // add transform usage

    public VertexArrayObject vertexArrayObject = null;
    
    public Mesh() {}

    public Mesh(VertexArrayObject vertexArrayObject)
    {
        this.vertexArrayObject = vertexArrayObject;
    }

    public Mesh(Transform transform)
    {
        super(transform);
    }

    public Mesh(Transform transform, VertexArrayObject vertexArrayObject)
    {
        super(transform);

        this.vertexArrayObject = vertexArrayObject;
    }

    @Override
    public void draw()
    {
        if (!this.shouldDraw || this.vertexArrayObject == null)
        {
            return;
        }
        
        this.vertexArrayObject.draw();
    }
}