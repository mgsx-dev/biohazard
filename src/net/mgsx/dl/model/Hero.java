package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class Hero extends Entity {

	private static Circle circle = new Circle();
	private static Circle circle2 = new Circle();
	
	Vector2 direction = new Vector2();
	private Vector2 target = new Vector2();
	
	boolean moving;
	public int combo;
	
	@Override
	public void update(World world, float deltaTime) 
	{
		circle.set(position, radius);
		circle2.set(world.home.position, world.home.radius);
//		if(Gdx.input.isTouched()){
//			if(circle.contains(world.cursor.position)){
//				direction.set(position).sub(world.home.position).nor();
//				moving = true;
//			}
//		}
		if(moving){
			position.mulAdd(direction, 1200 * deltaTime);
			if(position.x <= 0){
				position.x = 0;
				direction.x = -direction.x;
			}
			if(position.y <= 0){
				position.y = 0;
				direction.y = -direction.y;
			}
			if(position.x >= World.WIDTH){
				position.x = World.WIDTH;
				direction.x = -direction.x;
			}
			if(position.y >= World.HEIGHT){
				position.y = World.HEIGHT;
				direction.y = -direction.y;
			}
		}
		else{
			direction.set(world.home.position).sub(position);
			float len = direction.len();
			position.mulAdd(direction, 10 * deltaTime / len);
		}
		if(Intersector.overlaps(circle, circle2)){
			position.sub(world.home.position).setLength(world.home.radius + radius).add(world.home.position);
			moving = false;
			world.home.radius += combo * combo * 2;
			combo = 0;
		}
	}

	@Override
	public void render(ShapeRenderer renderer) {
		renderer.circle(position.x, position.y, radius);
	}

	
}
