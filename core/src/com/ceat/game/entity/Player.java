package com.ceat.game.entity;

import com.ceat.game.Grid;

public class Player extends GridEntity {
    public Player(Grid grid) {
        super(grid);
        getModel().setColor(1, 1, 0);
    }
}
