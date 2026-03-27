#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aUV;

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uModel;

out vec3 vFragmentPosition;
out vec3 vNormal;
out vec2 vUV;

void main()
{
    vec4 worldPosition = uModel * vec4(aPosition, 1.f);

    gl_Position = uProjection * uView * worldPosition;
    vFragmentPosition = worldPosition.xyz;
    vNormal = mat3(transpose(inverse(uModel))) * aNormal;
    vUV = aUV;
}