package com.ceat.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ceat.game.Font;
import com.ceat.game.Lerp;
import com.ceat.game.Master;

public class DistanceLabel {
    private BitmapFont font;
    private float distance;
    private float yOffset;
    public DistanceLabel() {
        font = Font.create(new Font.ParamSetter() {
            public void run (FreeTypeFontGenerator.FreeTypeFontParameter params) {
                params.size = 15;
            }
        });
    }
    public void setDistance(int x, int y) {
        distance = (float)Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))*1000)/1000;
        yOffset = 20;
    }

    public void draw(SpriteBatch batch) {
        yOffset = Lerp.lerp(yOffset, 0, Lerp.alpha(Master.getDeltaTime(), 10));
        String distText = "DISTANCE: " + distance;
        font.draw(batch, distText, Gdx.graphics.getWidth()/2 - Font.getTextWidth(font, distText)/2, 150 + yOffset);
    }

    public void dispose() {
        font.dispose();
    }
}
