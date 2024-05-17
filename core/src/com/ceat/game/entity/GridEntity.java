package com.ceat.game.entity;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Grid;
import com.ceat.game.Lerp;
import com.ceat.game.Loop;
import com.ceat.game.IntVector2;
import com.ceat.game.SimpleModelInstance;

public class GridEntity extends Entity {
    private Grid grid;
    private int gridX;
    private int gridY;
    private GridTile parentTile;
    private boolean isAnimating;
    public GridEntity(Grid grid) {
        super();
        this.grid = grid;
        setGridPosition(gridX, gridY);
        getModel().setScale(200, 200, 200);
    }

    public GridEntity setParentTile(GridTile tile) {
        parentTile = tile;
        return this;
    }
    public void setGridPosition(int x, int y) {
        gridX = x;
        gridY = y;
        System.out.println(grid.getTile(x, y));
        setParentTile(grid.getTile(x, y));
    }
    public void animateJump(GridTile newParent) {
        Vector3 oldPos = parentTile.getAbsolutePosition();
        Vector3 currentNewPos = newParent.getAbsolutePosition();
        Vector3 midPos = new Vector3((oldPos.x + currentNewPos.x)/2, (oldPos.y + currentNewPos.y)/2 + 10, (oldPos.z + currentNewPos.z)/2);
        isAnimating = true;
        new Loop(0.4f) {
            public void run(float dt, float elapsed) {
                float progress = elapsed/0.4f;
                setAbsolutePosition(Lerp.threePointBezier(oldPos, midPos, newParent.getAbsolutePosition(), progress));
            }
            public void onEnd() {
                isAnimating = false;
                setAbsolutePosition(newParent.getAbsolutePosition());
            }
        };
    }

    public void render() {
        if (!isAnimating && parentTile != null) {
            setAbsolutePosition(parentTile.getAbsolutePosition());
        }
    }
    public void draw(ModelBatch batch) {
        batch.render(getModel());
    }

    public IntVector2 getGridPosition() {
        return new IntVector2(gridX, gridY);
    }
    public void dispose() {

    }
}
