package com.ceat.game.fx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.ArrayList;

public class Effect {
    public enum EffectType {
        MODEL,
        SPRITE,
        UNKNOWN
    }
    public static final ArrayList<Effect> effects = new ArrayList<>();
    public static void drawEffects(ModelBatch modelBatch) {
        for (Effect effect: effects) {
            if (effect.getType() == EffectType.MODEL) {
                effect.draw(modelBatch);
            }
        }
    }
    public static void drawEffects(SpriteBatch spriteBatch) {
        for (Effect effect: effects) {
            if (effect.getType() == EffectType.SPRITE) {
                effect.draw(spriteBatch);
            }
        }
    }
    public EffectType getType() {
        return EffectType.UNKNOWN;
    }
    public void registerEffect() {
        effects.add(this);
    }
    public void unregisterEffect() {
        effects.remove(this);
    }
    public void draw(ModelBatch modelBatch) {}
    public void draw(SpriteBatch batch) {}
    public void dispose() {}
}
