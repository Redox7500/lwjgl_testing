package com.mk.engine.nodes;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Transform
{
    private Matrix4f matrix = new Matrix4f();
    private Vector3f translation = new Vector3f();
    private Vector3f rotation = new Vector3f();
    private Vector3f scale = new Vector3f();

    private boolean dirtyMatrixTranslation = false;
    private boolean dirtyMatrixRotation = false;
    private boolean dirtyMatrixScale = false;
    private boolean dirtyTranslation = false;
    private boolean dirtyRotation = false;
    private boolean dirtyScale = false;

    public Transform()
    {
        
    }

    public Transform(Matrix4f matrix)
    {
        this.setMatrix(matrix);
    }

    public Transform(Vector3f translation, Vector3f rotation, Vector3f scale)
    {
        this.setTranslation(translation);
        this.setRotation(rotation);
        this.setScale(scale);
    }

    public Transform(Transform transform)
    {
        this.setMatrix(new Matrix4f(transform.getMatrix()));
    }

    public void setMatrix(Matrix4f matrix)
    {
        this.matrix = matrix;

        this.dirtyTranslation = true;
        this.dirtyRotation = true;
        this.dirtyScale = true;
    }

    public Matrix4fc getMatrix()
    {
        if (this.dirtyMatrixScale)
        {
            this.matrix.identity().translate(this.translation).rotateXYZ(this.rotation).scale(this.scale);
            
            this.dirtyMatrixTranslation = false;
            this.dirtyMatrixRotation = false;
            this.dirtyMatrixScale = false;
        }
        else
        {
            if (this.dirtyMatrixTranslation)
            {
                this.matrix.setTranslation(this.translation);

                this.dirtyMatrixTranslation = false;
            }
            if (this.dirtyMatrixRotation)
            {
                this.matrix.setRotationXYZ(this.rotation.x, this.rotation.y, this.rotation.z);

                this.dirtyMatrixRotation = false;
            }
        }

        return this.matrix;
    }

    public void setTranslation(Vector3f translation)
    {
        this.translation = translation;

        this.dirtyMatrixTranslation = true;
    }

    public Vector3fc getTranslation()
    {
        if (this.dirtyTranslation)
        {
            this.matrix.getTranslation(this.translation);
            
            this.dirtyTranslation = false;
        }

        return this.translation;
    }

    public void setRotation(Vector3f rotation)
    {
        this.rotation = rotation;

        this.dirtyMatrixRotation = true;
    }

    public Vector3fc getRotation()
    {
        if (this.dirtyRotation)
        {
            this.matrix.getEulerAnglesXYZ(this.rotation);

            this.dirtyRotation = false;
        }

        return this.rotation;
    }

    public void setScale(Vector3f scale)
    {
        this.scale = scale;

        this.dirtyMatrixScale = true;
    }

    public Vector3fc getScale()
    {
        if (this.dirtyScale)
        {
            this.matrix.getScale(this.scale);

            this.dirtyScale = false;
        }

        return this.scale;
    }

    public void translate(Vector3fc translation)
    {
        this.matrix.translate(translation);

        this.dirtyTranslation = true;
    }

    public void translateX(float offset)
    {
        this.matrix.translate(offset, 0, 0);

        this.dirtyTranslation = true;
    }

    public void translateY(float offset)
    {
        this.matrix.translate(0, offset, 0);

        this.dirtyTranslation = true;
    }

    public void translateZ(float offset)
    {
        this.matrix.translate(0, 0, offset);

        this.dirtyTranslation = true;
    }

    public void rotate(float angle, Vector3fc axis)
    {
        this.matrix.rotate(angle, axis);

        this.dirtyRotation = true;
    }

    public void rotate(Vector3fc rotation)
    {
        this.matrix.rotateXYZ(rotation);

        this.dirtyRotation = true;
    }

    public void rotateX(float angle)
    {
        this.matrix.rotateX(angle);

        this.dirtyRotation = true;
    }

    public void rotateY(float angle)
    {
        this.matrix.rotateY(angle);

        this.dirtyRotation = true;
    }

    public void rotateZ(float angle)
    {
        this.matrix.rotateZ(angle);

        this.dirtyRotation = true;
    }

    public void scale(Vector3fc scale)
    {
        this.matrix.scale(scale);

        this.dirtyScale = true;
    }

    public void scaleX(float factor)
    {
        this.matrix.scale(factor, 0, 0);

        this.dirtyScale = true;
    }

    public void scaleY(float factor)
    {
        this.matrix.scale(0, factor, 0);

        this.dirtyScale = true;
    }

    public void scaleZ(float factor)
    {
        this.matrix.scale(0, 0, factor);

        this.dirtyScale = true;
    }

    public void apply(Transform transform)
    {
        Matrix4f transformedMatrix = new Matrix4f();
        transform.getMatrix().mul(this.getMatrix(), transformedMatrix);
        transform.setMatrix(transformedMatrix);
    }
}