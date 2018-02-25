package net.mgsx.dl.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.dl.model.World;

public class SceneGFX {

	private FrameBuffer fboFlat, fboBlur;
	
	private ShaderProgram blurShader, finalShader;
	private Vector3 blurSize = new Vector3();
	
	private Batch batch;
	private float time;
	private Viewport viewport;
	
	public SceneGFX(int decimate) {
		
		int width = World.WIDTH / decimate;
		int height = World.HEIGHT / decimate;
		
		fboFlat = new FrameBuffer(Format.RGBA8888, width, height, false);
		fboBlur = new FrameBuffer(Format.RGBA8888, width, height, false);
		
		fboFlat.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fboBlur.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		batch = new SpriteBatch();
		
		blurShader = new ShaderProgram(Gdx.files.internal("blur.vs"), Gdx.files.internal("blur.fs"));
		finalShader = new ShaderProgram(Gdx.files.internal("final.vs"), Gdx.files.internal("final.fs"));
		
		viewport = new FitViewport(World.WIDTH, World.HEIGHT);
	}
	
	public void update(float delta){
		time += delta;
	}
	
	public void begin(){
		fboFlat.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void end(){
		fboFlat.end();
		int blurPasses = 3;
		batch.disableBlending();
		FrameBuffer fboBlurSrc = fboBlur;
		FrameBuffer fboBlurDst = fboFlat;
		for(int i=0 ; i<blurPasses ; i++){
			// swap
			FrameBuffer tmp = fboBlurSrc;
			fboBlurSrc = fboBlurDst;
			fboBlurDst = tmp;
			// blur
			fboBlurDst.begin();
			batch.setShader(blurShader);
			batch.begin();
			// TODO set params
			blurShader.setUniformf("u_size", blurSize.set(fboBlurSrc.getWidth(), fboBlurSrc.getHeight(), .5f));
			batch.draw(fboBlurSrc.getColorBufferTexture(), 0, 0, World.WIDTH, World.HEIGHT, 0, 0, 1, 1);
			batch.end();
			fboBlurDst.end();
		}
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();
		
		// TODO blur it
		// Gdx.gl.glClearColor(0, 0, 0, 0);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setShader(finalShader);
		batch.begin();
		finalShader.setUniformf("u_ceil", MathUtils.sin(time)*.0f+.11f);
		batch.draw(fboBlurDst.getColorBufferTexture(), 0, 0, World.WIDTH, World.HEIGHT, 0, 0, 1, 1);
		batch.end();
	}
}
