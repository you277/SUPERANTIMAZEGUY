package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.entity.GridTile;
import com.ceat.game.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private static int gridDistance = 5;
    private float lifetime;
    private ArrayList<GridTile> gridTiles;
    private ArrayList<GridTile> removingTiles;
    private Player player;
    private int targetX;
    private int targetY;

    public Grid() {
        gridTiles = new ArrayList<>();
        removingTiles = new ArrayList<>();
        checkTiles();
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

    public Grid checkTiles() {
        HashMap<String, Integer> wow = new HashMap<>();
        for (int i = 0; i < gridTiles.size(); i++) {
            GridTile tile = gridTiles.get(i);
            wow.put(tile.getX() + "~" + tile.getY(), i);
        }
        for (int xOffset = -gridDistance; xOffset <= gridDistance; xOffset++) {
            for (int yOffset = -gridDistance; yOffset <= gridDistance; yOffset++) {
                int x = targetX + xOffset;
                int y = targetY + yOffset;
                String index = x + "~" + y;
                if (!wow.containsKey(index)) {
                    gridTiles.add(new GridTile(x, y, this) {
                        public void onDispose() {
                            removingTiles.remove(this);
                        }
                    });
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
        return this;
    }

    public GridTile getTile(int x, int y) {
        for (GridTile tile: gridTiles) {
            if (x == tile.getX() && y == tile.getY()) {
                return tile;
            }
        }
        return null;
    }

    public void render(ModelBatch batch) {
        lifetime += Master.getDeltaTime();
        checkTiles();
        for (GridTile tile: removingTiles) {
            tile.render(lifetime, batch);
        }
        for (GridTile tile: gridTiles) {
            tile.render(lifetime, batch);
        }
//        player.render();
//        player.draw(batch);
    }
}
