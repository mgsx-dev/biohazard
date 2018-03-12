package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Home extends Entity
{
	boolean growing;
	
	public float targetRadius;
	
	public Home() {
		targetRadius = 100;
		radius = 0;
		growing = true;
	}
	
	@Override
	public void update(World world, float deltaTime) {
		int minRadius = 30;
		int initRadius = 100;
		int maxRadius = 200;
		
		if(growing){
			if(radius >= initRadius){
				radius = initRadius;
				growing = false;
			}
		}else{
			if(radius < initRadius){
				targetRadius += deltaTime * 0; // no recovery
			}
			if(targetRadius > maxRadius){
				targetRadius = maxRadius;
			}
			if(radius < minRadius){
				world.gameOver();
				targetRadius = minRadius;
			}
		}
		radius = MathUtils.lerp(radius, targetRadius, deltaTime);
	}
	
	@Override
	public void render(ShapeRenderer renderer) {
		renderer.circle(position.x, position.y, radius + 1);
	}

	
}
