package com.ceat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.ceat.game.entity.Player;
import com.ceat.game.gui.GameGui;

public class Game {
    private Grid grid;
    private Player player;
    private CoolCamera camera;
    private GameGui gameGui;
    private int playerX;
    private int playerY;

    public Game() {
        grid = new Grid().checkTiles();
        player = new Player(grid);
        camera = new CoolCamera();
        gameGui = new GameGui();
        grid.setPlayer(player);
        player.setParentTile(grid.getTile(0, 0));
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

    public void renderModels(ModelBatch batch) {
        camera.render(Master.getDeltaTime());
        grid.render(batch);
        player.render();
        player.draw(batch);
    }
    public void renderGui(SpriteBatch batch) {
        gameGui.render();
        gameGui.draw(batch);
    }

    public void rotateCamera(float xRot, float yRot) {
        camera.rotateHorizontal(xRot);
        camera.rotateVertical(yRot);
    }
}
