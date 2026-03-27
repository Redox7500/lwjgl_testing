package com.mk.engine.nodes;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Transform
{
    private Matrix4f matrix = new Matrix4f();
    private Node attachedNode = null;
    private TransformType type = null;

    public Transform(Matrix4fc matrix)
    {
        this.matrix = new Matrix4f(matrix);
    }

    public Transform(Transform transform)
    {
        this.matrix = new Matrix4f(transform.matrix);
        this.attachedNode = transform.attachedNode;
        this.type = transform.type;
    }

    public Transform(Node attachedNode, TransformType type)
    {
        this.attachedNode = attachedNode;
        this.type = type;
    }

    public Transform(Node attachedNode, TransformType type, Matrix4fc matrix)
    {
        this.attachedNode = attachedNode;
        this.type = type;
        this.matrix = new Matrix4f(matrix);
    }

    public Transform(Node attachedNode, TransformType type, Transform transform)
    {
        this.attachedNode = attachedNode;
        this.type = type;
        this.matrix = new Matrix4f(transform.matrix);
    }

    private void updateAttachedNode()
    {
        if (this.attachedNode == null)
        {
            return;
        }

        if (this.type == TransformType.LOCAL)
        {
            if (this.attachedNode.getParent() == null)
            {
                this.attachedNode.globalTransform.matrix = new Matrix4f(this.matrix);
            }
            else
            {
                this.attachedNode.getParent().globalTransform.matrix.mul(this.matrix, this.attachedNode.globalTransform.matrix);
            }
        }
        else
        {
            if (this.attachedNode.getParent() == null)
            {
                this.attachedNode.localTransform.matrix = new Matrix4f(this.matrix);
            }
            else
            {
                this.attachedNode.getParent().globalTransform.matrix.invert().mul(this.matrix, this.attachedNode.localTransform.matrix);
            }
        }
    }

    public Transform setMatrix(Matrix4fc matrix)
    {
        this.matrix = new Matrix4f(matrix);

        this.updateAttachedNode();
        
        return this;
    }

    public Matrix4fc getMatrix()
    {
        return this.matrix;
    }

    public Transform setTranslation(Vector3fc translation)
    {
        this.matrix.setTranslation(translation);

        this.updateAttachedNode();

        return this;
    }

    public Vector3f getTranslation()
    {
        Vector3f translation = new Vector3f();
        this.matrix.getTranslation(translation);
        return translation;
    }

    public Transform setRotation(Vector3fc rotation)
    {
        this.matrix.setRotationXYZ(rotation.x(), rotation.y(), rotation.z());

        this.updateAttachedNode();

        return this;
    }

    public Vector3f getRotation()
    {
        Vector3f rotation = new Vector3f();
        this.matrix.getEulerAnglesXYZ(rotation);
        return rotation;
    }

    public Transform setScale(Vector3fc scale)
    {
        this.matrix = new Matrix4f().translate(this.getTranslation()).rotateXYZ(this.getRotation()).scale(scale);

        this.updateAttachedNode();

        return this;
    }

    public Vector3f getScale()
    {
        Vector3f translation = new Vector3f();
        this.matrix.getScale(translation);
        return translation;
    }

    public Transform translate(Vector3fc translation)
    {
        this.matrix.translate(translation);

        this.updateAttachedNode();

        return this;
    }

    public Transform translateX(float offset)
    {
        this.matrix.translate(offset, 0, 0);

        this.updateAttachedNode();

        return this;
    }

    public Transform translateY(float offset)
    {
        this.matrix.translate(0, offset, 0);

        this.updateAttachedNode();

        return this;
    }

    public Transform translateZ(float offset)
    {
        this.matrix.translate(0, 0, offset);

        this.updateAttachedNode();

        return this;
    }

    public Transform rotate(Vector3fc rotation)
    {
        this.matrix.rotateXYZ(rotation);

        this.updateAttachedNode();

        return this;
    }

    public Transform rotateX(float angle)
    {
        this.matrix.rotateXYZ(angle, 0, 0);

        this.updateAttachedNode();

        return this;
    }

    public Transform rotateY(float angle)
    {
        this.matrix.rotateXYZ(0, angle, 0);

        this.updateAttachedNode();

        return this;
    }

    public Transform rotateZ(float angle)
    {
        this.matrix.rotateXYZ(0, 0, angle);

        this.updateAttachedNode();

        return this;
    }

    public Transform scale(Vector3fc scale)
    {
        this.matrix.scale(scale);

        this.updateAttachedNode();

        return this;
    }

    public Transform scaleX(float factor)
    {
        this.matrix.scale(factor, 0, 0);

        this.updateAttachedNode();

        return this;
    }

    public Transform scaleY(float factor)
    {
        this.matrix.scale(0, factor, 0);

        this.updateAttachedNode();

        return this;
    }

    public Transform scaleZ(float factor)
    {
        this.matrix.scale(0, 0, factor);

        this.updateAttachedNode();

        return this;
    }

    public Transform inverse()
    {
        this.matrix.invert();

        this.updateAttachedNode();

        return this;
    }

    public Transform apply(Transform transform)
    {
        transform.matrix.mul(this.matrix, this.matrix);
        
        this.updateAttachedNode();

        return this;
    }
}