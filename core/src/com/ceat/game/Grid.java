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
    private HashMap<String, GridTile> gridModels;
    private HashMap<String, GridTile> removingModels;
    private Player player;
    private int targetX;
    private int targetY;

    public Grid() {
        gridModels = new HashMap<>();
        removingModels = new HashMap<>();
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
        for (int xOffset = -gridDistance; xOffset <= gridDistance; xOffset++) {
            for (int yOffset = -gridDistance; yOffset <= gridDistance; yOffset++) {
                int x = targetX + xOffset;
                int y = targetY + yOffset;
                String index = x + "~" + y;
                if (!gridModels.containsKey(index)) {
                    gridModels.put(index, new GridTile(x, y, this) {
                        public void onDispose() {
                            removingModels.remove(index);
                        }
                    });
                }
            }
        }
        ArrayList<String> tilesToRemove = new ArrayList<>();
        for (String i: gridModels.keySet()) {
            GridTile tile = gridModels.get(i);
            int xDist = Math.abs(tile.getX() - targetX);
            int yDist = Math.abs(tile.getY() - targetY);
            if ((xDist > gridDistance || yDist > gridDistance) && tile.getActive()) {
                tilesToRemove.add(i);
                tile.exit();
            }
        }
        for (String name: tilesToRemove) {
            GridTile tile = gridModels.get(name);
            gridModels.remove(name);
            removingModels.put(name, tile);
        }
        return this;
    }

    public GridTile getTile(int x, int y) {
        String index = x + "~" + y;
        System.out.print(index);System.out.print(gridModels.containsKey(index));System.out.println(gridModels.get(index));
        if (gridModels.containsKey(index)) {
            System.out.println(gridModels.get(index).getClass());
        }
        if (gridModels.containsKey(index)) gridModels.get(index);
        return null;
    }

    public void render(ModelBatch batch) {
        lifetime += Master.getDeltaTime();
        checkTiles();
        for (String i: removingModels.keySet()) {
            GridTile tile = removingModels.get(i);
            tile.render(lifetime, batch);
        }
        for (String i: gridModels.keySet()) {
            GridTile tile = gridModels.get(i);
            tile.render(lifetime, batch);
        }
//        player.render();
//        player.draw(batch);
    }
}
