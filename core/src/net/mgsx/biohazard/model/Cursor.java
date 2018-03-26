package net.mgsx.biohazard.model;

import com.badlogic.gdx.Gdx;
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
			//hero.direction.set(position.x, position.y).sub(hero.position.x, -hero.position.y);
			hero.direction.set(position).sub(hero.position);
			if(new Vector2(world.home.position).sub(hero.position).dot(hero.direction) > 0) {
			hero.direction.x *= -1;
			hero.direction.y *= -1;
			}
			float len = hero.direction.len();
			if(Math.abs(len) > 1){
				hero.position.mulAdd(hero.direction.scl(1f / len),20);
				hero.speed = len/300+0.25f;
				hero.moving = true;
				hero.radius = 20;
				hero = null;
			}else{
				hero.direction.set(1,0);
				hero = null;
			}
		}
		
	}

	@Override
	public void render(ShapeRenderer renderer) {
		// renderer.circle(position.x, position.y, radius);
		
		if(hero != null){
			// draw ray
			int maxRays = 7;
			a.set(hero.position.x, hero.position.y);
			b.set(position.x,position.y);
			dir.set(b).sub(a);
			dir.nor();
			if(new Vector2(world.home.position).sub(hero.position).dot(dir) > 0) {
			dir.x *=-1;
			dir.y *=-1;
			}
			float minx = hero.radius;
			float miny = hero.radius;
			float maxx = World.WIDTH - hero.radius;
			float maxy = World.HEIGHT - hero.radius;
			
			float rad = hero.radius/4;
			
			for(int i=0 ; i<maxRays ; i++){
				
				// rad *= .5f;
				
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
					}
				}
				
				
				renderer.rectLine(a, b, rad * 2);
				renderer.circle(b.x, b.y, rad);
				a.set(b);
				if(dot < doty) dir.x = -dir.x;
				if(dot < dotx) dir.y = -dir.y;
				
				// renderer.getColor().g *= 0.7f;
				
			}
			
		}
	}

}
