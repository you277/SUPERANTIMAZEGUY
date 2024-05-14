package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.HashMap;

public class Grid {
    private static int gridDistance = 5;
    private float lifetime;
    private HashMap<String, GridTile> gridModels;
    private int targetX;
    private int targetY;

    public void setTargetPosition(int x, int y) {
        targetX = x;
        targetY = y;
    }

    public Grid() {
        gridModels = new HashMap<>();
        checkTiles();
    }

    public void checkTiles() {
        for (int xOffset = -gridDistance; xOffset <= gridDistance; xOffset++) {
            for (int yOffset = -gridDistance; yOffset <= gridDistance; yOffset++) {
                int x = targetX + xOffset;
                int y = targetY + yOffset;
                String index = x + "~" + y;
                if (!gridModels.containsKey(index)) {
                    gridModels.put(index, new GridTile(x, y) {
                        public void onDispose() {
                            gridModels.remove(index);
                        }
                    });
                }
            }
        }
        for (String i: gridModels.keySet()) {
            GridTile tile = gridModels.get(i);
            int xDist = Math.abs(tile.getX() - targetX);
            int yDist = Math.abs(tile.getY() - targetY);
            if ((xDist > gridDistance || yDist > gridDistance) && tile.getActive()) {
                tile.exit();
            }
        }
    }

    public void render(ModelBatch batch) {
        lifetime += Master.getDeltaTime();
        checkTiles();
        for (String i: gridModels.keySet()) {
            GridTile tile = gridModels.get(i);
            tile.render(lifetime, batch);
        }
    }
}
