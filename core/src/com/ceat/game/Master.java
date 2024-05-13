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
	ModelBatch batch2;
	TexSprite sprite;
	OrthographicCamera cam;
	private static float elapsed;
	private static float gameSpeed = 1;
	PerspectiveCamera cam2;
	Model model;
	SimpleModelInstance instance;
	Model model2;
	SimpleModelInstance instance2;

	private Grid grid;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		batch2 = new ModelBatch();
		sprite = new TexSprite("img/badlogic.jpg");
		sprite.setSize(100, 100);
		cam = new OrthographicCamera(800, 600);
		cam2 = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		model = SimpleModelInstance.planeModel;
		instance = new SimpleModelInstance(model).setColor(1, 0, 0);
		instance.setScale(5, 5, 5);
//		instance.transform.scale(5, 5, 5);
		model2 = SimpleModelInstance.planeModel;
		instance2 = new SimpleModelInstance(model2).setColor(1, 1, 1);

		grid = new Grid();
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

	@Override
	public void render() {
		float delta = getDeltaTime();
		elapsed += delta;

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//		cam.position.set((float)Math.cos(elapsed)*50, (float)Math.sin(elapsed)*50, 0);
//		cam.update();
//		batch.setProjectionMatrix(cam.combined);

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			z -= delta*30;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			z += delta*30;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			x -= delta*30;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			x += delta*30;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			y += delta*30;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			y -= delta*30;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalRot = (horizontalRot - delta*180)%360;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalRot = (horizontalRot + delta*180)%360;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			verticalRot = Math.min(verticalRot + delta*180, 80);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			verticalRot = Math.max(verticalRot - delta*180, -80);
		}

		double polar = Math.toRadians(verticalRot);
		double alpha = Math.toRadians(horizontalRot);

		float xOff = camDist * (float)(Math.sin(alpha) * Math.cos(polar));
		float yOff = camDist * (float)Math.sin(polar);
		float zOff = camDist * (float)(Math.cos(alpha) * Math.cos(polar));

		float finalX = x + xOff;
		float finalY = y + yOff;
		float finalZ = z + zOff;

		instance2.setPosition(finalX*3, finalY*3, finalZ*3);
		grid.render(batch2);

		cam2.position.set(finalX, finalY, finalZ);
//		cam2.;
		cam2.lookAt(x, y, z);
//		cam2.position.set(20, 20, 20);
//		cam2.lookAt(0, 0, 0);
		cam.near = 1;
		cam.far = 300;
		cam2.update();

//		ScreenUtils.clear(0, 0, 0, 1);


//instance.transform.set(new Vector3(0, (float)Math.sin(elapsed)*5, 0), new Quaternion());
		instance.setPosition(0, (float)Math.sin(elapsed)*5, 0).setRotation(elapsed*90, elapsed*90, elapsed*90);
		instance2.setPosition(0, -(float)Math.sin(elapsed)*5, 0).setRotation(elapsed*90, elapsed*90, elapsed*90);
		batch2.begin(cam2);
		instance.render(batch2);
		instance2.render(batch2);
//		batch2.render(instance);
		batch2.end();

//		batch.begin();
//		sprite.draw(batch);
//		batch.end();
	}

	@Override
	public boolean keyDown(int keycode) {
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
