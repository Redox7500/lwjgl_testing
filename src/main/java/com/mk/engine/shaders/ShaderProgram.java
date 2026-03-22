package com.mk.engine.shaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;

import com.mk.engine.uniforms.Uniform;

public class ShaderProgram
{
    private int id = glCreateProgram();

    private Shader vertexShader = null;
    private Shader fragmentShader = null;
    private Shader geometryShader = null;
    private Shader tessellationShader = null;
    private Shader computeShader = null;

    private Map<String, Integer> uniformLocationMap = new HashMap<>();

    public ShaderProgram()
    {

    }

    public ShaderProgram(Shader vertexShader, Shader fragmentShader)
    {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;

        this.attachShader(this.vertexShader);
        this.attachShader(this.fragmentShader);

        this.updateUniformLocationMap();
    }

    public int getId()
    {
        return this.id;
    }

    private void attachShader(Shader shader)
    {
        glAttachShader(this.id, shader.getId());

        glLinkProgram(this.id);
        if (glGetProgrami(this.id, GL_LINK_STATUS) == GL_FALSE)
        {
            throw new RuntimeException(glGetProgramInfoLog(this.id));
        }
    }

    public void addGeometryShader(Shader shader)
    {
        if (this.geometryShader == null)
        {
            return;
        }

        this.geometryShader = shader;
        this.attachShader(this.geometryShader);
    }

    public void addTessellationShader(Shader shader)
    {
        if (this.tessellationShader == null)
        {
            return;
        }

        this.tessellationShader = shader;
        this.attachShader(this.tessellationShader);
    }

    public void addComputeShader(Shader shader)
    {
        if (this.computeShader == null)
        {
            return;
        }

        this.computeShader = shader;
        this.attachShader(this.computeShader);
    }

    public void setVertexShaderSource(String source)
    {
        this.vertexShader.setSource(source);
        this.attachShader(this.vertexShader);
        this.updateUniformLocationMap();
    }

    public void setFragmentShaderSource(String source)
    {
        this.fragmentShader.setSource(source);
        this.attachShader(this.fragmentShader);
        this.updateUniformLocationMap();
    }

    public void setGeometryShaderSource(String source)
    {
        this.fragmentShader.setSource(source);
        this.attachShader(this.geometryShader);
        this.updateUniformLocationMap();
    }

    public void setTessellationShaderSource(String source)
    {
        this.fragmentShader.setSource(source);
        this.attachShader(this.tessellationShader);
        this.updateUniformLocationMap();
    }

    public void setComputeShaderSource(String source)
    {
        this.fragmentShader.setSource(source);
        this.attachShader(this.computeShader);
        this.updateUniformLocationMap();
    }

    public void updateUniformLocationMap()
    {
        this.uniformLocationMap = new HashMap<>();

        StringBuilder combinedSources = new StringBuilder(this.vertexShader.getSource() + this.fragmentShader.getSource());
        if (this.geometryShader != null)
        {
            combinedSources.append(this.geometryShader.getSource());
        }
        if (this.tessellationShader != null)
        {
            combinedSources.append(this.tessellationShader.getSource());
        }
        if (this.computeShader != null)
        {
            combinedSources.append(this.computeShader.getSource());
        }

        Pattern pattern = Pattern.compile("(uniform\\s+[a-zA-Z0-9_]+\\s+([a-zA-Z0-9_\\[\\]]+(?:\\s*,\\s*[a-zA-Z0-9_\\[\\]]+)*))");
        Matcher matcher = pattern.matcher(combinedSources); // should i change when combined sources is updated, so that it updates when a shader is attached? or maybe some dirty flag? idk
        List<String> uniformNames = new ArrayList<>();
        while (matcher.find())
        {
            uniformNames.add(matcher.group(matcher.groupCount()));
        }

        // List<String> uniformNames = Pattern.compile("(uniform\\s+[a-zA-Z0-9_]+\\s+([a-zA-Z0-9_\\[\\]]+(?:\\s*,\\s*[a-zA-Z0-9_\\[\\]]+)*))")
        //     .matcher(combinedSources.toString())
        //     .results()
        //     .map((matchResult) -> matchResult.group())
        //     .collect(Collectors.toList());

        this.use();
        for (String uniformName:uniformNames)
        {
            this.uniformLocationMap.put(uniformName, glGetUniformLocation(this.id, uniformName));
        }
    }

    public void use()
    {
        glUseProgram(this.id);
    }

    public void setUniforms(Map<String, Uniform> uniformValueMap)
    {
        for (String name:uniformValueMap.keySet())
        {
            Uniform.set(this.uniformLocationMap.get(name), uniformValueMap.get(name));
        }
    }
}