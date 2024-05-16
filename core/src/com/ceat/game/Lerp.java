package com.ceat.game;

import com.badlogic.gdx.math.Vector3;

public class Lerp {
    public static float lerp(float v1, float v2, float a) {
        return v1 + (v2 - v1)*a;
    }

    public static float alpha(float deltaTime, float speed) {
        return (float)(1.0 - Math.pow(1/speed, speed*deltaTime));
    }

    public static Vector3 threePointBezier(Vector3 v1, Vector3 v2, Vector3 v3, float a) {
        float x1 = lerp(v1.x, v2.x, a);
        float y1 = lerp(v1.y, v2.y, a);
        float z1 = lerp(v1.z, v2.z, a);
        float x2 = lerp(v2.x, v3.x, a);
        float y2 = lerp(v2.y, v3.y, a);
        float z2 = lerp(v2.z, v3.z, a);
        return new Vector3(lerp(x1, x2, a), lerp(y1, y2, a), lerp(z1, z2, a));
    }
}
