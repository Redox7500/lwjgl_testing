package com.mk.engine.nodes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

public class Node
{
    private Transform localTransform = new Transform();
    private Transform globalTransform = new Transform();

    public boolean shouldDraw = true;

    private Node parent = null;
    private List<Node> children = new ArrayList<>();

    public Node()
    {

    }

    public Node(Transform transform)
    {
        this.localTransform = transform;
    }

    public void setLocalTransform(Transform transform)
    {
        this.localTransform = transform;
        this.updateGlobalTransform();
    }

    public Transform getLocalTransform()
    {
        return this.localTransform;
    }

    public void setGlobalTransform(Transform transform)
    {
        this.globalTransform = transform;

        Matrix4f parentGlobalTransformInverse = new Matrix4f();
        this.parent.globalTransform.getMatrix().invert(parentGlobalTransformInverse);

        this.localTransform.setMatrix(parentGlobalTransformInverse.mul(this.globalTransform.getMatrix()));
    }

    public Transform getGlobalTransform()
    {
        return this.globalTransform;
    }

    public void updateGlobalTransform()
    {
        if (this.parent == null)
        {
            this.globalTransform.setMatrix(new Matrix4f(this.localTransform.getMatrix()));
        }
        else
        {
            this.globalTransform.setMatrix(new Matrix4f(this.parent.globalTransform.getMatrix()).mul(this.localTransform.getMatrix()));
        }
    }

    public void setParent(Node parent)
    {
        this.parent = parent;

        this.updateGlobalTransform();
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