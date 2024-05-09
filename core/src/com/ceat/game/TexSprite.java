package com.ceat.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class TexSprite extends Sprite {
    // cache textures
    private static final HashMap<String, Texture> textures = new HashMap<>();
    private static final HashMap<String, Integer> textureUsage = new HashMap<>();
    public static Texture getCachedTexture(String path) {
        if (textures.containsKey(path)) {
            textureUsage.put(path, textureUsage.get(path) + 1);
            return textures.get(path);
        }
        Texture newTexture = new Texture(path);
        textures.put(path, newTexture);
        textureUsage.put(path, 1);
        return newTexture;
    }

    private final String texturePath;

    public TexSprite(String path) {
        super(getCachedTexture(path));
        texturePath = path;
    }

    public TexSprite setCenter() {
        super.setCenter(getWidth()/2, getHeight()/2);
        return this;
    }

    public TexSprite setPosition(Vector2 position) {
        super.setPosition(position.x, position.y);
        return this;
    }

    public void dispose() {
        if (!textureUsage.containsKey(texturePath)) return;
        int currentUsage = textureUsage.get(texturePath);
        if (currentUsage == 1) {
            textures.get(texturePath).dispose();
            textures.remove(texturePath);
            textureUsage.remove(texturePath);
            return;
        }
        textureUsage.put(texturePath, currentUsage - 1);
    }
}