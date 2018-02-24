package net.mgsx.dl.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Cursor extends Entity{

	private static Circle circle = new Circle();

	public Hero hero;
	
	private Vector2 a = new Vector2(), b = new Vector2(), dir = new Vector2(), dif = new Vector2();

	private World world; // XXX
	
	@Override
	public void update(World world, float deltaTime) {
		this.world = world;
		if(Gdx.input.isTouched()){
			if(hero == null){
				for(Hero hero : world.heroes){
					circle.set(hero.position, hero.radius);
					if(circle.contains(position)){
						this.hero = hero;
						hero.radius = 20;
						break;
					}
				}
			}
		}
		else if(hero != null){
			hero.direction.set(position).sub(hero.position).nor();
			hero.moving = true;
			hero.radius = 20;
			hero = null;
		}
		
	}

	@Override
	public void render(ShapeRenderer renderer) {
		renderer.circle(position.x, position.y, radius);
		if(hero != null){
			// draw ray
			int maxRays = 7;
			a.set(hero.position);
			b.set(position);
			dir.set(b).sub(a);
			dir.nor();
			float minx = hero.radius;
			float miny = hero.radius;
			float maxx = World.WIDTH - hero.radius;
			float maxy = World.HEIGHT - hero.radius;
			
			for(int i=0 ; i<maxRays ; i++){
				float dotx, doty;
				
				if(dir.x > 0){
					dotx = (maxx - a.x) / dir.x;
				}else if(dir.x < 0){
					dotx = (minx-a.x) / dir.x;
				}else{
					dotx = 0;
				}
				if(dir.y > 0){
					doty = (maxy - a.y) / dir.y;
				}else if(dir.y < 0){
					doty = (miny-a.y) / dir.y;
				}else{
					doty = 0;
				}
				float dot = Math.abs(dotx) < Math.abs(doty) ? dotx : doty;
				b.set(a).mulAdd(dir, dot);
				
				// clamp to circle
				dif.set(world.home.position).sub(a); 
				float dotc = dif.dot(dir);
				if(dotc > 0){
					float dotd = dif.dot(dir.y, -dir.x);
					float u = Math.abs(dotd);
					float v = hero.radius + world.home.radius;
					if(u < v){
						float w = (float)Math.sqrt(v*v-u*u);
						b.set(a).mulAdd(dir, dotc - w);
						i = maxRays;
						renderer.setColor(Color.GREEN);
					}
				}
				
				
				renderer.rectLine(a, b, hero.radius * 2);
				renderer.circle(b.x, b.y, hero.radius);
				a.set(b);
				if(dot < doty) dir.x = -dir.x;
				if(dot < dotx) dir.y = -dir.y;
				
				
			}
			
		}
	}

}
