#version 330 core

in vec2 vUv;

uniform sampler2D uTexture;

out vec4 vFragmentColor;

void main()
{
    vFragmentColor = texture(uTexture, vUv);
}