package com.ceat.game;

public class Lerp {
    public static float lerp(float v1, float v2, float a) {
        return v1 + (v2 - v1)*a;
    }

    public static float alpha(float deltaTime, float speed) {
        return (float)(1.0 - Math.pow(1/speed, speed*deltaTime));
    }
}
