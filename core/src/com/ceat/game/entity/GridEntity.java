package com.ceat.game.entity;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Grid;
import com.ceat.game.Lerp;
import com.ceat.game.Loop;
import com.ceat.game.IntVector2;

public class GridEntity extends Entity {
    public static final float jumpDuration = 0.15f;
    private final Grid grid;
    private int gridX;
    private int gridY;
    private GridTile parentTile;
    private boolean isAnimating;
    private boolean isVisible = true;
    public GridEntity(Grid grid) {
        super();
        this.grid = grid;
        setGridPosition(gridX, gridY);
        getModel().setScale(3, 3, 3);
    }

    public GridEntity setParentTile(GridTile tile) {
        parentTile = tile;
        return this;
    }
    public GridTile getParentTile() {
        return  parentTile;
    }
    public void setGridPosition(int x, int y) {
        gridX = x;
        gridY = y;
        setParentTile(grid.getTile(x, y));
    }
    public Grid getGrid() {
        return grid;
    }
    public void animateJump(GridTile newParent) {
        Vector3 oldPos = parentTile.getAbsolutePosition();
        Vector3 currentNewPos = newParent.getAbsolutePosition();
        Vector3 midPos = new Vector3((oldPos.x + currentNewPos.x)/2, (oldPos.y + currentNewPos.y)/2 + 10, (oldPos.z + currentNewPos.z)/2);
        isAnimating = true;
        new Loop(jumpDuration) {
            public void run(float dt, float elapsed) {
                float progress = elapsed/jumpDuration;
                Vector3 p = Lerp.threePointBezier(oldPos, midPos, newParent.getAbsolutePosition(), progress);
                setAbsolutePosition(p.x, p.y + 0.1f, p.z);
            }
            public void onEnd() {
                isAnimating = false;
                setAbsolutePosition(newParent.getAbsolutePosition());
            }
        };
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void render() {
        if (!isAnimating && parentTile != null) {
            Vector3 p = parentTile.getAbsolutePosition();
            setAbsolutePosition(p.x, p.y + 0.1f, p.z);
        }
    }
    public void draw(ModelBatch batch) {
        if (!isVisible) return;
        getModel().render(batch);
    }

    public IntVector2 getGridPosition() {
        return new IntVector2(gridX, gridY);
    }
    public void dispose() {
        getModel().dispose();
    }
}
