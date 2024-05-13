package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class GridTile {
    private SimpleModelInstance model;
    private int x;
    private int y;
    public GridTile(int x, int y) {
        model = new SimpleModelInstance(SimpleModelInstance.planeModel)
                .setScale(5, 0, 5)
                .setColor(1, 1, 1, 1);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void render(float lifetime, ModelBatch batch) {
        model.setPosition(x*6, (float)Math.sin(lifetime + x + y)*3, y*6);
        model.render(batch);
    }

    public void dispose() {
        model.dispose();
    }
}
