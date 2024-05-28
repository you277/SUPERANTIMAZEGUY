package com.ceat.game;

import java.util.ArrayList;

public class Schedule {
    public static class Task {
        private float lifetime;
        private float timeAlive;
        public void run() {}
        public Task setLifetime(float lifetime) {
            this.lifetime = lifetime;
            return this;
        }
        public boolean incrementTimeAlive(float delta) {
            timeAlive += delta;
            return timeAlive >= lifetime;
        }
    }

    private static final ArrayList<Task> tasks = new ArrayList<>();
    public static void runTasks(float delta) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.incrementTimeAlive(delta)) {
                tasks.remove(i);
                i--;
                task.run();
            }
        }
    }

    private float currentTime;

    public Schedule wait(float delay) {
        currentTime += delay;
        return this;
    }

    public Schedule run(Task task) {
        tasks.add(task.setLifetime(currentTime));
        return this;
    }
}
