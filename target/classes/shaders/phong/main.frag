#version 330 core

in vec3 vFragmentPosition;
in vec3 vNormal;
in vec2 vUV;

uniform vec3 uViewPosition;
uniform vec3 uLightPosition;
uniform vec4 uLightColor;
uniform sampler2D uTex;

out vec4 vFragColor;

void main()
{
    float ambientStrength = 0.1f;
    float diffuseStrength = 1.0f;
    float specularStrength = 0.0f;
    int specularShininess = 32;

    vec4 ambient = ambientStrength * uLightColor;

    vec3 lightDirection = normalize(uLightPosition - vFragmentPosition);
    vec3 normal = normalize(vNormal);
    float difference = max(dot(normal, lightDirection), 0.f);
    vec4 diffuse = diffuseStrength * difference * uLightColor;

    vec3 viewDirection = normalize(uViewPosition - vFragmentPosition);
    vec3 reflectDirection = reflect(-lightDirection, normal);
    float spec = pow(max(dot(viewDirection, reflectDirection), 0.f), specularShininess);
    vec4 specular = specularStrength * spec * uLightColor;

    vFragColor = (ambient + diffuse + specular) * texture(uTex, vUV);
}