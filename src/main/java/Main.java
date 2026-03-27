import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
import static org.lwjgl.system.MemoryUtil.NULL;

import com.mk.engine.nodes.Camera;
import com.mk.engine.nodes.Mesh;
import com.mk.engine.nodes.Node;
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

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
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
        cube.addChild(new Mesh(new float[]{
          // pos            // u v
            -0.5f,-0.5f, 0.5f, 0,0,  0.5f,-0.5f, 0.5f, 1,0,  0.5f, 0.5f,0.5f, 1,1,
             0.5f, 0.5f, 0.5f, 1,1, -0.5f, 0.5f, 0.5f, 0,1, -0.5f,-0.5f,0.5f, 0,0,

             0.5f,-0.5f,-0.5f, 0,0, -0.5f,-0.5f,-0.5f, 1,0, -0.5f, 0.5f,-0.5f, 1,1,
            -0.5f, 0.5f,-0.5f, 1,1,  0.5f, 0.5f,-0.5f, 0,1,  0.5f,-0.5f,-0.5f, 0,0,

            -0.5f,-0.5f,-0.5f, 0,0, -0.5f,-0.5f, 0.5f, 1,0, -0.5f, 0.5f, 0.5f, 1,1,
            -0.5f, 0.5f, 0.5f, 1,1, -0.5f, 0.5f,-0.5f, 0,1, -0.5f,-0.5f,-0.5f, 0,0,

             0.5f,-0.5f, 0.5f, 0,0,  0.5f,-0.5f,-0.5f, 1,0,  0.5f, 0.5f,-0.5f, 1,1,
             0.5f, 0.5f,-0.5f, 1,1,  0.5f, 0.5f, 0.5f, 0,1,  0.5f,-0.5f, 0.5f, 0,0,

            -0.5f, 0.5f, 0.5f, 0,0,  0.5f, 0.5f, 0.5f, 1,0,  0.5f, 0.5f,-0.5f, 1,1,
             0.5f, 0.5f,-0.5f, 1,1, -0.5f, 0.5f,-0.5f, 0,1, -0.5f, 0.5f, 0.5f, 0,0,

            -0.5f,-0.5f,-0.5f, 0,0,  0.5f,-0.5f,-0.5f, 1,0,  0.5f,-0.5f, 0.5f, 1,1,
             0.5f,-0.5f, 0.5f, 1,1, -0.5f,-0.5f ,0.5f, 0,1, -0.5f,-0.5f,-0.5f, 0,0
        }));

        Camera camera = new Camera((float)Math.toRadians(60), (float)WINDOW_WIDTH / WINDOW_HEIGHT, 0.1f, 100);
        camera.localTransform.translate(new Vector3f(0, 2, 6));
        camera.localTransform.rotateX(-0.3f);

        ShaderProgram shaderProgram = new ShaderProgram(
            loadText("/shaders/basic/main.vert"),
            loadText("/shaders/basic/main.frag")
        );

        Texture cubeTexture = new Texture("/textures/cube.png");

        float lastFrameTime = (float)glfwGetTime();
        float currentFrameTime;
        float deltaTime;

        while (!glfwWindowShouldClose(window))
        {
            currentFrameTime = (float)glfwGetTime();
            deltaTime = currentFrameTime - lastFrameTime;

            glClearColor(0.1f, 0.1f, 0.15f, 1f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            cube.localTransform.rotateY(deltaTime);

            shaderProgram.use();
            cubeTexture.use();

            shaderProgram.setUniforms(Map.of(
                "mvp", Uniform.of(new Matrix4f(camera.getProjectionMatrix()).mul(camera.getViewMatrix()).mul(cube.localTransform.getMatrix())),
                "tex", Uniform.of(0)
            ));

            cube.draw();

            glfwSwapBuffers(window);
            glfwPollEvents();

            lastFrameTime = currentFrameTime;
        }

        glfwTerminate();
    }

    static String loadText(String path)
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
}