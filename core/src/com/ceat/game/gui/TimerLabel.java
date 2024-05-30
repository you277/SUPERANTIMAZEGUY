package com.ceat.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ceat.game.Font;
import com.ceat.game.Master;

public class TimerLabel {
    private BitmapFont font;
    private float yOffset;
    private float lifetime;
    public TimerLabel() {
        font = Font.create(new Font.ParamSetter() {
            public void run (FreeTypeFontGenerator.FreeTypeFontParameter params) {
                params.size = 15;
            }
        });
    }

    public void draw(SpriteBatch batch) {
        lifetime += Master.getDeltaTime();
        float seconds = (float)Math.floor((lifetime%60)*1000)/1000; // with millis
        int minutes = (int)lifetime/60;
        font.draw(batch, (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds), 10, Gdx.graphics.getHeight()*0.75f);
    }

    public void dispose() {
        font.dispose();
    }
}
