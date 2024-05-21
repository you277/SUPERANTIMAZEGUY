package com.ceat.game.entity;

import com.badlogic.gdx.math.Vector3;
import com.ceat.game.SimpleModelInstance;

public class Entity {
    private int gridX;
    private int gridY;
    private float x;
    private float y;
    private float z;
    private SimpleModelInstance model;
    public Entity() {
        model = new SimpleModelInstance(SimpleModelInstance.planeModel);
    }

    public SimpleModelInstance getModel() {
        return model;
    }

    public void setAbsolutePosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = y;
        model.setPosition(x, y, z);
    }
    public void setAbsolutePosition(Vector3 position) {
        setAbsolutePosition(position.x, position.y, position.z);
    }

    public Vector3 getAbsolutePosition() {
        return new Vector3(x, y, z);
    }

    public void dispose() {

    }
}
