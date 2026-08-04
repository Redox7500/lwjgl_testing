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

    public Camera() {}

    public Camera(float fov, float aspect, float near, float far)
    {
        this.setFov(fov).setAspect(aspect).setNear(near).setFar(far);
    }

    public Camera(Matrix4fc projectionMatrix)
    {
        this.setProjectionMatrix(projectionMatrix);
    }

    public Camera(Transform transform)
    {
        super(transform);
    }

    public Camera(Transform transform, float fov, float aspect, float near, float far)
    {
        super(transform);

        this.setFov(fov).setAspect(aspect).setNear(near).setFar(far);
    }

    public Camera(Transform transform, Matrix4fc projectionMatrix)
    {
        super(transform);

        this.setProjectionMatrix(projectionMatrix);
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

    public Camera setProjectionMatrix(Matrix4fc projectionMatrix)
    {
        this.projectionMatrix = new Matrix4f(projectionMatrix);

        this.dirtyProjectionValues = true;

        return this;
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

    public Camera setFov(float fov)
    {
        this.fov = fov;

        this.dirtyProjectionMatrix = true;

        return this;
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

    public Camera setAspect(float aspect)
    {
        this.aspect = aspect;

        this.dirtyProjectionMatrix = true;

        return this;
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

    public Camera setNear(float near)
    {
        this.near = near;

        this.dirtyProjectionMatrix = true;

        return this;
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

    public Camera setFar(float far)
    {
        this.far = far;

        this.dirtyProjectionMatrix = true;

        return this;
    }
}