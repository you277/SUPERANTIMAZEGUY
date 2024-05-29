package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Game;
import com.ceat.game.Grid;
import com.ceat.game.SimpleModelInstance;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.fx.ModelParticles;
import com.ceat.game.fx.SpawnBeam;

public class Enemy extends GridEntity {
    public Enemy(Grid grid, int initX, int initY) {
        super(grid);
        getModel().setColor(1, 0, 0);
        setGridPosition(initX, initY);
        spawnEffect(new Color(1, 0, 0, 1), initX, initY);
    }

    public void spawnEffect(Color color, int initX, int initY) {
        Game.current.addParticles(new ModelParticles(SimpleModelInstance.sphereModel)
                .setColor(color.r, color.g, color.g)
                .setScale(3, 0)
                .setDirection(new Vector3(0, 1, 0))
                .setSpread((float)Math.random()*3.14f, (float)Math.random()*3.14f)
                .setEnabled(false)
                .setLifetime(0.5f, 1f)
                .setOrigin(getGrid().imagineTilePosition(initX, initY))
                .setSpeed(20, 30)
                .emit(5)
        );
        new SpawnBeam(color, initX*6, initY*6);
    }
}
