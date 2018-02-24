package net.mgsx.dl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.dl.model.World;

public class DLGameScreen extends ScreenAdapter
{
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	private Viewport viewport;
	private World world;
	
	public DLGameScreen() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(World.WIDTH, World.HEIGHT, camera);
		renderer = new ShapeRenderer();
		world = new World();
	}
	
	@Override
	public void render(float delta) {
		
		Ray ray = viewport.getPickRay(Gdx.input.getX(), Gdx.input.getY());
		world.cursor(ray.origin.x, ray.origin.y);
		
		world.update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setProjectionMatrix(camera.combined);
		world.render(renderer);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
}
