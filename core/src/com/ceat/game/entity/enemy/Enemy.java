package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Game;
import com.ceat.game.Grid;
import com.ceat.game.SimpleModelInstance;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.entity.Player;
import com.ceat.game.fx.ModelParticles;
import com.ceat.game.fx.SpawnBeam;

public class Enemy extends GridEntity {
    public static boolean overlapsOtherEnemy(Enemy enemy, int x, int y) {
        for (Enemy other: Game.current.getEnemies()) {
            if (other == enemy) continue;
            if (other.getGridPosition().equals(x, y)) return true;
        }
        return false;
    }
    public Enemy(Grid grid, int initX, int initY) {
        super(grid);
        getModel().setColor(getColor());
        setGridPosition(initX, initY);
        spawnEffect(initX, initY);
    }

    public Color getColor() {
        return new Color(1, 0, 0, 1);
    }

    public void spawnEffect(int initX, int initY) {
        Game.current.addParticles(new ModelParticles(SimpleModelInstance.sphereModel)
                .setColor(getColor())
                .setScale(3, 0)
                .setDirection(new Vector3(0, 1, 0))
                .setSpread((float)Math.random()*3.14f, (float)Math.random()*3.14f)
                .setEnabled(false)
                .setLifetime(0.5f, 1f)
                .setOrigin(getGrid().imagineTilePosition(initX, initY))
                .setSpeed(20, 30)
                .emit(5)
        );
        new SpawnBeam(getColor(), initX*6, initY*6);
    }

    public void doTurn(Player player) {

    }

    public boolean collidesWithPlayer(Player player) {
        return getGridPosition().equals(player.getGridPosition());
    }
}
