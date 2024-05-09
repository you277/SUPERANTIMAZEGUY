package com.ceat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	float elapsed;
	float gameSpeed = 1;
	PerspectiveCamera cam2;
	Model model;
	SimpleModelInstance instance;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		batch2 = new ModelBatch();
		sprite = new TexSprite("img/badlogic.jpg");
		sprite.setSize(100, 100);
		cam = new OrthographicCamera(800, 600);
		cam2 = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		model = new ModelBuilder().createBox(5, 0, 5,
				new Material( ColorAttribute.createDiffuse(Color.WHITE) ), VertexAttributes.Usage.Position);
		instance = new SimpleModelInstance(model);
	}

	public float getDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}

	public float getScaledDeltaTime() {
		return getDeltaTime()*gameSpeed;
	}

	@Override
	public void render() {
		float delta = getScaledDeltaTime();
		elapsed += delta;

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//		cam.position.set((float)Math.cos(elapsed)*50, (float)Math.sin(elapsed)*50, 0);
//		cam.update();
//		batch.setProjectionMatrix(cam.combined);

		cam2.position.set(10, 10, 10);
		cam2.lookAt(0, 0, 0);
		cam.near = 1;
		cam.far = 300;
		cam2.update();

//		ScreenUtils.clear(0, 0, 0, 1);


//instance.transform.set(new Vector3(0, (float)Math.sin(elapsed)*5, 0), new Quaternion());
		instance.setPosition(0, (float)Math.sin(elapsed)*5, 0).setRotation(0, elapsed*360, 0);
		batch2.begin(cam2);
		instance.render(batch2);
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
