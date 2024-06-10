package com.ceat.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class CoolCamera {
    private static class Shake {
        public float strength;
        public float decayTime;
        public float lifetime;
        public Shake(float strength, float decayTime) {
            this.strength = strength;
            this.decayTime = decayTime;
        }
    }
    private int x;
    private int y;
    private float renderX;
    private float renderY;
    private float renderZ;
    private float xOff;
    private float yOff;
    private float zOff;
    private float horizontalRot;
    private float verticalRot;
    private final PerspectiveCamera cam;
    private final ArrayList<Shake> cameraShakes = new ArrayList<>();
    public CoolCamera() {
        cam = new PerspectiveCamera(67, 800, 600);
    }

    public void setFocusPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotateHorizontal(float angle) {
        horizontalRot = (horizontalRot + angle)%360;
    }
    public void rotateVertical(float angle) {
        verticalRot = Math.min(Math.max(verticalRot + angle, -80), 80);
    }

    public PerspectiveCamera getCamera() {
        return cam;
    }

    public Vector3 getRenderPosition() {
        return new Vector3(renderX + xOff, renderY + yOff, renderZ + zOff);
    }
    public Vector3 getFocusPosition() {
        return new Vector3(renderX, renderY, renderZ);
    }

    public void shake(float strength, float decayTime) {
        cameraShakes.add(new Shake(strength, decayTime));
    }

    public void render(float delta) {
        double polar = Math.toRadians(verticalRot);
        double alpha = Math.toRadians(horizontalRot +90);

//        float xOff = 10 * (float)(Math.sin(alpha) * Math.cos(polar));
//        float yOff = 10 * (float)Math.sin(polar);
//        float zOff = 10 * (float)(Math.cos(alpha) * Math.cos(polar));

        xOff = (float)Math.cos(Math.toRadians(horizontalRot))*20;
        yOff = 10;
        zOff = (float)Math.sin(Math.toRadians(horizontalRot))*20;

        float targetX = x*6;
        float targetY = 0;
        float targetZ = y*6;

        renderX = Lerp.lerp(renderX, targetX, Lerp.alpha(delta, 5));
        renderY = Lerp.lerp(renderY, targetY, Lerp.alpha(delta, 5));
        renderZ = Lerp.lerp(renderZ, targetZ, Lerp.alpha(delta, 5));

//        float finalX = renderX + xOff;
//        float finalY = renderY + yOff;
//        float finalZ = renderZ + zOff;
        float finalX = renderX + 35;
        float finalY = renderY + 25;
        float finalZ = renderZ + 35;

        float yOffset = 0;
        ArrayList<Shake> shakesToKill = new ArrayList<>();
        for (Shake shake: cameraShakes) {
            yOffset += (-shake.strength/2 + (float)Math.random()*shake.strength)*(1 - shake.lifetime/shake.decayTime);
            shake.lifetime += delta;
            if (shake.lifetime > shake.decayTime) {
                shakesToKill.add(shake);
            }
        }
        for (Shake shake: shakesToKill) {
            cameraShakes.remove(shake);
        }

//        System.out.println("" + finalX + ", " + finalY + ", " + finalZ);

        cam.position.set(finalX, finalY + yOffset, finalZ);
        cam.lookAt(renderX, renderY + yOffset, renderZ);
//        cam.lookAt(renderX, renderY, renderZ);
        cam.near = 1;
        cam.far = 300;
        cam.update();
    }
}
