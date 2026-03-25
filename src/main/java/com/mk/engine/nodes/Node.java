package com.mk.engine.nodes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4fc;

public class Node
{
    public Transform localTransform = new Transform(this, TransformType.LOCAL);
    public Transform globalTransform = new Transform(this, TransformType.GLOBAL);

    public boolean shouldDraw = true;

    private Node parent = null;
    private List<Node> children = new ArrayList<>();

    public Node()
    {

    }

    public Node(Matrix4fc transformMatrix)
    {
        this.localTransform = new Transform(this, TransformType.LOCAL, transformMatrix);
    }

    public Node(Transform transform)
    {
        this.localTransform = new Transform(this, TransformType.LOCAL, transform);
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public Node getParent()
    {
        return this.parent;
    }

    public void addChild(Node child)
    {
        if (this.children.contains(child))
        {
            return;
        }

        if (child.parent != null)
        {
            child.parent.removeChild(child);
        }
        
        child.setParent(this);
        this.children.add(child);
    }

    public void addChildren(List<Node> children)
    {
        for (Node child:children)
        {
            this.addChild(child);
        }
    }

    public void removeChild(Node child)
    {
        if (!this.children.contains(child))
        {
            return;
        }

        child.parent = null;

        this.children.remove(child);
    }

    public void removeChild(int index)
    {
        if (index >= this.children.size())
        {
            return;
        }

        this.children.get(index).parent = null;

        this.children.remove(index);
    }

    public Node getChild(int index)
    {
        if (index >= this.children.size())
        {
            return null;
        }

        return this.children.get(index);
    }

    public void draw()
    {
        if (!this.shouldDraw)
        {
            return;
        }

        for (Node child:this.children)
        {
            child.draw();
        }
    }
}