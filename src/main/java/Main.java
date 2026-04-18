import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
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
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.mk.engine.buffers.FloatBufferData;
import com.mk.engine.buffers.UnsignedByteBufferData;
import com.mk.engine.buffers.UnsignedShortBufferData;
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

        long window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "yes", NULL, NULL);
        if (window == NULL)
        {
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);

        Node cube = new Node();
        cube.addChild(new Mesh(new VertexArrayObject(arrayListOf(new VertexBufferObject(new FloatBufferData(new float[]{
          // pos
            -0.5f,-0.5f, 0.5f,  0.5f,-0.5f, 0.5f,  0.5f, 0.5f, 0.5f,
             0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f,-0.5f, 0.5f,

             0.5f,-0.5f,-0.5f, -0.5f,-0.5f,-0.5f, -0.5f, 0.5f,-0.5f,
            -0.5f, 0.5f,-0.5f,  0.5f, 0.5f,-0.5f,  0.5f,-0.5f,-0.5f,

            -0.5f,-0.5f,-0.5f, -0.5f,-0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,-0.5f, -0.5f,-0.5f,-0.5f,

             0.5f,-0.5f, 0.5f,  0.5f,-0.5f,-0.5f,  0.5f, 0.5f,-0.5f,
             0.5f, 0.5f,-0.5f,  0.5f, 0.5f, 0.5f,  0.5f,-0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,  0.5f, 0.5f, 0.5f,  0.5f, 0.5f,-0.5f,
             0.5f, 0.5f,-0.5f, -0.5f, 0.5f,-0.5f, -0.5f, 0.5f, 0.5f,

            -0.5f,-0.5f,-0.5f,  0.5f,-0.5f,-0.5f,  0.5f,-0.5f, 0.5f,
             0.5f,-0.5f, 0.5f, -0.5f,-0.5f ,0.5f, -0.5f,-0.5f,-0.5f
        }), arrayListOf(3)), new VertexBufferObject(new UnsignedByteBufferData(new byte[]{
         // u v
            0,0, 1,0, 1,1,
            1,1, 0,1, 0,0,
            0,0, 1,0, 1,1,
            1,1, 0,1, 0,0,
            0,0, 1,0, 1,1,
            1,1, 0,1, 0,0,
            0,0, 1,0, 1,1,
            1,1, 0,1, 0,0,
            0,0, 1,0, 1,1,
            1,1, 0,1, 0,0,
            0,0, 1,0, 1,1,
            1,1, 0,1, 0,0
        }), arrayListOf(2))))));
        // cube.addChild(new Mesh(arrayListOf(new VertexBufferObject(new FloatBufferData(new float[]{
        //   // pos
        //     -0.5f,-0.5f, 0.5f,  0.5f,-0.5f, 0.5f,  0.5f, 0.5f, 0.5f,
        //      0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f,-0.5f, 0.5f,

        //      0.5f,-0.5f,-0.5f, -0.5f,-0.5f,-0.5f, -0.5f, 0.5f,-0.5f,
        //     -0.5f, 0.5f,-0.5f,  0.5f, 0.5f,-0.5f,  0.5f,-0.5f,-0.5f,

        //     -0.5f,-0.5f,-0.5f, -0.5f,-0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
        //     -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,-0.5f, -0.5f,-0.5f,-0.5f,

        //      0.5f,-0.5f, 0.5f,  0.5f,-0.5f,-0.5f,  0.5f, 0.5f,-0.5f,
        //      0.5f, 0.5f,-0.5f,  0.5f, 0.5f, 0.5f,  0.5f,-0.5f, 0.5f,

        //     -0.5f, 0.5f, 0.5f,  0.5f, 0.5f, 0.5f,  0.5f, 0.5f,-0.5f,
        //      0.5f, 0.5f,-0.5f, -0.5f, 0.5f,-0.5f, -0.5f, 0.5f, 0.5f,

        //     -0.5f,-0.5f,-0.5f,  0.5f,-0.5f,-0.5f,  0.5f,-0.5f, 0.5f,
        //      0.5f,-0.5f, 0.5f, -0.5f,-0.5f ,0.5f, -0.5f,-0.5f,-0.5f
        // }), GL_STATIC_DRAW, arrayListOf(3)), new VertexBufferObject(new UnsignedByteBufferData(new short[]{
        //  // u v
        //     0,0, 1,0, 1,1,
        //     1,1, 0,1, 0,0,
        //     0,0, 1,0, 1,1,
        //     1,1, 0,1, 0,0,
        //     0,0, 1,0, 1,1,
        //     1,1, 0,1, 0,0,
        //     0,0, 1,0, 1,1,
        //     1,1, 0,1, 0,0,
        //     0,0, 1,0, 1,1,
        //     1,1, 0,1, 0,0,
        //     0,0, 1,0, 1,1,
        //     1,1, 0,1, 0,0
        // }), GL_STATIC_DRAW, arrayListOf(2)))));

        Texture cubeTexture = new Texture("/textures/cube.png"); // possibly add texture property for meshes?

        Camera camera = new Camera((float)Math.toRadians(60), (float)WINDOW_WIDTH / WINDOW_HEIGHT, 0.1f, 100);
        camera.localTransform.translate(new Vector3f(0, 2, 6)).rotateX(-0.3f);
        
        ShaderProgram shaderProgram = new ShaderProgram(
            loadText("/shaders/basic/main.vert"),
            loadText("/shaders/basic/main.frag")
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

            cube.localTransform.rotateY(deltaTime).setTranslation(new Vector3f(0, (float)Math.sin(elapsedTime * 2), 0));

            shaderProgram.use();

            shaderProgram.setUniforms(Map.of(
                "uModelViewProjection", Uniform.of(new Matrix4f(camera.getProjectionMatrix()).mul(new Transform(camera.globalTransform).inverse().getMatrix()).mul(cube.globalTransform.getMatrix())),
                "uTexture", Uniform.of(cubeTexture.use())
            ));
            // shaderProgram.setUniforms(Map.ofEntries(
            //     Map.entry("uProjection",        Uniform.of(camera.getProjectionMatrix())),
            //     Map.entry("uView",              Uniform.of(new Transform(camera.globalTransform).inverse().getMatrix())),
            //     Map.entry("uModel",             Uniform.of(cube.globalTransform.getMatrix())),
            //     // Map.entry("uViewPosition",      Uniform.of(camera.globalTransform.getTranslation())),
            //     // Map.entry("uLightPosition",     Uniform.of(new Vector3f(10, 10, 0))),
            //     // Map.entry("uLightColor",        Uniform.of(new Vector4f(1, 1, 1, 1))),
            //     // Map.entry("uAmbientStrength",   Uniform.of(0.2f)),
            //     // Map.entry("uDiffuseStrength",   Uniform.of(0.2f)),
            //     // Map.entry("uSpecularStrength",  Uniform.of(0.2f)),
            //     // Map.entry("uSpecularShininess", Uniform.of(32)),
            //     Map.entry("uTexture",           Uniform.of(cubeTexture.use()))
            // ));

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

    private static <T> ArrayList<T> arrayListOf(T... elements)
    {
        return new ArrayList<T>(List.of(elements));
    }
}