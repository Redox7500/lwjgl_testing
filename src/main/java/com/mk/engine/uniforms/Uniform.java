package com.mk.engine.uniforms;

import java.nio.FloatBuffer;
import java.util.Map;

import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public sealed interface Uniform
    permits I, V3F, V4F, M4F
{
    public static void set(int location, int value) {glUniform1i(location, value);}
    public static void set(int location, Vector3fc value) {glUniform3f(location, value.x(), value.y(), value.z());}
    public static void set(int location, Vector4fc value) {glUniform4f(location, value.x(), value.y(), value.z(), value.w());}
    public static void set(int location, Matrix4fc value) {FloatBuffer buffer = BufferUtils.createFloatBuffer(16); value.get(buffer); glUniformMatrix4fv(location, false, buffer);}

    public static void set(int location, Uniform value)
    {
        switch (value)
        {
            case I uniform -> set(location, uniform.value());
            case V3F uniform -> set(location, uniform.value());
            case V4F uniform -> set(location, uniform.value());
            case M4F uniform -> set(location, uniform.value());
            default -> throw new AssertionError("Unknown UniformValue type");
        }
    }

    public static void set(Map<Integer, Uniform> uniformLocationMap)
    {
        for (int location:uniformLocationMap.keySet())
        {
            set(location, uniformLocationMap.get(location));
        }
    }

    public static Uniform of(int value) {return new I(value);}
    public static Uniform of(Vector3fc value) {return new V3F(value);}
    public static Uniform of(Vector4fc value) {return new V4F(value);}
    public static Uniform of(Matrix4fc value) {return new M4F(value);}
}