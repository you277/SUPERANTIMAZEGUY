package com.ceat.game.fx;

import com.ceat.game.SimpleModelInstance;

import java.awt.*;
import java.util.ArrayList;

public class CubeParticles {
    private static class Particle {
        private float speed;
        private float rotSpeed;
        private float lifetime;
        private float startScale;
        private float endScale;
        private SimpleModelInstance model;
        public Particle(float speed, float rotSpeed, float lifetime, float startScale, float endScale, float color) {
            this.speed = speed;
            this.rotSpeed = rotSpeed;
            this.lifetime = lifetime;
            this.startScale = startScale;
            this.endScale = endScale;
            model = new SimpleModelInstance(SimpleModelInstance.cubeModel).setScale(startScale).setScale()
        }
    }
    private float spreadX;
    private float spreadY;
    private float minSpeed;
    private float maxSpeed;
    private float minLife;
    private float maxLife;
    private float minRotSpeed;
    private float maxRotSpeed;
    private float startScale;
    private float endScale;
    private Color color;
    private ArrayList
}
