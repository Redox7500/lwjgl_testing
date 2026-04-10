#version 330 core

// in vec3 vFragmentPosition;
// in vec3 vNormal;
in vec2 vUV;

// uniform vec3 uViewPosition;
// uniform vec3 uLightPosition;
// uniform vec4 uLightColor;
// uniform float uAmbientStrength;
// uniform float uDiffuseStrength;
// uniform float uSpecularStrength;
// uniform int uSpecularShininess;
uniform sampler2D uTexture;

out vec4 vFragmentColor;

void main()
{
    // vec4 ambient = uAmbientStrength * uLightColor;

    // vec3 lightDirection = normalize(uLightPosition - vFragmentPosition);
    // vec3 normal = normalize(vNormal);
    // float difference = max(dot(normal, lightDirection), 0.f);
    // vec4 diffuse = uDiffuseStrength * difference * uLightColor;

    // vec3 viewDirection = normalize(uViewPosition - vFragmentPosition);
    // vec3 reflectDirection = reflect(-lightDirection, normal);
    // float spec = pow(max(dot(viewDirection, reflectDirection), 0.f), uSpecularShininess);
    // vec4 specular = uSpecularStrength * spec * uLightColor;

    vFragmentColor = texture(uTexture, vUV);
}