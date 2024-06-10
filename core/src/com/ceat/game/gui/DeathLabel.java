package com.ceat.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ceat.game.Font;

public class DeathLabel {
    private BitmapFont font;
    private String reason;
    public DeathLabel() {
        font = Font.create(new Font.ParamSetter() {
            public void run (FreeTypeFontGenerator.FreeTypeFontParameter params) {
                params.size = 15;
            }
        });
    }

    public void enable(String reason) {
        this.reason = "YOU DIED BY " + reason;
    }

    public void draw(SpriteBatch batch) {
        if (reason == null) return;
        font.draw(batch, reason, Gdx.graphics.getWidth()/2 - Font.getTextWidth(font, reason)/2, 400);
    }

    public void dispose() {
        font.dispose();
    }
}
