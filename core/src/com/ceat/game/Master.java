package com.ceat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Master extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	ModelBatch modelBatch;
	private static float elapsed;
	private static float gameSpeed = 1;
	private Game game;
	
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		modelBatch = new ModelBatch();
		game = new Game();
	}

	public static float getRawDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}

	public static float getDeltaTime() {
		return getRawDeltaTime()*gameSpeed;
	}
	float horizontalRot;
	float verticalRot;
	float x;
	float y;
	float z;
	float camDist = 10;

	private void rotateCameraHorizontal(float rot) {
		game.getCamera().rotateHorizontal(rot);
	}
	private void rotateCameraVertical(float rot) {
		game.getCamera().rotateVertical(rot);
	}

	@Override
	public void render() {
		float delta = getDeltaTime();
		elapsed += delta;

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			rotateCameraHorizontal(-delta*180);
			horizontalRot = (horizontalRot - delta*180)%360;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			rotateCameraHorizontal(delta*180);
			horizontalRot = (horizontalRot + delta*180)%360;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			rotateCameraVertical(-delta*180);
			verticalRot = Math.max(verticalRot - delta*180, -80);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			rotateCameraVertical(delta*180);
			verticalRot = Math.min(verticalRot + delta*180, 80);
		}

		Schedule.runTasks(delta);
		Loop.runLoops(delta);

		modelBatch.begin(game.getCamera().getCamera());
		game.render(batch, modelBatch);
		modelBatch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
		game.keydown(keycode);
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
