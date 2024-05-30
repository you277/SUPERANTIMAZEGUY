package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Game;
import com.ceat.game.Grid;
import com.ceat.game.IntVector2;
import com.ceat.game.entity.GridTile;

public class RandomEnemy extends Enemy {
    public RandomEnemy(Grid grid, int initX, int initY) {
        super(grid, initX, initY);
    }

    public Color getColor() {
        return new Color(0, 0, 1, 1);
    }

    public void doTurn() {
        int[] offset = {0, 0};
        int rand = (int)(Math.random()*4);
        switch (rand) {
            case 0:
                offset = new int[] {-1, 0};
                break;
            case 1:
                offset = new int[] {1, 0};
                break;
            case 2:
                offset = new int[] {0, -1};
                break;
            case 3:
                offset = new int[] {0, 1};
                break;
        }
        IntVector2 pos = getGridPosition();
        int nextX = pos.getX() + offset[0];
        int nextY = pos.getY() + offset[1];
        GridTile tile = getGrid().getTile(nextX, nextY);
        if (tile != null && tile.getIsExistent()) {
            for (Enemy enemy: Game.current.getEnemies()) {
                if (enemy == this) continue;
                if (enemy.getGridPosition().equals(nextX, nextY)) return;
            }
            animateJump(tile);
            setGridPosition(nextX, nextY);
        }
    }
}
