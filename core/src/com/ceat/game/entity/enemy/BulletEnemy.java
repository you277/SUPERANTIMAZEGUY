package com.ceat.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Game;
import com.ceat.game.Grid;
import com.ceat.game.entity.Bullet;
import com.ceat.game.entity.Player;

public class BulletEnemy extends Enemy {
    public BulletEnemy(Grid grid, int initX, int initY) {
        super(grid, initX, initY);
    }

    public Color getColor() {
        return new Color(0, 1, 0, 1);
    }

    public void doTurn(Player player) {
        double rotation = Math.random()*Math.PI*2;
        for (int i = 0; i < 4; i++) {
            double thisRotation = rotation + Math.PI*0.5*i;
            double xDir = Math.cos(thisRotation);
            double yDir = Math.sin(thisRotation);

            Game.current.addBullet(new Bullet(getParentTile().getAbsolutePosition(), new Vector3((float)xDir, 0, (float)yDir), 9f));
        }
    }
}
