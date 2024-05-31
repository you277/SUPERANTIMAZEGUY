package com.ceat.game.entity;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.SimpleModelInstance;

public class Bullet {
    private Vector3 position;
    private Vector3 velocity;
    private SimpleModelInstance model;
    private float lifetime;
    public Bullet(Vector3 initPosition, Vector3 direction, float speed) {
        position = initPosition;
        velocity = new Vector3(direction.x*speed, direction.y*speed, direction.z*speed);
        model = new SimpleModelInstance(SimpleModelInstance.sphereModel).setColor(1, 1, 1).setScale(0.75f);
    }

    public boolean step(float delta) {
        lifetime += delta;
        if (lifetime > 3) {
            return false;
        }
        position = new Vector3(position.x + velocity.x*delta, position.y + velocity.y*delta, position.z + velocity.z*delta);
        model.setPosition(position);
        return true;
    }

    public void draw(ModelBatch batch) {
        model.render(batch);
    }

    public void dispose() {
        model.dispose();
    }
}
