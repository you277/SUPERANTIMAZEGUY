package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class SimpleModelInstance extends ModelInstance {
    private Vector3 position;
    private Vector3 rotation; // euler x y z
    public SimpleModelInstance(Model model) {
        super(model);
        position = new Vector3();
        rotation = new Vector3();
    }

    public SimpleModelInstance setPosition(float x, float y, float z) {
        position = new Vector3(x, y, z);
        return this;
    }
    public SimpleModelInstance setPosition(Vector3 newPosition) {
        position = newPosition;
        return this;
    }
    public SimpleModelInstance setRotation(float x, float y, float z) {
        rotation = new Vector3(x, y, z);
        return this;
    }
    public SimpleModelInstance setRotation(Vector3 newRotation) {
        rotation = newRotation;
        return this;
    }

    public void render(ModelBatch batch) {
        transform.setFromEulerAngles(rotation.y, rotation.x, rotation.z);
        transform.setTranslation(position.x, position.y, position.z);
        batch.render(this);
    }

    public void dispose() {

    }
}
