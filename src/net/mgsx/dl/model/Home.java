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
		int maxRadius = 100;
		
		if(radius < minRadius){
			radius = minRadius;
			growing = true;
		}
		if(growing){
			radius += deltaTime * 260;
			if(radius >= maxRadius){
				radius = maxRadius;
				growing = false;
			}
		}else{
			if(radius < maxRadius){
				radius += deltaTime * 10;
			}
		}
	}
	
	@Override
	public void render(ShapeRenderer renderer) {
		renderer.circle(position.x, position.y, radius + 1);
	}

	
}
