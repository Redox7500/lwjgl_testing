package com.mk.engine.shaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;

import com.mk.engine.uniforms.Uniform;

public class ShaderProgram
{
    private int id = glCreateProgram();

    private int vertexShaderId = 0;
    private int fragmentShaderId = 0;
    private int geometryShaderId = 0;
    private int tessellationControlShaderId = 0;
    private int tessellationEvaluationShaderId = 0;
    // private int computeShaderId = 0;
    private String vertexShaderSource = null;
    private String fragmentShaderSource = null;
    private String geometryShaderSource = null;
    private String tessellationControlShaderSource = null;
    private String tessellationEvaluationShaderSource = null;
    // private String computeShaderSource = null;

    private Map<String, Integer> uniformLocationMap = new HashMap<>();

    public ShaderProgram()
    {

    }

    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource)
    {
        this.setVertexShaderSource(vertexShaderSource);
        this.setFragmentShaderSource(fragmentShaderSource);
    }

    public int getId()
    {
        return this.id;
    }

    private void setShaderSource(int shaderId, String source)
    {
        glShaderSource(shaderId, source);
        this.compileShader(shaderId);
        this.link();
        this.updateUniformLocationMap();
    }

    private void compileShader(int shaderId)
    {
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE)
        {
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }

    private void link()
    {
        glLinkProgram(this.id);
        if (glGetProgrami(this.id, GL_LINK_STATUS) == GL_FALSE)
        {
            throw new RuntimeException(glGetProgramInfoLog(this.id));
        }
    }

    private void removeShader(int shaderId)
    {
        if (shaderId != 0)
        {
            glDetachShader(this.id, shaderId);
        }
    }

    public void setVertexShaderSource(String source)
    {
        this.vertexShaderSource = source;

        if (this.vertexShaderId == 0)
        {
            this.vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
            glAttachShader(this.id, this.vertexShaderId);
        }

        this.setShaderSource(this.vertexShaderId, this.vertexShaderSource);
    }

    public void setFragmentShaderSource(String source)
    {
        this.fragmentShaderSource = source;

        if (this.fragmentShaderId == 0)
        {
            this.fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
            glAttachShader(this.id, this.fragmentShaderId);
        }

        this.setShaderSource(this.fragmentShaderId, this.fragmentShaderSource);
    }

    public void setGeometryShaderSource(String source)
    {
        this.geometryShaderSource = source;

        if (this.geometryShaderId == 0)
        {
            this.geometryShaderId = glCreateShader(GL_GEOMETRY_SHADER);
            glAttachShader(this.id, this.geometryShaderId);
        }

        this.setShaderSource(this.geometryShaderId, this.geometryShaderSource);
    }

    public void setTessellationEvaluationShaderSource(String source)
    {
        this.tessellationEvaluationShaderSource = source;

        if (this.tessellationEvaluationShaderId == 0)
        {
            this.tessellationEvaluationShaderId = glCreateShader(GL_TESS_EVALUATION_SHADER);
            glAttachShader(this.id, this.tessellationEvaluationShaderId);
        }

        this.setShaderSource(this.tessellationEvaluationShaderId, this.tessellationEvaluationShaderSource);
    }

    public void setTessellationShadersSources(String controlSource, String evaluationSource)
    {
        this.tessellationControlShaderSource = controlSource;

        if (this.tessellationControlShaderId == 0)
        {
            this.tessellationControlShaderId = glCreateShader(GL_TESS_CONTROL_SHADER);
            glAttachShader(this.id, this.tessellationControlShaderId);
        }

        this.setShaderSource(this.tessellationControlShaderId, this.tessellationControlShaderSource);

        this.setTessellationEvaluationShaderSource(evaluationSource);
    }

    // public void setComputeShaderSource(String source)
    // {
    //     this.computeShaderSource = source;

    //     if (this.computeShaderId == 0)
    //     {
    //         this.computeShaderId = glCreateShader(GL_COMPUTE_SHADER);
    //         glAttachShader(this.id, this.computeShaderId);
    //     }

    //     this.setShaderSource(this.computeShaderId, this.computeShaderSource);
    // }

    public void removeGeometryShader()
    {
        this.removeShader(this.geometryShaderId);

        this.geometryShaderSource = null;
    }

    public void removeTessellationControlShader()
    {
        this.removeShader(this.tessellationControlShaderId);
        
        this.tessellationControlShaderSource = null;
    }

    public void removeTessellationShaders()
    {
        this.removeShader(this.tessellationEvaluationShaderId);

        this.tessellationEvaluationShaderSource = null;

        this.removeTessellationControlShader();
    }

    // public void removeComputeShader()
    // {
    //     this.removeShader(this.computeShaderId);

    //     this.computeShaderSource = null;
    // }

    public void updateUniformLocationMap()
    {
        this.uniformLocationMap = new HashMap<>();

        StringBuilder combinedSources = new StringBuilder(this.vertexShaderSource + this.fragmentShaderSource);
        if (this.geometryShaderId != 0)
        {
            combinedSources.append(this.geometryShaderSource);
        }
        if (this.tessellationControlShaderId != 0)
        {
            combinedSources.append(this.tessellationControlShaderSource);
        }
        if (this.tessellationEvaluationShaderId != 0)
        {
            combinedSources.append(this.tessellationEvaluationShaderSource);
        }
        // if (this.computeShaderId != 0)
        // {
        //     combinedSources.append(this.computeShaderSource);
        // }

        Pattern pattern = Pattern.compile("(uniform\\s+[a-zA-Z0-9_]+\\s+([a-zA-Z0-9_\\[\\]]+(?:\\s*,\\s*[a-zA-Z0-9_\\[\\]]+)*))");
        Matcher matcher = pattern.matcher(combinedSources); // should i change when combined sources is updated, so that it updates when a shader is attached? or maybe some dirty flag? idk
        List<String> uniformNames = new ArrayList<>();
        while (matcher.find())
        {
            uniformNames.add(matcher.group(matcher.groupCount()));
        }

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