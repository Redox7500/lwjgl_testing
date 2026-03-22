package com.mk.engine.nodes;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public class Camera extends Node
{
    private float fov = (float)Math.toRadians(60);
    private float aspect = 1;
    private float near = 0.1f;
    private float far = 100;
    private Matrix4f projectionMatrix = new Matrix4f().perspective(this.fov, this.aspect, this.near, this.far);


    private boolean dirtyProjectionMatrix = false;
    private boolean dirtyProjectionValues = false;

    public Camera()
    {
        super();
    }

    public Camera(Transform transform)
    {
        super(transform);
    }

    public Camera(float fov, float aspect, float near, float far)
    {
        super();

        this.fov = fov;
        this.aspect = aspect;
        this.near = near;
        this.far = far;
        
        this.dirtyProjectionMatrix = true;
    }

    public Camera(Matrix4f projectionMatrix)
    {
        super();

        this.projectionMatrix = projectionMatrix;

        this.dirtyProjectionValues = true;
    }

    public Camera(Transform transform, float fov, float aspect, float near, float far)
    {
        super(transform);

        this.fov = fov;
        this.aspect = aspect;
        this.near = near;
        this.far = far;

        this.dirtyProjectionMatrix = true;
    }

    public Camera(Transform transform, Matrix4f projectionMatrix)
    {
        super(transform);

        this.projectionMatrix = projectionMatrix;

        this.dirtyProjectionValues = true;
    }

    public Matrix4fc getViewMatrix()
    {
        Matrix4f viewMatrix = new Matrix4f();
        this.getLocalTransform().getMatrix().invert(viewMatrix);
        return viewMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix)
    {
        this.projectionMatrix = projectionMatrix;

        this.dirtyProjectionValues = true;
    }

    public Matrix4fc getProjectionMatrix()
    {
        if (this.dirtyProjectionMatrix)
        {
            this.projectionMatrix = new Matrix4f().perspective(this.fov, this.aspect, this.near, this.far);

            this.dirtyProjectionMatrix = false;
        }

        return this.projectionMatrix;
    }

    public void setFov(float fov)
    {
        this.fov = fov;

        this.dirtyProjectionMatrix = true;
    }

    public float getFov()
    {
        if (this.dirtyProjectionValues)
        {
            this.fov = 2 * (float)Math.atan(1 / this.projectionMatrix.m11());
            this.aspect = this.projectionMatrix.m11() / this.projectionMatrix.m00();
            this.near = this.projectionMatrix.m23() / (this.projectionMatrix.m22() - 1);
            this.far = this.projectionMatrix.m23() / (this.projectionMatrix.m22() + 1);

            this.dirtyProjectionValues = false;
        }

        return this.fov;
    }

    public void setAspect(float aspect)
    {
        this.aspect = aspect;

        this.dirtyProjectionMatrix = true;
    }

    public float getAspect()
    {
        if (this.dirtyProjectionValues)
        {
            this.fov = 2 * (float)Math.atan(1 / this.projectionMatrix.m11());
            this.aspect = this.projectionMatrix.m11() / this.projectionMatrix.m00();
            this.near = this.projectionMatrix.m23() / (this.projectionMatrix.m22() - 1);
            this.far = this.projectionMatrix.m23() / (this.projectionMatrix.m22() + 1);

            this.dirtyProjectionValues = false;
        }
        
        return this.aspect;
    }

    public void setNear(float near)
    {
        this.near = near;

        this.dirtyProjectionMatrix = true;
    }

    public float getNear()
    {
        if (this.dirtyProjectionValues)
        {
            this.fov = 2 * (float)Math.atan(1 / this.projectionMatrix.m11());
            this.aspect = this.projectionMatrix.m11() / this.projectionMatrix.m00();
            this.near = this.projectionMatrix.m23() / (this.projectionMatrix.m22() - 1);
            this.far = this.projectionMatrix.m23() / (this.projectionMatrix.m22() + 1);

            this.dirtyProjectionValues = false;
        }
        
        return this.near;
    }

    public void setFar(float far)
    {
        this.far = far;

        this.dirtyProjectionMatrix = true;
    }

    public float getFar()
    {
        if (this.dirtyProjectionValues)
        {
            this.fov = 2 * (float)Math.atan(1 / this.projectionMatrix.m11());
            this.aspect = this.projectionMatrix.m11() / this.projectionMatrix.m00();
            this.near = this.projectionMatrix.m23() / (this.projectionMatrix.m22() - 1);
            this.far = this.projectionMatrix.m23() / (this.projectionMatrix.m22() + 1);

            this.dirtyProjectionValues = false;
        }
        
        return this.far;
    }
}