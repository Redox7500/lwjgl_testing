package com.mk.engine.textures;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import org.lwjgl.stb.STBImage;

public class Texture
{
    private int id = glGenTextures();

    public Texture()
    {
        
    }

    public Texture(String filepath)
    {
        this();
        
        this.setTexture(filepath);
    }

    public int getId()
    {
        return this.id;
    }

    public void setTexture(String filepath)
    {
        ByteBuffer imageBuffer;
        try (InputStream in = Texture.class.getResourceAsStream(filepath))
        {
            if (in == null)
            {
                throw new RuntimeException("Missing resource " + filepath);
            }
            byte[] data = in.readAllBytes();
            imageBuffer = BufferUtils.createByteBuffer(data.length);
            imageBuffer.put(data).flip();
        }
        catch (IOException e) 
        {
            throw new RuntimeException(e);
        }

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);

        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer img = STBImage.stbi_load_from_memory(imageBuffer, w, h, c, 4);
        if (img == null)
        {
            throw new RuntimeException("Failed to load image");
        }

        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8,
                w.get(0), h.get(0), 0,
                GL_RGBA, GL_UNSIGNED_BYTE, img);
        glGenerateMipmap(GL_TEXTURE_2D);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        STBImage.stbi_image_free(img);
    }

    public void use()
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, this.id);
    }
}