import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import org.lwjgl.opengl.GLUtil;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.mk.engine.buffers.FloatVertexBufferObject;
import com.mk.engine.buffers.UnsignedByteElementBufferObject;
import com.mk.engine.buffers.UnsignedByteVertexBufferObject;
import com.mk.engine.buffers.VertexArrayObject;
import com.mk.engine.buffers.VertexBufferObject;
import com.mk.engine.nodes.Camera;
import com.mk.engine.nodes.Mesh;
import com.mk.engine.nodes.Node;
import com.mk.engine.nodes.Transform;
import com.mk.engine.shaders.ShaderProgram;
import com.mk.engine.textures.Texture;
import com.mk.engine.uniforms.Uniform;

public class Main
{
    public static void main(String[] args)
    {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        int WINDOW_WIDTH = 800;
        int WINDOW_HEIGHT = 600;

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE); // macOS
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        long window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "yes", NULL, NULL);
        if (window == NULL)
        {
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback();
        glEnable(GL_DEPTH_TEST);

        Node cube = new Node();
        cube.addChild(newRectangularPrismMesh(new Vector3f(), new Vector3f(1, 1, 1)));

        Texture cubeTexture = new Texture("/textures/cube copy.png"); // possibly add texture property for meshes?

        Camera camera = new Camera((float)Math.toRadians(60), (float)WINDOW_WIDTH / WINDOW_HEIGHT, 0.1f, 100);
        camera.getLocalTransform().translate(new Vector3f(0, 2, 6)).rotateX(-0.3f);

        // Light light = new Light(new Matrix4f().translate(new Vector3f(10, 10, 0)));
        
        ShaderProgram shaderProgram = new ShaderProgram(
            loadText("/shaders/phong/main.vert"),
            loadText("/shaders/phong/main.frag")
        );

        float startTime = (float)glfwGetTime();
        float lastFrameTime = startTime;
        float currentFrameTime;
        float elapsedTime;
        float deltaTime;

        while (!glfwWindowShouldClose(window))
        {
            currentFrameTime = (float)glfwGetTime();
            elapsedTime = currentFrameTime - startTime;
            deltaTime = currentFrameTime - lastFrameTime;

            glClearColor(0.1f, 0.1f, 0.15f, 1f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            cube.getLocalTransform().rotateY(deltaTime).setTranslation(new Vector3f(0, (float)Math.sin(elapsedTime * 2) * 3 + 0.5f, 0));

            shaderProgram.use();

            // shaderProgram.setUniforms(Map.of(
            //     "uModelViewProjection", Uniform.of(new Matrix4f(camera.getProjectionMatrix()).mul(new Transform(camera.globalTransform).inverse().getMatrix()).mul(cube.globalTransform.getMatrix())),
            //     "uTexture", Uniform.of(cubeTexture.use())
            // ));
            shaderProgram.setUniforms(Map.ofEntries(
                Map.entry("uProjection",        Uniform.of(camera.getProjectionMatrix())),
                Map.entry("uView",              Uniform.of(new Transform().copy(camera.getGlobalTransform()).inverse().getMatrix())),
                Map.entry("uModel",             Uniform.of(cube.getGlobalTransform().getMatrix())),
                Map.entry("uViewPosition",      Uniform.of(camera.getGlobalTransform().getTranslation())),
                Map.entry("uLightPosition",     Uniform.of(new Vector3f(10, 10, 0))),
                Map.entry("uLightColor",        Uniform.of(new Vector4f(1, 1, 1, 1))),
                Map.entry("uAmbientStrength",   Uniform.of(0.1f)),
                Map.entry("uDiffuseStrength",   Uniform.of(1f)),
                Map.entry("uSpecularStrength",  Uniform.of(0f)),
                Map.entry("uSpecularShininess", Uniform.of(32)),
                Map.entry("uTexture",           Uniform.of(cubeTexture.use()))
            ));

            cube.draw();

            glfwSwapBuffers(window);
            glfwPollEvents();

            lastFrameTime = currentFrameTime;
        }

        glfwTerminate();
    }

    private static String loadText(String path)
    {
        try (InputStream in = Main.class.getResourceAsStream(path))
        {
            if (in == null)
            {
                throw new RuntimeException("Missing resource " + path);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // private static float[] generateNormals(float[] positions, int[] indices)
    // {
    //     float[] normals = new float[positions.length];

    //     for (int i = 0; i < indices.length; i += 3)
    //     {
    //         int index0 = indices[i] * 3;
    //         int index1 = indices[i + 1] * 3;
    //         int index2 = indices[i + 2] * 3;
    //         Vector3f position0 = new Vector3f(positions[index0], positions[index0 + 1], positions[index0 + 2]);
    //         Vector3f position1 = new Vector3f(positions[index1], positions[index1 + 1], positions[index1 + 2]);
    //         Vector3f position2 = new Vector3f(positions[index2], positions[index2 + 1], positions[index2 + 2]);

    //         Vector3f edge1 = new Vector3f();
    //         position1.sub(position0, edge1);
    //         Vector3f edge2 = new Vector3f();
    //         position2.sub(position0, edge2);

    //         Vector3f normal = new Vector3f();
    //         edge1.cross(edge2, normal);

    //         if (normal.length() > 1e-4f)
    //         {
    //             normal.normalize();
    //         }
    //         else
    //         {
    //             normal = new Vector3f();
    //         }

    //         normals[index0] = normal.x; normals[index0 + 1] = normal.y; normals[index0 + 2] = normal.z;
    //         normals[index1] = normal.x; normals[index1 + 1] = normal.y; normals[index1 + 2] = normal.z;
    //         normals[index2] = normal.x; normals[index2 + 1] = normal.y; normals[index2 + 2] = normal.z;
    //     }

    //     return normals;
    // }
    // private static float[] generateNormals(float[] positions, byte[] indices) 
    // {
    //     float[] normals = new float[positions.length];

    //     for (int i = 0; i < indices.length; i += 3)
    //     {
    //         int index0 = indices[i] * 3;
    //         int index1 = indices[i + 1] * 3;
    //         int index2 = indices[i + 2] * 3;
    //         Vector3f position0 = new Vector3f(positions[index0], positions[index0 + 1], positions[index0 + 2]);
    //         Vector3f position1 = new Vector3f(positions[index1], positions[index1 + 1], positions[index1 + 2]);
    //         Vector3f position2 = new Vector3f(positions[index2], positions[index2 + 1], positions[index2 + 2]);

    //         Vector3f edge1 = new Vector3f();
    //         position1.sub(position0, edge1);
    //         Vector3f edge2 = new Vector3f();
    //         position2.sub(position0, edge2);

    //         Vector3f normal = new Vector3f();
    //         edge1.cross(edge2, normal);

    //         if (normal.length() > 1e-4f)
    //         {
    //             normal.normalize();
    //         }
    //         else
    //         {
    //             normal = new Vector3f();
    //         }

    //         normals[index0] = normal.x; normals[index0 + 1] = normal.y; normals[index0 + 2] = normal.z;
    //         normals[index1] = normal.x; normals[index1 + 1] = normal.y; normals[index1 + 2] = normal.z;
    //         normals[index2] = normal.x; normals[index2 + 1] = normal.y; normals[index2 + 2] = normal.z;
    //     }

    //     return normals;
    // }

    private static float[] generateNormals(float[] positions, short[] indices)
    {
        float[] normals = new float[positions.length];

        for (int i = 0; i < indices.length; i += 3)
        {
            int index0 = indices[i] * 3;
            int index1 = indices[i + 1] * 3;
            int index2 = indices[i + 2] * 3;
            Vector3f position0 = new Vector3f(positions[index0], positions[index0 + 1], positions[index0 + 2]);
            Vector3f position1 = new Vector3f(positions[index1], positions[index1 + 1], positions[index1 + 2]);
            Vector3f position2 = new Vector3f(positions[index2], positions[index2 + 1], positions[index2 + 2]);

            Vector3f edge1 = new Vector3f();
            position1.sub(position0, edge1);
            Vector3f edge2 = new Vector3f();
            position2.sub(position0, edge2);

            Vector3f normal = new Vector3f();
            edge1.cross(edge2, normal);

            if (normal.length() > 1e-4f)
            {
                normal.normalize();
            }
            else
            {
                normal = new Vector3f();
            }

            normals[index0] = normal.x; normals[index0 + 1] = normal.y; normals[index0 + 2] = normal.z;
            normals[index1] = normal.x; normals[index1 + 1] = normal.y; normals[index1 + 2] = normal.z;
            normals[index2] = normal.x; normals[index2 + 1] = normal.y; normals[index2 + 2] = normal.z;
        }

        return normals;
    }

    private static Mesh newRectangularPrismMesh(Vector3fc position, Vector3fc size)
    {
        float positionX = position.x(), positionY = position.y(), positionZ = position.z();
        float halfSizeX = size.x() / 2, halfSizeY = size.y() / 2, halfSizeZ = size.z() / 2;
        float[] positions = {
            positionX - halfSizeX, positionY - halfSizeY, positionZ + halfSizeZ,
            positionX + halfSizeX, positionY - halfSizeY, positionZ + halfSizeZ,
            positionX + halfSizeX, positionY + halfSizeY, positionZ + halfSizeZ,
            positionX - halfSizeX, positionY + halfSizeY, positionZ + halfSizeZ,

            positionX - halfSizeX, positionY - halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY - halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY + halfSizeY, positionZ - halfSizeZ,
            positionX - halfSizeX, positionY + halfSizeY, positionZ - halfSizeZ,

            positionX - halfSizeX, positionY - halfSizeY, positionZ - halfSizeZ,
            positionX - halfSizeX, positionY - halfSizeY, positionZ + halfSizeZ,
            positionX - halfSizeX, positionY + halfSizeY, positionZ + halfSizeZ,
            positionX - halfSizeX, positionY + halfSizeY, positionZ - halfSizeZ,

            positionX + halfSizeX, positionY - halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY - halfSizeY, positionZ + halfSizeZ,
            positionX + halfSizeX, positionY + halfSizeY, positionZ + halfSizeZ,
            positionX + halfSizeX, positionY + halfSizeY, positionZ - halfSizeZ,

            positionX - halfSizeX, positionY + halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY + halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY + halfSizeY, positionZ + halfSizeZ,
            positionX - halfSizeX, positionY + halfSizeY, positionZ + halfSizeZ,

            positionX - halfSizeX, positionY - halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY - halfSizeY, positionZ - halfSizeZ,
            positionX + halfSizeX, positionY - halfSizeY, positionZ + halfSizeZ,
            positionX - halfSizeX, positionY - halfSizeY, positionZ + halfSizeZ
        };

        short[] uvs = {
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1
        };

        short[] indices = {
             0,  1,  2,  2,  3,  0,
             4,  6,  5,  4,  7,  6,
             8,  9, 10, 10, 11,  8,
            12, 14, 13, 12, 15, 14,
            16, 18, 17, 16, 19, 18,
            20, 21, 22, 22, 23, 20
        };

        float[] normals = Main.generateNormals(positions, indices);

        return new Mesh(new VertexArrayObject(
            new VertexBufferObject[]{
                new FloatVertexBufferObject(       positions, new int[]{3}),
                new FloatVertexBufferObject(       normals,   new int[]{3}),
                new UnsignedByteVertexBufferObject(uvs,       new int[]{2})
            },
            new UnsignedByteElementBufferObject(indices)
        ));
    }
}