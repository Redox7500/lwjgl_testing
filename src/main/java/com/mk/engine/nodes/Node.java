package com.mk.engine.nodes;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    public final Transform localTransform  = new Transform(this, TransformType.LOCAL);
    public final Transform globalTransform = new Transform(this, TransformType.GLOBAL);

    public boolean shouldDraw = true;

    private Node parent = null;
    private List<Node> children = new ArrayList<>();

    public Node()                    {} // be aware that things "inheriting" this empty constructor don't do super, their constructors are literally just empty
    public Node(Transform transform) {this.localTransform.copy(transform);}

    public Node setParent(Node parent)
    {
        if (this.parent != null)
        {
            this.parent.removeChild(this);
        }

        this.parent = parent;

        return this;
    }

    public Node getParent()
    {
        return this.parent;
    }

    public Node addChild(Node child)
    {
        if (!this.children.contains(child))
        {
            if (child.parent != null)
            {
                child.parent.removeChild(child);
            }
            
            child.setParent(this);
            this.children.add(child);
        }

        return this;
    }

    public Node addChildren(List<Node> children)
    {
        for (Node child:children)
        {
            this.addChild(child);
        }

        return this;
    }

    public Node removeChild(Node child)
    {
        if (this.children.contains(child))
        {
            child.parent = null;
            this.children.remove(child);
        }

        return this;
    }

    public Node removeChild(int index)
    {
        if (index < this.children.size())
        {
            this.children.get(index).parent = null;
            this.children.remove(index);
        }
        
        return this;
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