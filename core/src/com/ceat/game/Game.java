package com.ceat.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;
import com.ceat.game.entity.enemy.Enemy;
import com.ceat.game.fx.ModelParticles;
import com.ceat.game.gui.GameGui;

import java.util.ArrayList;

public class Game {
    public static Game current;
    private Grid grid;
    private final Player player;
    private final CoolCamera camera;
    private GameGui gameGui;
    private int playerX;
    private int playerY;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private boolean allowMovementInput = true;

    // effect stuff;
    private ArrayList<ModelParticles> modelParticles = new ArrayList<>();

    public Game() {
        grid = new Grid();
        grid.checkTiles(0, 0);
        player = new Player(grid);
        camera = new CoolCamera();
        gameGui = new GameGui();
        grid.setPlayer(player);
        player.setParentTile(grid.getTile(0, 0));
        current = this;
    }

    public void addParticles(ModelParticles modelParticle) {
        modelParticles.add(modelParticle);
    }

    private void rollEnemySpawn(GridTile tile) {
        if (!tile.getIsExistent()) return;
        if (Math.random() < 0.3) {
            enemies.add(new Enemy(grid, tile.getX(), tile.getY()));
        }
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
            if (!allowMovementInput) {
                playerX = oldX;
                playerY = oldY;
                return;
            }
            GridTile tile = grid.getTile(playerX, playerY);
            if (tile.getIsExistent()) {
                grid.setTargetPosition(playerX, playerY);
                ArrayList<GridTile> newTiles = grid.checkTiles(playerX, playerY, enemies);
                grid.setTargetPosition(oldX, oldY);
                for (GridTile newTile: newTiles) {
                    rollEnemySpawn(newTile);
                }
                player.animateJump(grid.getTile(playerX, playerY));
                player.setGridPosition(playerX, playerY);
                camera.setFocusPosition(playerX, playerY);
                allowMovementInput = false;
                new Schedule().wait(GridEntity.jumpDuration).run(new Schedule.Task() {
                    public void run() {
                        allowMovementInput = true;
                    }
                });
            } else {
                playerX = oldX;
                playerY = oldY;
                player.animateJump(grid.getTile(oldX, oldY));
            }
        }
    }

    public CoolCamera getCamera() {
        return camera;
    }

    public void renderModels(ModelBatch batch) {
        float delta = Master.getDeltaTime();
        camera.render(delta);
        grid.render(batch);
        player.render();
        for (Enemy enemy: enemies) {
            enemy.render();
        }
        ArrayList<ModelParticles> modelParticlesToRemove = new ArrayList<>();
        for (ModelParticles emitter: modelParticles) {
            if (!emitter.step(delta)) {
                emitter.dispose();
                modelParticlesToRemove.add(emitter);
            }
        }
        for (ModelParticles emitter: modelParticlesToRemove) {
            modelParticles.remove(emitter);
        }
        player.draw(batch);
        for (ModelParticles emitter: modelParticles) {
            emitter.draw(batch);
        }
        for (Enemy enemy: enemies) {
            enemy.draw(batch);
        }
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
