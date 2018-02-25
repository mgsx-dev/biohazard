package net.mgsx.dl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.mgsx.dl.gfx.SceneGFX;
import net.mgsx.dl.model.World;
import net.mgsx.dl.utils.StageScreen;

public class DLEndScreen extends StageScreen
{
	private ShapeRenderer renderer;
	private OrthographicCamera camera;
	private SceneGFX gfx;
	private Batch batch;

	private BitmapFont font;
	private BitmapFontCache cache;
	
	private GlyphLayout title;
	private TextButton comboLabel;
	private TextButton killedLabel;
	private TextButton rankLabel;
	
	private Array<TextButton> buttons = new Array<TextButton>();
	private Table main;
	
	
	public DLEndScreen() {
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
		
		main = new Table();
		main.defaults().padBottom(20);
		
		main.add(createLabel("Game Over", World.homeColor, 6)).row();
		root.add(main).expand().fill();
		
		main.add(comboLabel = createPlayButton("Combo 45x", 1)).row();
		main.add(killedLabel = createPlayButton("Killed 4567x", 1)).row();
		main.add(rankLabel = createPlayButton("Ranking A", 1)).row();
		
		buttons.add(comboLabel);
		buttons.add(killedLabel);
		buttons.add(rankLabel);
		
	}
	
	private Actor createLabel(String text, Color color, float scale){
		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = new Color(color);
		Label label = new Label(text, style);
		label.setFontScale(scale);
		return label;
	}
	
	@Override
	public void show() {
		super.show();
		float delay = 0;
		for(TextButton button : buttons){
			button.clearActions();
			button.setScale(8);
			button.validate();
			button.setOrigin(comboLabel.getPrefWidth()/2, -60);
			button.addAction(Actions.sequence(Actions.delay(delay), Actions.scaleTo(1, 1, 1f, Interpolation.pow2In)));
			delay += 1f;
		}
		main.clearActions();
		main.addAction(Actions.delay(delay + 3));
	}
	
	private TextButton createPlayButton(String text, final int level){
		
//		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
//		pixmap.setColor(Color.WHITE);
//		pixmap.drawPixel(0, 0);
//		Texture texture = new Texture(pixmap);
//		TextureRegionDrawable up = new TextureRegionDrawable(new TextureRegion(texture));
//		up.setLeftWidth(10);
//		up.setRightWidth(10);
		
		TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		style.fontColor = new Color(World.enemyColor);
//		style.up = up.tint(new Color(1,1,0,.3f));
//		style.down = up.tint(new Color(0,1,1,.3f));
		TextButton bt = new TextButton("", style);
		bt.setTransform(true);
		// bt.add(new Image(up.tint(World.rayColor))).size(30);
		bt.add(createLabel(text, World.enemyColor, 4));
		
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
		
		if(!main.hasActions()){
			DLGame.game().showMenu();
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void set(int maxCombo, int enemiesKilled, String rank) {
		((Label)comboLabel.getChildren().peek()).setText("Max Combo " + maxCombo + "x");
		((Label)killedLabel.getChildren().peek()).setText("Killed " + enemiesKilled + "x");
		((Label)rankLabel.getChildren().peek()).setText("Rank " + rank);
	}
}
