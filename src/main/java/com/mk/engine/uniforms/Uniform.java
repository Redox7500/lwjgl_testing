package com.mk.engine.uniforms;

import java.nio.FloatBuffer;
import java.util.Map;

import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public sealed interface Uniform
    permits I, F, V3F, V4F, M4F
{
    public static Uniform of(int value)   {return new I(value);}
    public static Uniform of(float value) {return new F(value);}
    public static Uniform of(Vector3fc value) {return new V3F(value);}
    public static Uniform of(Vector4fc value) {return new V4F(value);}
    public static Uniform of(Matrix4fc value) {return new M4F(value);}

    public static void set(int location, Uniform value)
    {
        switch (value)
        {
            case I uniform -> glUniform1i(location, uniform.value());
            case F uniform -> glUniform1f(location, uniform.value());
            case V3F uniform -> glUniform3f(location, uniform.value().x(), uniform.value().y(), uniform.value().z());
            case V4F uniform -> glUniform4f(location, uniform.value().x(), uniform.value().y(), uniform.value().z(), uniform.value().w());
            case M4F uniform -> {FloatBuffer buffer = BufferUtils.createFloatBuffer(16); uniform.value().get(buffer); glUniformMatrix4fv(location, false, buffer);}
        }
    }

    public static void set(Map<Integer, Uniform> uniformLocationMap)
    {
        for (int location:uniformLocationMap.keySet())
        {
            set(location, uniformLocationMap.get(location));
        }
    }
}

record I(int value) implements Uniform {}
record F(float value) implements Uniform {}
record V3F(Vector3fc value) implements Uniform {}
record V4F(Vector4fc value) implements Uniform {}
record M4F(Matrix4fc value) implements Uniform {}