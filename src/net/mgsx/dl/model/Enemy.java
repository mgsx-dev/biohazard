package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	private Vector2 direction = new Vector2();
	public int energy;
	public float shieldTime;
	
	@Override
	public void update(World world, float deltaTime) {
		if(shieldTime > 0){
			shieldTime -= deltaTime;
		}
		direction.set(world.home.position).sub(position);
		float dst = direction.len();
		if(dst <= world.home.radius){
			// alive = false;
			radius /=2;
			world.home.radius -= 10 * energy;
			energy--;
			if(energy <= 0){
				alive = false;
				energy = 0;
			}
		}else{
			direction.scl(1f / dst);
			position.mulAdd(direction, .1f * dst * deltaTime);
		}
	}

	@Override
	public void render(ShapeRenderer renderer) {
		if(shieldTime <= 0 || shieldTime % .1f < .05f)
			renderer.circle(position.x, position.y, radius);
	}

}
