package com.ceat.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class SimpleModelInstance extends ModelInstance {
    public static Model planeModel = new ModelBuilder().createBox(1, 0, 1,
            new Material( ColorAttribute.createDiffuse(Color.WHITE) ), VertexAttributes.Usage.Position);
    public static Model cubeModel = new ModelBuilder().createBox(1, 1, 1,
            new Material( ColorAttribute.createDiffuse(Color.WHITE) ), VertexAttributes.Usage.Position);
    public static Model sphereModel = new ModelBuilder().createSphere(1, 1, 1, 10, 10,
            new Material( ColorAttribute.createDiffuse(Color.WHITE) ), VertexAttributes.Usage.Position);
    private Vector3 position;
    private Vector3 rotation; // euler x y z
    private Vector3 scale;
    public SimpleModelInstance(Model model) {
        super(model);
        position = new Vector3();
        rotation = new Vector3();
        scale =  new Vector3(1, 1, 1);
    }

    public SimpleModelInstance setPosition(float x, float y, float z) {
        position = new Vector3(x, y, z);
        return this;
    }
    public SimpleModelInstance setPosition(Vector3 newPosition) {
        position = newPosition;
        return this;
    }
    public Vector3 getPosition() {
        return position;
    }
    public SimpleModelInstance setRotation(float x, float y, float z) {
        rotation = new Vector3(x, y, z);
        return this;
    }
    public SimpleModelInstance setRotation(Vector3 newRotation) {
        rotation = newRotation;
        return this;
    }
    public Vector3 getRotation() {
        return rotation;
    }
    public SimpleModelInstance setScale(float x, float y, float z) {
        scale = new Vector3(x, y, z);
        return this;
    }
    public SimpleModelInstance setScale(float scale) {
        setScale(scale, scale, scale);
        return this;
    }

    public SimpleModelInstance setColor(Color color) {
        materials.get(0).set(ColorAttribute.createDiffuse(color));
        return this;
    }
    public SimpleModelInstance setOpacity(float opacity) {
        materials.get(0).set(new BlendingAttribute(opacity));
        return this;
    }
    public SimpleModelInstance setColor(float r, float g, float b) {
        materials.get(0).set(ColorAttribute.createDiffuse(new Color(r, g, b, 1)));
        return this;
    }

    public void render(ModelBatch batch) {
        transform.setFromEulerAngles(rotation.y, rotation.x, rotation.z);
        transform.setTranslation(position.x, position.y, position.z);
        transform.scale(scale.x, scale.y, scale.z);
        batch.render(this);
    }

    public void dispose() {

    }
}
