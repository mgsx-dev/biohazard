package net.mgsx.dl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.dl.gfx.SceneGFX;
import net.mgsx.dl.model.World;

public class DLGameScreen extends ScreenAdapter
{
	public static final boolean DEBUG = true;
	
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	private Viewport viewport;
	private World world;
	private boolean pause;
	private SceneGFX gfx;
	private Batch batch;

	private BitmapFont font;
	
	
	public DLGameScreen() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(World.WIDTH, World.HEIGHT, camera);
		renderer = new ShapeRenderer();
		world = new World();
		gfx = new SceneGFX();
		font = new BitmapFont();
		batch = new SpriteBatch();
		

	}
	
	@Override
	public void render(float delta) {
		
		if(DEBUG && Gdx.input.isKeyJustPressed(Keys.SPACE)) pause = !pause;
		
		Ray ray = viewport.getPickRay(Gdx.input.getX(), Gdx.input.getY());
		if(!pause) world.cursor(ray.origin.x, ray.origin.y);
		
		if(!pause) world.update(delta);
		
		gfx.update(delta);
		
		gfx.begin();
		
		world.render(camera, renderer);
		
		
		float scale = 7;
		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.setProjectionMatrix(camera.combined);
		batch.setTransformMatrix(batch.getTransformMatrix().idt().scl(scale));
		batch.begin();
		
		if(world.combo > 0){
			font.setColor(World.homeColor);
			font.draw(batch, world.combo + "x", 4, 14);
		}
//		font.setColor(World.enemyColor);
//		font.draw(batch, world.enemies.size + "x", 65, 14);
		batch.end();

		gfx.end();
		
	}
	
	@Override
	public void resize(int width, int height) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.update(width, height, true);
	}
}
