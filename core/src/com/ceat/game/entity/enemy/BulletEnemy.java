package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Grid;

public class BulletEnemy extends Enemy {
    public BulletEnemy(Grid grid, int initX, int initY) {
        super(grid, initX, initY);
    }

    public Color getColor() {
        return new Color(0, 1, 0, 1);
    }
}
