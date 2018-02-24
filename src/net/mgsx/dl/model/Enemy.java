package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	private Vector2 direction = new Vector2();
	
	@Override
	public void update(World world, float deltaTime) {
		direction.set(world.home.position).sub(position);
		float dst = direction.len();
		if(dst <= world.home.radius){
			alive = false;
			world.home.radius -= 10;
		}else{
			direction.scl(1f / dst);
			position.mulAdd(direction, .1f * dst * deltaTime);
		}
	}

	@Override
	public void render(ShapeRenderer renderer) {
		renderer.circle(position.x, position.y, radius);
	}

}
