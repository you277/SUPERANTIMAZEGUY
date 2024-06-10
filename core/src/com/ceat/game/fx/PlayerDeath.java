package com.ceat.game.fx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Game;
import com.ceat.game.Loop;
import com.ceat.game.SimpleModelInstance;

public class PlayerDeath extends Effect {
    private Vector3 position;

    public PlayerDeath(Vector3 position) {
        this.position = position;
        play();
    }

    ModelParticles p1;
    ModelParticles p2;

    public void play() {
        p1 = new ModelParticles(SimpleModelInstance.sphereModel, ModelParticles.TimeScale.ABSOLUTE)
                .setOrigin(position)
                .setRate(25)
                .setScale(3, 100)
                .setLifetime(0.2f)
                .setColor(new Color(1, 1, 1, 1))
                .setEnabled(true);
        p2 = new ModelParticles(SimpleModelInstance.cubeModel, ModelParticles.TimeScale.ABSOLUTE)
                .setOrigin(position)
                .setEnabled(false)
                .setScale(75, 0)
                .setLifetime(1)
                .setColor(new Color(1, 1, 1, 1))
                .setRotSpeed(-360, 360)
                .emit(1);
        Game.current.addParticles(p1);
        Game.current.addParticles(p2);
        new Loop(1, Loop.LoopType.ABSOLUTE) {
            public void onEnd() {
                p1.setEnabled(false);
            }
        };
    }
}
