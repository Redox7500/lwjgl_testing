package com.mk.engine.buffers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_BYTE;
import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static org.lwjgl.opengl.GL41.glVertexAttribLPointer;

public class VertexArrayObject
{
    private int id = glGenVertexArrays();
    private List<VertexBufferObject> vertexBufferObjects = new ArrayList<>();
    private ElementBufferObject elementBufferObject = null;
    private int toBeDrawn = 0;

    public VertexArrayObject()
    {
        super();
    }

    public VertexArrayObject(List<VertexBufferObject> vertexBufferObjects)
    {
        super();

        this.vertexBufferObjects = vertexBufferObjects;
    }

    public VertexArrayObject(List<VertexBufferObject> vertexBufferObjects, ElementBufferObject elementBufferObject)
    {
        super();

        this.vertexBufferObjects = vertexBufferObjects;
        this.elementBufferObject = elementBufferObject;
    }

    public void addVertexBufferObject(VertexBufferObject vertexBufferObject)
    {
        vertexBufferObject.setVertexArrayObject(this);
        this.vertexBufferObjects.add(vertexBufferObject);
    }

    public void addVertexBufferObjects(VertexBufferObject... vertexBufferObjects)
    {
        for (VertexBufferObject vertexBufferObject:vertexBufferObjects)
        {
            vertexBufferObject.setVertexArrayObject(this);
        }
        Collections.addAll(this.vertexBufferObjects, vertexBufferObjects);
    }

    public void removeVertexBufferObject(VertexBufferObject vertexBufferObject)
    {
        vertexBufferObject.setVertexArrayObject(null);
        this.vertexBufferObjects.remove(vertexBufferObject);
    }

    public void removeVertexBufferObject(int index)
    {
        this.vertexBufferObjects.get(index).setVertexArrayObject(null);
        this.vertexBufferObjects.remove(index);
    }

    public void removeVertexBufferObjects(VertexBufferObject... vertexBufferObjects)
    {
        for (VertexBufferObject vertexBufferObject:vertexBufferObjects)
        {
            vertexBufferObject.setVertexArrayObject(null);
        }
        this.vertexBufferObjects.removeAll(Arrays.asList(vertexBufferObjects));
    }

    // all of this stuff is here for performance reasons, e.g. using this.vertexBufferObjects.removeAll();

    public void removeVertexBufferObjects(int... indices)
    {
        Arrays.sort(indices);
        for (int i = indices.length - 1; i >= 0; i--)
        {
            int index = indices[i];
            this.vertexBufferObjects.get(index).setVertexArrayObject(null);
            this.vertexBufferObjects.remove(index);
        }
    }

    public void removeAllVertexBufferObjects()
    {
        for (VertexBufferObject vertexBufferObject:this.vertexBufferObjects)
        {
            vertexBufferObject.setVertexArrayObject(null);
        }
        this.vertexBufferObjects.clear();
    }

    public ElementBufferObject getElementBufferObject()
    {
        return this.elementBufferObject;
    }

    public void setElementBufferObject(ElementBufferObject elementBufferObject)
    {
        this.elementBufferObject.setVertexArrayObject(null);
        this.elementBufferObject = elementBufferObject;
    }

    public void update()
    {
        int maxAttributes = glGetInteger(GL_MAX_VERTEX_ATTRIBS);

        glBindVertexArray(this.id);

        this.toBeDrawn = 0;

        int attributeLocation = 0;
        for (int i = 0; i < this.vertexBufferObjects.size(); i++)
        {
            VertexBufferObject currentVertexBufferObject = this.vertexBufferObjects.get(i);
            currentVertexBufferObject.use();

            if (currentVertexBufferObject.hasDirtyData())
            {
                currentVertexBufferObject.update(GL_STATIC_DRAW); // make not default, somehow make an option in BufferObject class or something ig
            }

            int currentDataType = currentVertexBufferObject.getDataType();
            int currentFullElementStrides = currentVertexBufferObject.getFullElementStrides();

            int currentBytesPerElement = currentVertexBufferObject.getDataTypeBytes();
            int currentFullByteStrides = currentFullElementStrides * currentBytesPerElement;
            int totalByteStrides = 0;
            for (int stride:currentVertexBufferObject.getStrides())
            {
                if (attributeLocation >= maxAttributes)
                {
                    throw new IllegalStateException("More vertex attributes than GL_MAX_VERTEX_ATTRIBS, which is " + maxAttributes);
                }

                switch (currentDataType)
                {
                    case GL_BYTE, GL_SHORT, GL_INT, GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT, GL_UNSIGNED_INT -> glVertexAttribIPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                    case GL_DOUBLE -> glVertexAttribLPointer(attributeLocation, stride, currentDataType, currentFullByteStrides, totalByteStrides);
                    default -> glVertexAttribPointer(attributeLocation, stride, currentDataType, false, currentFullByteStrides, totalByteStrides);
                }
                glEnableVertexAttribArray(attributeLocation);

                attributeLocation++;
                totalByteStrides += stride * currentBytesPerElement;
            }

            int currentVertexCount = currentVertexBufferObject.getDataLength() / currentFullElementStrides;
            if (currentVertexCount < toBeDrawn || currentVertexCount == 0)
            {
                throw new IllegalStateException("A VBO has an invalid number of vertex attributes");
            }

            this.toBeDrawn = currentVertexCount;
        }

        if (this.elementBufferObject != null)
        {
            this.elementBufferObject.use();
            toBeDrawn = Math.min(this.elementBufferObject.getDataLength(), toBeDrawn);
        }

        glBindVertexArray(0);
    }

    public void use()
    {
        glBindVertexArray(this.id);
    }

    public void draw()
    {
        this.use();

        boolean dirty = false;
        if (this.elementBufferObject != null)
        {
            dirty = dirty || this.elementBufferObject.hasDirtyData() || this.elementBufferObject.hasDirtyStrides();
        }
        for (VertexBufferObject vertexBufferObject:this.vertexBufferObjects)
        {
            if (dirty)
            {
                break;
            }
            dirty = dirty || vertexBufferObject.hasDirtyData() || vertexBufferObject.hasDirtyStrides();
        }

        if (dirty)
        {
            // make this better hopefully, maybe put in BufferObject class itself
            this.update();
        }

        if (this.elementBufferObject != null)
        {
            glDrawElements(GL_TRIANGLES, toBeDrawn, this.elementBufferObject.getDataType(), 0);
        }
        else
        {
            glDrawArrays(GL_TRIANGLES, 0, toBeDrawn);
        }
    }
}