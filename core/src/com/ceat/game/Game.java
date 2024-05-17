package com.ceat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.ceat.game.entity.Player;

public class Game {
    private Grid grid;
    private Player player;
    private CoolCamera camera;
    private int playerX;
    private int playerY;

    public Game() {
        grid = new Grid().checkTiles();
        player = new Player(grid);
        camera = new CoolCamera();
        grid.setPlayer(player);
        player.setParentTile(grid.getTile(0, 0));
        System.out.println(grid.getTile(0, 0) == null);
    }

    public void keydown(int keycode) {
        int oldX = playerX;
        int oldY = playerY;
        switch (keycode) {
            case Input.Keys.W:
                playerY--;
                break;
            case Input.Keys.A:
                playerX--;
                break;
            case Input.Keys.S:
                playerY++;
                break;
            case Input.Keys.D:
                playerX++;
                break;
        }
        if (oldX != playerX || oldY != playerY) {
            grid.setTargetPosition(playerX, playerY);
            grid.checkTiles();
//            player.animateJump(grid.getTile(playerX, playerY));
            player.setGridPosition(playerX, playerY);
            camera.setFocusPosition(playerX, playerY);
        }
    }

    public CoolCamera getCamera() {
        return camera;
    }

    public void render(SpriteBatch batch, ModelBatch modelBatch) {
        camera.render(Master.getDeltaTime());
        grid.render(modelBatch);
        player.render();
        player.draw(modelBatch);
    }

    public void rotateCamera(float xRot, float yRot) {
        camera.rotateHorizontal(xRot);
        camera.rotateVertical(yRot);
    }
}
