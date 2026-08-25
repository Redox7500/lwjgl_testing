// add dirty thing maybe? for local and global transforms

package com.mk.engine.nodes;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Transform
{
    private Matrix4f matrix = new Matrix4f();
    private Node attachedNode = null;
    private TransformType type = null;

    public Transform() {}

    public Transform(Node attachedNode, TransformType type)
    {
        this.attachedNode = attachedNode;
        this.type = type;
    }

    private void updateAttachedNode()
    {
        if (this.attachedNode == null) {return;}

        switch (this.type)
        {
            case TransformType.LOCAL -> {
                if (this.attachedNode.getParent() == null)
                {
                    this.attachedNode.globalTransform.matrix = new Matrix4f(this.matrix);
                }
                else
                {
                    this.attachedNode.getParent().globalTransform.matrix.mul(this.matrix, this.attachedNode.globalTransform.matrix);
                }
            }
            case TransformType.GLOBAL -> {
                if (this.attachedNode.getParent() == null)
                {
                    this.attachedNode.localTransform.matrix = new Matrix4f(this.matrix);
                }
                else
                {
                    this.attachedNode.getParent().globalTransform.matrix.invert().mul(this.matrix, this.attachedNode.localTransform.matrix);
                }
            }
        }
    }

    public Matrix4fc getMatrix() {return this.matrix;}

    public Transform setMatrix(Matrix4fc matrix) {this.matrix = new Matrix4f(matrix); this.updateAttachedNode(); return this;}

    public Vector3f getTranslation() {Vector3f translation = new Vector3f(); this.matrix.getTranslation(translation); return translation;}
    public float getTranslationX() {return this.matrix.m30();}
    public float getTranslationY() {return this.matrix.m31();}
    public float getTranslationZ() {return this.matrix.m32();}

    public Transform setTranslation(Vector3fc translation) {this.matrix.setTranslation(translation); this.updateAttachedNode(); return this;}
    public Transform setTranslation(float x, float y, float z) {return this.setTranslation(new Vector3f(x, y, z));}
    public Transform setTranslationX(float offset) {this.matrix.m30(offset); this.updateAttachedNode(); return this;}
    public Transform setTranslationY(float offset) {this.matrix.m31(offset); this.updateAttachedNode(); return this;}
    public Transform setTranslationZ(float offset) {this.matrix.m32(offset);this.updateAttachedNode(); return this;}

    public Transform translate(Vector3fc translation) {this.matrix.translate(translation); this.updateAttachedNode(); return this;}
    public Transform translate(float x, float y, float z) {return this.translate(new Vector3f(x, y, z));}
    public Transform translateX(float offset) {this.matrix.translate(offset, 0, 0); this.updateAttachedNode(); return this;}
    public Transform translateY(float offset) {this.matrix.translate(0, offset, 0); this.updateAttachedNode(); return this;}
    public Transform translateZ(float offset) {this.matrix.translate(0, 0, offset); this.updateAttachedNode(); return this;}

    public Vector3f getRotation() {Vector3f rotation = new Vector3f(); this.matrix.getEulerAnglesXYZ(rotation); return rotation;}
    public float getRotationX() {return this.getRotation().x;}
    public float getRotationY() {return this.getRotation().y;}
    public float getRotationZ() {return this.getRotation().z;}

    public Transform setRotation(Vector3fc rotation) {this.matrix.setRotationXYZ(rotation.x(), rotation.y(), rotation.z()); this.updateAttachedNode(); return this;}
    public Transform setRotation(float x, float y, float z) {return this.setRotation(new Vector3f(x, y, z));}
    public Transform setRotationX(float angle) {this.matrix.identity().translate(this.getTranslation()).rotateXYZ(this.getRotation().setComponent(0, angle)).scale(this.getScale()); this.updateAttachedNode(); return this;}
    public Transform setRotationY(float angle) {this.matrix.identity().translate(this.getTranslation()).rotateXYZ(this.getRotation().setComponent(1, angle)).scale(this.getScale()); this.updateAttachedNode(); return this;}
    public Transform setRotationZ(float angle) {this.matrix.identity().translate(this.getTranslation()).rotateXYZ(this.getRotation().setComponent(2, angle)).scale(this.getScale()); this.updateAttachedNode(); return this;}

    public Transform rotate(Vector3fc rotation) {this.matrix.rotateXYZ(rotation); this.updateAttachedNode(); return this;}
    public Transform rotate(float x, float y, float z) {return this.rotate(new Vector3f(x, y, z));}
    public Transform rotateX(float angle) {this.matrix.rotateXYZ(angle, 0, 0); this.updateAttachedNode(); return this;}
    public Transform rotateY(float angle) {this.matrix.rotateXYZ(0, angle, 0); this.updateAttachedNode(); return this;}
    public Transform rotateZ(float angle) {this.matrix.rotateXYZ(0, 0, angle); this.updateAttachedNode(); return this;}

    public Vector3f getScale() {Vector3f translation = new Vector3f(); this.matrix.getScale(translation); return translation;}
    public float getScaleX() {return this.getScale().x;} // could do this manually
    public float getScaleY() {return this.getScale().y;}
    public float getScaleZ() {return this.getScale().z;}

    public Transform setScale(Vector3fc scale) {this.matrix = new Matrix4f().translate(this.getTranslation()).rotateXYZ(this.getRotation()).scale(scale); this.updateAttachedNode(); return this;}
    public Transform setScale(float x, float y, float z) {return this.setScale(new Vector3f(x, y, z));}
    public Transform setScaleX(float factor) {this.matrix.identity().translate(this.getTranslation()).rotateXYZ(this.getRotation()).scale(this.getScale().setComponent(0, factor)); this.updateAttachedNode(); return this;}
    public Transform setScaleY(float factor) {this.matrix.identity().translate(this.getTranslation()).rotateXYZ(this.getRotation()).scale(this.getScale().setComponent(1, factor)); this.updateAttachedNode(); return this;}
    public Transform setScaleZ(float factor) {this.matrix.identity().translate(this.getTranslation()).rotateXYZ(this.getRotation()).scale(this.getScale().setComponent(2, factor)); this.updateAttachedNode(); return this;}

    public Transform scale(Vector3fc scale) {this.matrix.scale(scale); this.updateAttachedNode(); return this;}
    public Transform scale(float x, float y, float z) {return this.scale(new Vector3f(x, y, z));}
    public Transform scaleX(float factor) {this.matrix.scale(factor, 0, 0); this.updateAttachedNode(); return this;}
    public Transform scaleY(float factor) {this.matrix.scale(0, factor, 0); this.updateAttachedNode(); return this;}
    public Transform scaleZ(float factor) {this.matrix.scale(0, 0, factor); this.updateAttachedNode(); return this;}

    public Transform invert() {this.matrix.invert(); this.updateAttachedNode(); return this;}
    public Transform inverse() {return new Transform().copy(this).invert();}

    public Transform apply(Transform transform) {transform.getMatrix().mul(this.matrix, this.matrix); this.updateAttachedNode(); return this;}

    public Transform copy(Transform transform) {this.setMatrix(transform.getMatrix()); return this;}
}