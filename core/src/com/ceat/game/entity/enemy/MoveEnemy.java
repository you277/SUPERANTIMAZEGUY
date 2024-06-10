package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.ceat.game.Grid;
import com.ceat.game.IntVector2;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;

public class MoveEnemy extends Enemy {
    public MoveEnemy(Grid grid, int initX, int initY) {
        super(grid, initX, initY);
    }

    public Color getColor() {
        return new Color(0, 0, 1, 1);
    }

    public String getName() {
        return "BLUE ENEMY";
    }

    public GridTile randomMove() {
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
            if (Enemy.overlapsOtherEnemy(this, nextX, nextY)) return null;
            return tile;
        }
        return null;
    }
    public GridTile getPlayerTargetingNextTile(Player player) {
        IntVector2 pos = getGridPosition();
        int nextX = pos.getX();
        int nextY = pos.getY();
        IntVector2 playerPos = player.getGridPosition();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();
        if (nextX > playerX) {
            nextX--;
        } else if (nextX < playerX) {
            nextX++;
        }
        if (nextY > playerY) {
            nextY--;
        } else if (nextY < playerY) {
            nextY++;
        }
        GridTile tile1 = getGrid().getTile(nextX, pos.getY());
        GridTile tile2 = getGrid().getTile(pos.getX(), nextY);
        if (tile1 != null && (
                !tile1.getIsExistent() ||
                pos.equals(tile1.getX(), tile1.getY()) ||
                Enemy.overlapsOtherEnemy(this, tile1.getX(), tile1.getY())
        )) {
            tile1 = null;
        }
        if (tile2 != null && (
                !tile2.getIsExistent() ||
                        pos.equals(tile2.getX(), tile2.getY()) ||
                        Enemy.overlapsOtherEnemy(this, tile2.getX(), tile2.getY())
        )) {
            tile2 = null;
        }
        if (tile1 != null && tile2 != null) {
            if (Math.random() < 0.5) return tile1;
            return tile2;
        }
        if (tile1 != null) {
            return tile1;
        }
        return tile2; // null or GridTile
    }
    public GridTile getTargetGridTile(Player player) {
        GridTile tile = getPlayerTargetingNextTile(player);
        if (tile != null) return tile;
        return randomMove();
    }
    private int turn = 0;
    public void doTurn(Player player) {
        turn++;
        if (turn%2 == 0) return;
        GridTile tile = getTargetGridTile(player);
        if (tile != null) {
            animateJump(tile);
            setGridPosition(tile.getX(), tile.getY());
        }
    }
}
