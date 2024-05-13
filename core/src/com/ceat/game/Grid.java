package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.HashMap;

public class Grid {
    private float lifetime;
    private HashMap<String, GridTile> gridModels;
    private int targetX;
    private int targetY;

    private void setTargetPosition(int x, int y) {
        targetX = x;
        targetY = y;
    }

    public Grid() {
        gridModels = new HashMap<>();
        checkTiles();
    }

    public void checkTiles() {
        for (int xOffset = -2; xOffset <= 2; xOffset++) {
            for (int yOffset = -2; yOffset <= 2; yOffset++) {
                int x = targetX + xOffset;
                int y = targetY + yOffset;
                String index = x + "~" + y;
                if (!gridModels.containsKey(index)) {
                    gridModels.put(index, new GridTile(x, y));
                }
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
