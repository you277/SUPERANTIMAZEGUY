package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.entity.GridEntity;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;
import com.ceat.game.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private static int gridDistance = 5;
    private float lifetime;
    private final ArrayList<GridTile> gridTiles;
    private final ArrayList<GridTile> removingTiles;
    private Player player;
    private int targetX;
    private int targetY;

    public Grid() {
        gridTiles = new ArrayList<>();
        removingTiles = new ArrayList<>();
        checkTiles(targetX, targetY);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTargetPosition(int x, int y) {
        targetX = x;
        targetY = y;
    }

    public Vector3 imagineTilePosition(int x, int y) {
        return new Vector3(x*6, (float)Math.sin(lifetime*2 + x + y)*0.7f, y*6);
    }

    private void cullGridEntities(ArrayList<GridEntity> entities, ArrayList<GridTile> tiles) {
        ArrayList<GridEntity> toAnnihilate = new ArrayList<>();
        for (GridEntity entity: entities) {
            for (GridTile tile: tiles) {
                if (entity.getParentTile() == tile) {
                    entity.dispose();
                    toAnnihilate.add(entity);
                    break;
                }
            }
        }
        for (GridEntity entity: toAnnihilate) {
            entities.remove(entity);
        }
    }

    public ArrayList<GridTile> checkTiles(int playerX, int playerY, ArrayList<Enemy> enemies) {
        HashMap<String, Integer> wow = new HashMap<>();
        for (int i = 0; i < gridTiles.size(); i++) {
            GridTile tile = gridTiles.get(i);
            wow.put(tile.getX() + "~" + tile.getY(), i);
        }
        ArrayList<GridTile> newTiles = new ArrayList<>();
        for (int xOffset = -gridDistance + 1; xOffset < gridDistance; xOffset++) {
            for (int yOffset = -gridDistance + 1; yOffset < gridDistance; yOffset++) {
                int x = targetX + xOffset;
                int y = targetY + yOffset;
                String index = x + "~" + y;
                if (!wow.containsKey(index)) {
                    GridTile coolnewTile = new GridTile(x, y, this, (playerX == x && playerY == y) || Math.random() < 0.8) {
                        public void onDispose() {
                            removingTiles.remove(this);
                        }
                    };
                    gridTiles.add(coolnewTile);
                    newTiles.add(coolnewTile);
                }
            }
        }
        ArrayList<GridTile> tilesToRemove = new ArrayList<>();
        for (GridTile tile: gridTiles) {
            int xDist = Math.abs(tile.getX() - targetX);
            int yDist = Math.abs(tile.getY() - targetY);
            if ((xDist > gridDistance || yDist > gridDistance) && tile.getActive()) {
                tilesToRemove.add(tile);
                tile.exit();
            }
        }
        for (GridTile tile: tilesToRemove) {
            gridTiles.remove(tile);
            removingTiles.add(tile);
        }
        new Schedule().wait(0.3f).run(new Schedule.Task() {
            public void run() {
                ArrayList<GridEntity> everyEntityEver = new ArrayList<>();
                if (enemies != null) {
                    for (Enemy enemy : enemies) {
                        everyEntityEver.add(enemy);
                    }
                }
                cullGridEntities(everyEntityEver, tilesToRemove);
                for (GridTile tile: tilesToRemove) {
                    tile.dispose();
                    removingTiles.remove(tile);
                }
            }
        });
        return newTiles;
    }

    public ArrayList<GridTile> checkTiles(int playerX, int playerY) {
        return checkTiles(playerX, playerY, null);
    }

    public GridTile getTile(int x, int y) {
        for (GridTile tile: gridTiles) {
            if (x == tile.getX() && y == tile.getY()) {
                return tile;
            }
        }
        return null;
    }

    public void checkTiles(IntVector2 pos) {
        checkTiles(pos.getX(), pos.getY());
    }

    public void render(ModelBatch batch) {
        lifetime += Master.getDeltaTime();
        checkTiles(targetX, targetY);
        for (GridTile tile: removingTiles) {
            tile.render(lifetime, batch);
        }
        for (GridTile tile: gridTiles) {
            tile.render(lifetime, batch);
        }
    }
}
