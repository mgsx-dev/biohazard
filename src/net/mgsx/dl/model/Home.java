package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Home extends Entity
{
	boolean growing;
	
	public Home() {
		radius = 30;
		growing = true;
	}
	
	@Override
	public void update(World world, float deltaTime) {
		int minRadius = 30;
		int initRadius = 100;
		int maxRadius = 200;
		
		if(radius > maxRadius){
			radius = maxRadius;
		}
		if(radius < minRadius){
			world.gameOver();
			radius = minRadius;
			growing = true;
		}
		if(growing){
			radius += deltaTime * 260;
			if(radius >= initRadius){
				radius = initRadius;
				growing = false;
			}
		}else{
			if(radius < initRadius){
				radius += deltaTime * 10;
			}
		}
	}
	
	@Override
	public void render(ShapeRenderer renderer) {
		renderer.circle(position.x, position.y, radius + 1);
	}

	
}
