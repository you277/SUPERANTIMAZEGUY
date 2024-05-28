package com.ceat.game.entity.enemy;

import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Game;
import com.ceat.game.Grid;
import com.ceat.game.SimpleModelInstance;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.fx.ModelParticles;

public class Enemy extends GridEntity {
    public Enemy(Grid grid, int initX, int initY) {
        super(grid);
        getModel().setColor(1, 0, 0);
        setGridPosition(initX, initY);
        Game.current.addParticles(new ModelParticles(SimpleModelInstance.sphereModel)
                .setColor(1, 0, 0)
                .setScale(3, 0)
                .setDirection(new Vector3(0, 1, 0))
                .setSpread((float)Math.random()*3.14f, (float)Math.random()*3.14f)
                .setLifetime(0.5f, 1f)
        );
    }
}
