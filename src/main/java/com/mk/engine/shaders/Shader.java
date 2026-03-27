package com.mk.engine.shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

public class Shader
{
    private int id = 0;
    private String source = "";
    private int type = 0;
    private ShaderProgram shaderProgram = null;

    public Shader(int type, String source)
    {
        this(type);
        
        this.setSource(source);
    }

    public Shader(int type)
    {
        this.type = type;
        this.id = glCreateShader(this.type);
    }

    public Shader()
    {
        
    }

    public int getId()
    {
        return this.id;
    }

    public String getSource()
    {
        return this.source;
    }

    public int getType()
    {
        return this.type;
    }

    public void setSource(String source)
    {
        this.source = source;

        glShaderSource(this.id, this.source);
        glCompileShader(this.id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
        {
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }

    void setShaderProgram(ShaderProgram shaderProgram)
    {
        if (this.shaderProgram == null)
        {
            this.shaderProgram = shaderProgram;
        }
    }
}