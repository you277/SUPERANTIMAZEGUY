package com.ceat.game.fx;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Lerp;
import com.ceat.game.SimpleModelInstance;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class ModelParticles {
    private static class Particle {
        private final float speed;
        private final float rotSpeed;
        private final float lifetime;
        private final float startScale;
        private final float endScale;
        private final Vector3 direction;
        private Vector3 position;
        private final SimpleModelInstance model;
        private float existenceTime;
        public Particle(Model baseModel, Vector3 origin, float speed, float rotSpeed, float lifetime, Vector3 direction, Vector3 spread, float startScale, float endScale, Color color) {
            this.speed = speed;
            this.rotSpeed = rotSpeed;
            this.lifetime = lifetime;
            this.startScale = startScale;
            this.endScale = endScale;
            position = origin;
            Matrix4 rotation = new Matrix4();
            rotation
                    .rotateRad(new Vector3(1, 0, 0), direction.x - spread.x + (float)Math.random()*spread.x*2)
                    .rotateRad(new Vector3(0, 1, 0), direction.y - spread.y + (float)Math.random()*spread.y*2)
                    .rotateRad(new Vector3(0, 0, 1), direction.z - spread.z + (float)Math.random()*spread.z*2);
            Quaternion rot = rotation.getRotation(new Quaternion());
            float yaw = rot.getYawRad();
            float pitch = rot.getPitchRad();
            float x = (float)(Math.sin(yaw)*Math.cos(pitch));
            float y = (float)(Math.sin(yaw)*Math.sin(pitch));
            float z = (float)(Math.cos(yaw));
            this.direction = new Vector3(x, y, z);
            model = new SimpleModelInstance(baseModel).setScale(startScale).setColor(color);
        }

        public boolean step(float delta) {
            existenceTime += delta;
            if (existenceTime > lifetime) {
                return false;
            }
            position = new Vector3(
                    position.x + direction.x*speed*delta,
                    position.y + direction.y*speed*delta,
                    position.z + direction.z*speed*delta
            );
            Vector3 currRotation = model.getRotation();
            float scale = Lerp.lerp(startScale, endScale, existenceTime/lifetime);
            model.setPosition(position).setRotation(new Vector3(
                    currRotation.x + rotSpeed*delta,
                    currRotation.y + rotSpeed*delta,
                    currRotation.z + rotSpeed*delta
            )).setScale(scale);
            return true;
        }
        public void draw(ModelBatch batch) {
            model.render(batch);
        }
        public void dispose() {
            model.dispose();
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
    private float rate;
    private Color color;
    private Vector3 origin = new Vector3();
    private Vector3 direction = new Vector3(0, 1, 0);
    private ArrayList<Particle> particles = new ArrayList<>();
    private final Model baseModel;

    public ModelParticles(Model model) {
        baseModel = model;
    }
    public ModelParticles setOrigin(Vector3 origin) {
        this.origin = origin;
        return this;
    }
    public ModelParticles setOrigin(float x, float y, float z) {
        setOrigin(new Vector3(x, y, z));
        return this;
    }
    public ModelParticles setSpread(float spreadX, float spreadY) {
        this.spreadX = spreadX;
        this.spreadY = spreadY;
        return this;
    }
    public ModelParticles setSpeed(float min, float max) {
        minSpeed = min;
        maxSpeed = max;
        return this;
    }
    public ModelParticles setSpeed(float speed) {
        setSpeed(speed, speed);
        return this;
    }
    public ModelParticles setLifetime(float min, float max) {
        minLife = min;
        maxLife = max;
        return this;
    }
    public ModelParticles setLifetime(float life) {
        setLifetime(life, life);
        return this;
    }
    public ModelParticles setRotSpeed(float min, float max) {
        minRotSpeed = min;
        maxRotSpeed = max;
        return this;
    }
    public ModelParticles setRotSpeed(float speed) {
        setRotSpeed(speed, speed);
        return this;
    }
    public ModelParticles setScale(float begin, float end) {
        startScale = begin;
        endScale = end;
        return this;
    }
    public ModelParticles setScale(float scale) {
        setScale(scale, scale);
        return this;
    }
    public ModelParticles setColor(float r, float g, float b) {
        color = new Color(r, g, b, 1);
        return this;
    }
    public ModelParticles setColor(Color color) {
        this.color = color;
        return this;
    }
    public ModelParticles setRate(float rate) {
        this.rate = rate;
        return this;
    }
    public ModelParticles setDirection(Vector3 direction) {
        this.direction = direction;
        return this;
    }

    public void emit(int amt) {
        for (int i = 0; i < amt; i++) {
            float speed = minSpeed + (float)Math.random()*(maxSpeed - minSpeed);
            float rotSpeed = minRotSpeed + (float)Math.random()*(maxRotSpeed - minRotSpeed);
            float lifetime = minLife + (float)Math.random()*(maxLife - minLife);
            particles.add(new Particle(baseModel, origin, speed, rotSpeed, lifetime, direction, new Vector3(spreadX, spreadY, 0), startScale, endScale, color));
        }
    }
    public void emit() {
        emit(1);
    }

    private float lastEmitTime;
    private float existenceTime;
    public boolean step(float delta) {
        float now = existenceTime + delta;
        existenceTime = now;
        int emitAmt = (int)((now - lastEmitTime)/rate);
        if (emitAmt > 0) {
            lastEmitTime = now;
            emit(emitAmt);
        }
        ArrayList<Particle> toRemove = new ArrayList<>();
        for (Particle particle: particles) {
            if (!particle.step(delta)) {
                toRemove.add(particle);
            }
        }
        for (Particle particle: toRemove) {
            particles.remove(particle);
        }
        return particles.size() > 0;
    }

    public void draw(ModelBatch batch) {
        for (Particle particle: particles) {
            particle.draw(batch);
        }
    }

    public void dispose() {
        for (Particle particle: particles) {
            particle.dispose();
        }
    }
}
