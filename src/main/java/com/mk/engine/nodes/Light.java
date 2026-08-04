package com.mk.engine.nodes;

import org.joml.Vector4f;
import org.joml.Vector4fc;

public class Light extends Node
{
    public Vector4f color;

    public Light() {}

    public Light(Vector4fc color)
    {
        this.color = new Vector4f(color);
    }

    public Light(Transform transform)
    {
        super(transform);
    }

    public Light(Transform transform, Vector4fc color)
    {
        super(transform);
        
        this.color = new Vector4f(color);
    }
}