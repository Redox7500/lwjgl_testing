import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
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
        // cube.addChild(new Mesh(new VertexArrayObject(
        //     new VertexBufferObject[]{
        //         new FloatVertexBufferObject(new float[]{
        //         //    x      y      z      x      y      z      x      y      z
        //             -0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f,  0.5f,  0.5f,  0.5f,
        //              0.5f,  0.5f,  0.5f, -0.5f,  0.5f,  0.5f, -0.5f, -0.5f,  0.5f,
        //              0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,  0.5f, -0.5f,
        //             -0.5f,  0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f, -0.5f, -0.5f,
        //             -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,  0.5f, -0.5f,  0.5f,  0.5f,
        //             -0.5f,  0.5f,  0.5f, -0.5f,  0.5f, -0.5f, -0.5f, -0.5f, -0.5f,
        //              0.5f, -0.5f,  0.5f,  0.5f, -0.5f, -0.5f,  0.5f,  0.5f, -0.5f,
        //              0.5f,  0.5f, -0.5f,  0.5f,  0.5f,  0.5f,  0.5f, -0.5f,  0.5f,
        //             -0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f, -0.5f,
        //              0.5f,  0.5f, -0.5f, -0.5f,  0.5f, -0.5f, -0.5f,  0.5f,  0.5f,
        //             -0.5f, -0.5f, -0.5f,  0.5f, -0.5f, -0.5f,  0.5f, -0.5f,  0.5f,
        //              0.5f, -0.5f,  0.5f, -0.5f, -0.5f  ,0.5f, -0.5f, -0.5f, -0.5f
        //         }, new int[]{3}),
        //         new UnsignedByteVertexBufferObject(new short[]{
        //         //  u  v  u  v  u  v
        //             0, 0, 1, 0, 1, 1,
        //             1, 1, 0, 1, 0, 0,
        //             0, 0, 1, 0, 1, 1,
        //             1, 1, 0, 1, 0, 0,
        //             0, 0, 1, 0, 1, 1,
        //             1, 1, 0, 1, 0, 0,
        //             0, 0, 1, 0, 1, 1,
        //             1, 1, 0, 1, 0, 0,
        //             0, 0, 1, 0, 1, 1,
        //             1, 1, 0, 1, 0, 0,
        //             0, 0, 1, 0, 1, 1,
        //             1, 1, 0, 1, 0, 0
        //         }, new int[]{2}),
        //     },
        //     new UnsignedByteElementBufferObject(new byte[]{
        //         0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35
        //     })
        // )));

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

            cube.localTransform.rotateY(deltaTime).setTranslation(new Vector3f(0, (float)Math.sin(elapsedTime * 2) * 3 + 0.5f, 0));

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

    private static Mesh newRectangularPrismMesh(Vector3fc position, Vector3fc size)
    {
        float positionX = position.x(), positionY = position.y(), positionZ = position.z();
        float sizeX = size.x(), sizeY = size.y(), sizeZ = size.z();
        float[] positions = {
            positionX        , positionY        , positionZ        ,
            positionX + sizeX, positionY        , positionZ        ,
            positionX + sizeX, positionY + sizeY, positionZ        ,
            positionX        , positionY + sizeY, positionZ        ,
            positionX        , positionY        , positionZ + sizeZ,
            positionX + sizeX, positionY        , positionZ + sizeZ,
            positionX + sizeX, positionY + sizeY, positionZ + sizeZ,
            positionX        , positionY + sizeY, positionZ + sizeZ
        };
        float[] repeatedPositions = new float[24];
        for (int i = 0; i < 24; i += 8)
        {
            System.arraycopy(positions, 0, repeatedPositions, i, 8);
        }

        short[] uvs = {
        //  u  v
            0, 0, 1, 0, 1, 1,
            1, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 1,
            1, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 1,
            1, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 1,
            1, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 1,
            1, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 1,
            1, 1, 0, 1, 0, 0
        };
        short[] indices = {
             4,  5,  6,  6,  7,  4,
             0, 12, 15, 15,  3,  0,
             8,  1, 13, 13, 20,  8,
            16,  2,  9, 16, 11,  2,
            17, 14, 21, 17, 10, 14,
            19, 22, 18, 19, 23, 22,
        };

        return new Mesh(new VertexArrayObject(
            new VertexBufferObject[]{
                new FloatVertexBufferObject(repeatedPositions, new int[]{3}),
                new UnsignedByteVertexBufferObject(uvs, new int[]{2})
            },
            new UnsignedByteElementBufferObject(indices)
        ));
    }
}