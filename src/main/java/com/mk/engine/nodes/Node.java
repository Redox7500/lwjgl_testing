package com.mk.engine.nodes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4fc;

public class Node
{
    public Transform localTransform  = new Transform(this, TransformType.LOCAL);
    public Transform globalTransform = new Transform(this, TransformType.GLOBAL);

    public boolean shouldDraw = true;

    private Node parent = null;
    private List<Node> children = new ArrayList<>();

    public Node()                                                            {}
    public Node(Matrix4fc transformMatrix)                                   {this.localTransform = new Transform(this, TransformType.LOCAL, transformMatrix);}
    public Node(Transform transform)                                         {this.localTransform = new Transform(this, TransformType.LOCAL, transform);}

    public Node(Node parent)                                                 {this.setParent(parent);}
    public Node(Matrix4fc transformMatrix, Node parent)                      {this(transformMatrix); this.setParent(parent);}
    public Node(Transform transform, Node parent)                            {this(transform); this.setParent(parent);}

    public Node(List<Node> children)                                         {this.addChildren(children);}
    public Node(Matrix4fc transformMatrix, List<Node> children)              {this(transformMatrix); this.addChildren(children);}
    public Node(Transform transform, List<Node> children)                    {this(transform); this.addChildren(children);}

    public Node(Node parent, List<Node> children)                            {this(parent); this.addChildren(children);}
    public Node(Matrix4fc transformMatrix, Node parent, List<Node> children) {this(transformMatrix, parent); this.addChildren(children);}
    public Node(Transform transform, Node parent, List<Node> children)       {this(transform, parent); this.addChildren(children);}

    public void setParent(Node parent)
    {
        if (this.parent != null)
        {
            this.parent.removeChild(this);
        }

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