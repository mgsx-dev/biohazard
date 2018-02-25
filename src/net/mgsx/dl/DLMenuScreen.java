package net.mgsx.dl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.dl.gfx.SceneGFX;
import net.mgsx.dl.model.World;
import net.mgsx.dl.utils.StageScreen;

public class DLMenuScreen extends StageScreen
{
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	private SceneGFX gfx;
	private Batch batch;

	private BitmapFont font;
	private BitmapFontCache cache;
	
	private GlyphLayout title;
	
	
	
	public DLMenuScreen() {
		super(new FitViewport(World.WIDTH, World.HEIGHT));
		camera = new OrthographicCamera();
		viewport.setCamera(camera);
		renderer = new ShapeRenderer();
		gfx = new SceneGFX(4);
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);
		
		Table main = new Table();
		main.defaults().padBottom(20);
		
		main.add(createLabel("Three Blobs", World.homeColor, 6)).row();
		root.add(main).expand().fill();
		
		main.add(createPlayButton("Easy", 1)).row();
		main.add(createPlayButton("Normal", 1)).row();
		main.add(createPlayButton("Hard", 1)).row();
		
	}
	
	private Actor createLabel(String text, Color color, float scale){
		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = new Color(color);
		Label label = new Label(text, style);
		label.setFontScale(scale);
		return label;
	}
	
	private TextButton createPlayButton(String text, final int level){
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		Texture texture = new Texture(pixmap);
		TextureRegionDrawable up = new TextureRegionDrawable(new TextureRegion(texture));
		up.setLeftWidth(10);
		up.setRightWidth(10);
		
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		//style.fontColor = new Color(World.enemyColor);
		style.up = up.tint(new Color(1,1,0,.3f));
		style.down = up.tint(new Color(0,1,1,.3f));
		TextButton bt = new TextButton("", style);
		bt.setTransform(true);
		// bt.add(new Image(up.tint(World.rayColor))).size(30);
		bt.add(createLabel(text, World.homeColor, 4));
		
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				DLGame.game().startGame(level);
			}
		});
		
		return bt;
	}
	
	@Override
	public void render(float delta) {
		
		Ray ray = viewport.getPickRay(Gdx.input.getX(), Gdx.input.getY());
		// (ray.origin.x, ray.origin.y);
		
		gfx.update(delta);
		
		gfx.begin();
		
		
		float scale = 7;
		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.setProjectionMatrix(camera.combined);
		batch.setTransformMatrix(batch.getTransformMatrix().idt().scl(scale));
		batch.begin();
		
//		font.setColor(World.homeColor);
//		font.draw(batch, "Three Blobs", 4f, 14f);
		
		
		batch.end();

		super.render(delta);
		
		gfx.end();
		
		
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
