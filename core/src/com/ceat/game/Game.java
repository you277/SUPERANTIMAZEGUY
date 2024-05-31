package com.ceat.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.ceat.game.entity.Bullet;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;
import com.ceat.game.entity.enemy.BulletEnemy;
import com.ceat.game.entity.enemy.Enemy;
import com.ceat.game.entity.enemy.IdkEnemy;
import com.ceat.game.entity.enemy.MoveEnemy;
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
    private ArrayList<Bullet> bullets = new ArrayList<>();

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
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private void rollEnemySpawn(GridTile tile) {
        if (!tile.getIsExistent()) return;
        float distance = (float)Math.sqrt(Math.pow(playerX, 2) + Math.pow(playerY, 2));
        if (Math.random() < 0.3) {
            enemies.add(new BulletEnemy(grid, tile.getX(), tile.getY()));
            return;
        }
        if (Math.random() < 0.3) {
            if (distance > 10) {
                if (Math.random() < 0.3) {
                    enemies.add(new MoveEnemy(grid, tile.getX(), tile.getY()));
                    return;
                }
                if (distance > 25) {
                    if (Math.random() < 0.3) {
                        enemies.add(new IdkEnemy(grid, tile.getX(), tile.getY()));
                        return;
                    }
                    if (distance > 40) {
                        if (Math.random() < 0.3) {
                            enemies.add(new BulletEnemy(grid, tile.getX(), tile.getY()));
                            return;
                        }
                    }
                }
            }
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
            for (Enemy enemy: enemies) {
                enemy.doTurn(player);
            }
            if (tile.getIsExistent()) {
                grid.setTargetPosition(playerX, playerY);
                ArrayList<GridTile> newTiles = grid.checkTiles(playerX, playerY, enemies);
                grid.setTargetPosition(oldX, oldY);
                for (GridTile newTile: newTiles) {
                    rollEnemySpawn(newTile);
                }
                gameGui.distanceLabel.setDistance(playerX, playerY);
                player.animateJump(grid.getTile(playerX, playerY));
                player.setGridPosition(playerX, playerY);
                camera.setFocusPosition(playerX, playerY);
                allowMovementInput = false;
                new Schedule().wait(GridEntity.jumpDuration).run(new Schedule.Task() {
                    public void run() {
                        for (Enemy enemy: enemies) {
                            if (enemy.collidesWithPlayer(player)) {
                                System.out.println("dead");
                            }
                        }
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
        ArrayList<Enemy> enemiesToDestroy = new ArrayList<>();
        for (Enemy enemy: enemies) {
            if (enemy.getParentTile().getIsOnBoard()) {
                enemy.render();
            } else {
                enemiesToDestroy.add(enemy);
            }
        }
        for (Enemy enemy: enemiesToDestroy) {
            enemies.remove(enemy);
        }
        ArrayList<ModelParticles> modelParticlesToRemove = new ArrayList<>();
        for (ModelParticles emitter: modelParticles) {
            if (!emitter.step(delta)) {
                emitter.dispose();
                modelParticlesToRemove.add(emitter);
            }
        }
        ArrayList<Bullet> bulletsToErase = new ArrayList<>();
        for (Bullet bullet: bullets) {
            if (!bullet.step(delta)) {
                bulletsToErase.add(bullet);
            }
        }
        for (Bullet bullet: bulletsToErase) {
            bullets.remove(bullet);
        }
        for (ModelParticles emitter: modelParticlesToRemove) {
            modelParticles.remove(emitter);
        }
        player.draw(batch);
        for (ModelParticles emitter: modelParticles) {
            emitter.draw(batch);
        }
        for (Bullet bullet: bullets) {
            bullet.draw(batch);
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
