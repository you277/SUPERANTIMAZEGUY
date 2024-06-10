package com.ceat.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.entity.Bullet;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;
import com.ceat.game.entity.enemy.BulletEnemy;
import com.ceat.game.entity.enemy.Enemy;
import com.ceat.game.entity.enemy.IdkEnemy;
import com.ceat.game.entity.enemy.MoveEnemy;
import com.ceat.game.fx.ModelParticles;
import com.ceat.game.fx.PlayerDeath;
import com.ceat.game.fx.SkyBeam;
import com.ceat.game.gui.GameGui;

import java.util.ArrayList;

public class Game {
    public static Game current;
    private boolean active = true;
    private final Grid grid;
    private final Player player;
    private final CoolCamera camera;
    private final GameGui gameGui;
    private int playerX;
    private int playerY;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private boolean allowMovementInput = true;

    // effect stuff;
    private final ArrayList<ModelParticles> modelParticles = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

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

    public Enemy getEnemyFromPosition(int x, int y) {
        for (Enemy enemy: enemies) {
            if (enemy.getGridPosition().equals(x, y)) {
                return enemy;
            }
        }
        return null;
    }

    private void rollEnemySpawn(GridTile tile) {
        if (!tile.getIsExistent()) return;
        float distance = (float)Math.sqrt(Math.pow(playerX, 2) + Math.pow(playerY, 2));
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

    private GridTile getRandomTileInDist(int dist, int recurDepth) {
        if (recurDepth > 10) return null;
        int x = playerX - dist/2 + (int)(Math.random()*dist + 0.5);
        int y = playerY - dist/2 + (int)(Math.random()*dist + 0.5);
        GridTile tile = grid.getTile(x, y);
        if (tile == null || !tile.getIsExistent()) return getRandomTileInDist(dist, recurDepth + 1);
        return tile;
    }

    private GridTile getRandomTileInDist(int dist) {
        return getRandomTileInDist(dist, 0);
    }

    private void attack1() {
        ArrayList<ModelParticles> particles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GridTile tile = getRandomTileInDist(3);
            if (tile == null) continue;
            Enemy enemyHit = getEnemyFromPosition(tile.getX(), tile.getY());
            new SkyBeam(new Color(1, 1, 1, 1), tile.getX()*6, tile.getY()*6);
            if (enemyHit != null) {
                enemyHit.dispose();
                enemies.remove(enemyHit);
                camera.shake(5, 0.3f);
                ModelParticles particlesWow = new ModelParticles(SimpleModelInstance.sphereModel)
                        .setColor(new Color(1, 1, 1, 1))
                        .setScale(3, 0)
                        .setDirection(new Vector3(0, 1, 0))
                        .setSpread((float)Math.random()*3.14f/3, (float)Math.random()*3.14f/3)
                        .setEnabled(true)
                        .setLifetime(0.5f, 1f)
                        .setRate(3)
                        .setOrigin(tile.getAbsolutePosition())
                        .setSpeed(30, 50)
                        .emit(5);
                addParticles(particlesWow);
                particles.add(particlesWow);
            }
        }
        if (!particles.isEmpty()) {
            new Schedule().wait(0.6f).run(new Schedule.Task() {
                public void run() {
                    for (ModelParticles particle5000: particles) {
                        particle5000.setEnabled(false);
                    }
                }
            });
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
            case Input.Keys.NUM_1:
                if (!active) return;
                attack1();
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
                                gameGui.timerLabel.setTimerEnabled(false);
                                active = false;
                                gameGui.deathLabel.enable(enemy.getName());
                                deathSequence();
                                return;
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
    public void deathSequence() {
        player.setVisible(false);
        new PlayerDeath(player.getParentTile().getAbsolutePosition());
        camera.shake(15, 1f);
        new Loop(2, Loop.LoopType.ABSOLUTE) {
            public void run(float delta, float elapsed) {
                Master.setGameSpeed(1 - elapsed/2);
            }
            public void onEnd() {
                Master.setGameSpeed(0);
            }
        };
        new Loop(3, Loop.LoopType.ABSOLUTE) {
            public void onEnd() {
                restart();
            }
        };
    }

    public CoolCamera getCamera() {
        return camera;
    }

    public boolean getIsDeadByBullet() {
        Vector3 playerPos = player.getParentTile().getAbsolutePosition();
        for (Bullet bullet: bullets) {
            Vector3 bulletPos = bullet.getPosition();
            double magnitude = Math.sqrt(Math.pow(bulletPos.x - playerPos.x, 2) + Math.pow(bulletPos.y - playerPos.y, 2) + Math.pow(bulletPos.z - playerPos.z, 2));
            if (magnitude < 1.5) {
                return true;
            }
        }
        return false;
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
            if (!emitter.step()) {
                emitter.dispose();
                modelParticlesToRemove.add(emitter);
            }
        }
        if (active && getIsDeadByBullet()) {
            allowMovementInput = false;
            active = false;
            gameGui.deathLabel.enable("GREEN ENEMY BULLET");
            deathSequence();
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

    // to be overriden by master
    public void restart() {

    }


    public void rotateCamera(float xRot, float yRot) {
        camera.rotateHorizontal(xRot);
        camera.rotateVertical(yRot);
    }

    public void dispose() {
        player.dispose();
        for (Enemy enemy: enemies) {
            enemy.dispose();
        }
        for (ModelParticles particles: modelParticles) {
            particles.dispose();
        }
        for (Bullet bullet: bullets) {
            bullet.dispose();
        }
        gameGui.dispose();
    }
}
