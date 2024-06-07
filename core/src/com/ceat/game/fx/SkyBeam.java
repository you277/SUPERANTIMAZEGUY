package com.ceat.game.fx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.ceat.game.Lerp;
import com.ceat.game.Loop;
import com.ceat.game.SimpleModelInstance;



public class SkyBeam extends  Effect {
    private SimpleModelInstance model;
    private float rotation;

    public EffectType getType() {
        return EffectType.MODEL;
    }

    public SkyBeam(Color color, float x, float y) {
        model = new SimpleModelInstance(SimpleModelInstance.cubeModel)
                .setColor(color)
                .setPosition(x, 0, y)
                .setScale(1, 100, 1);
        registerEffect();
        new Loop(0.1f) {
            public void run(float delta, float elapsed) {
                rotation += delta*90;
                float scale = Lerp.lerp(1, 0, elapsed/0.1f);
                model.setRotation(0, rotation, 0).setScale(scale, 100, scale);
            }
            public void onEnd() {
                dispose();
                unregisterEffect();
            }
        };
    }
    public void draw(ModelBatch batch) {
        model.render(batch);
    }

    public void dispose() {
        model.dispose();
    }
}
