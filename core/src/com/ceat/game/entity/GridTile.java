package com.ceat.game.entity;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Grid;
import com.ceat.game.Lerp;
import com.ceat.game.Loop;
import com.ceat.game.SimpleModelInstance;

public class GridTile {
    private Grid grid;
    private SimpleModelInstance model;
    private int x;
    private int y;
    private float yOffset;
    private boolean active;
    private float lifetime;
    private boolean isExisting;
    private boolean isOnBoard = true;
    public GridTile(int x, int y, Grid grid, boolean tileExists) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        isExisting = tileExists;
        if (isExisting) {
            model = new SimpleModelInstance(SimpleModelInstance.planeModel)
                    .setScale(5, 5, 5)
                    .setColor(0, 0, 0)
                    .setOpacity(0.2f);
        }
        active = true;
        yOffset = 20;
        new Loop(0.3f) {
            public void run(float dt, float elapsed) {
                yOffset = Lerp.lerp(20, 0, elapsed/0.3f);
            }
            public void onEnd() {
                yOffset = 0;
            }
        };
    }

    public boolean getIsExistent() {
        return isExisting;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getActive() {
        return active;
    }
    public Vector3 getAbsolutePosition() {
        Vector3 pos = grid.imagineTilePosition(x, y);
        return new Vector3(pos.x, pos.y + yOffset, pos.z);
    }
    public void render(ModelBatch batch) {
        if (model != null) {
            model.setPosition(getAbsolutePosition());
            model.render(batch);
        }
    }

    public void render(float lifetime, ModelBatch batch) {
        this.lifetime = lifetime;
       if (model != null) {
           model.setPosition(getAbsolutePosition());
           model.render(batch);
       }
    }

    public boolean getIsOnBoard() {
        return isOnBoard;
    }

    public void exit() {
        active = false;
        new Loop(0.3f) {
            public void run(float dt, float elapsed) {
                yOffset = Lerp.lerp(0, 20, elapsed/0.3f);
            }
            public void onEnd() {
                isOnBoard = false;
                onDispose();
                dispose();
            }
        };
    }

    public void onDispose() {}
    public void dispose() {
       if (model != null) {
           model.dispose();
       }
    }
}
