package com.ceat.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ceat.game.Font;
import com.ceat.game.Master;

public class GameGui {
    public final DistanceLabel distanceLabel = new DistanceLabel();
    public final TimerLabel timerLabel = new TimerLabel();
    public final DeathLabel deathLabel = new DeathLabel();
    private BitmapFont epicFont;
    private float lifetime;

    public GameGui() {
        epicFont = Font.create(new Font.ParamSetter() {
            public void run (FreeTypeFontGenerator.FreeTypeFontParameter params) {
                params.size = 100;
            }
        });
    }

    public void render() {
        lifetime += Master.getDeltaTime();
    }
    public void draw(SpriteBatch batch) {
        distanceLabel.draw(batch);
        timerLabel.draw(batch);
        deathLabel.draw(batch);
        epicFont.draw(batch, "hello", 0 + Font.getTextWidth(epicFont, "hello"), 0 + Font.getTextHeight(epicFont, "hello") + (float)Math.sin(lifetime)*10);
    }

    public void dispose() {
        distanceLabel.dispose();
        timerLabel.dispose();
        deathLabel.dispose();
    }
}
