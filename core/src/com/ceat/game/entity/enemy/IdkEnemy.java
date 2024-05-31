package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.ceat.game.Grid;
import com.ceat.game.IntVector2;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;

public class IdkEnemy extends Enemy {
    private static int[][] movements = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    public IdkEnemy(Grid grid, int initX, int initY) {
        super(grid, initX, initY);
    }

    public Color getColor() {
        return new Color(1, 0, 1, 1);
    }

    private boolean forward = true;
    private int moveStage;

    public void doTurn(Player player) {
        int[] moveDir = movements[moveStage];
        IntVector2 pos = getGridPosition();
        GridTile nextTile = getGrid().getTile(pos.getX() + moveDir[0], pos.getY() + moveDir[1]);
        if (nextTile == null || !nextTile.getIsExistent() || Enemy.overlapsOtherEnemy(this, nextTile.getX(), nextTile.getY())) {
            forward = !forward;
        } else {
            animateJump(nextTile);
            setGridPosition(nextTile.getX(), nextTile.getY());
        }
        if (forward) {
            moveStage++;
            if (moveStage > 3) {
                moveStage = 0;
            }
        } else {
            moveStage--;
            if (moveStage < 0) {
                moveStage = 3;
            }
        }
    }
}
