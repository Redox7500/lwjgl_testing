#version 330 core

in vec2 vUV;

uniform sampler2D uTexture;

out vec4 vFragmentColor;

void main()
{
    vFragmentColor = texture(uTexture, vUV);
    // vFragmentColor = vec4(0.3, 0.5, 0.2, 1.0);
}