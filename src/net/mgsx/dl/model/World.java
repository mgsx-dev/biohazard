package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class World {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	

	private Array<Entity> entities = new Array<Entity>();
	
	Entity home;
	Array<Hero> heroes = new Array<Hero>();
	Array<Enemy> enemies = new Array<Enemy>();
	Cursor cursor;
	
	private float enemyTimeout;
	
	public World() {
		home = new Home();
		home.position.set(WIDTH/2, HEIGHT/2);
		entities.add(home);
		
		float baseAngle = MathUtils.random(360f);
		int heroCount = 3;
		for(int i=0 ; i<heroCount ; i++){
			Hero hero = new Hero();
			hero.radius = 20;
			float angle = baseAngle + i * 360f / heroCount;
			hero.position.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).scl(home.radius + hero.radius).add(home.position);
			entities.add(hero);
			heroes.add(hero);
		}
		
		cursor = new Cursor();
		cursor.radius = 10;
		entities.add(cursor);
	}
	
	public void cursor(float x, float y) {
		cursor.position.set(x, y);
	}

	public void update(float delta) {
		enemyTimeout -= delta;
		if(enemyTimeout < 0){
			enemyTimeout += 1f;
			if(enemies.size < 100){ // XXX hard limit
				Enemy enemy = new Enemy();
				enemy.radius = 10;
				float angle = MathUtils.random(360f);
				enemy.position.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).scl(WIDTH + enemy.radius);
				enemies.add(enemy);
				entities.add(enemy);
			}
		}
		
		for(Entity entity : entities){
			entity.update(this, delta);
		}
		
		for(Enemy enemy : enemies){
			for(Hero hero : heroes){
				float dst = enemy.radius + hero.radius;
				if(enemy.position.dst2(hero.position) < dst*dst){
					enemy.alive = false;
				}
			}
		}
		
		for(int i=0 ; i<enemies.size ; ){
			if(enemies.get(i).alive){
				i++;
			}else{
				enemies.swap(i, enemies.size-1);
				enemies.pop();
			}
		}
		for(int i=0 ; i<entities.size ; ){
			if(entities.get(i).alive){
				i++;
			}else{
				entities.swap(i, entities.size-1);
				entities.pop();
			}
		}
	}
	
	public void render(ShapeRenderer renderer) {
		
		renderer.begin(ShapeType.Filled);
		
		renderer.setColor(Color.RED);
		home.render(renderer);
		
		renderer.setColor(Color.GRAY);
		for(Enemy enemy : enemies){
			enemy.render(renderer);
		}
		
		renderer.setColor(Color.ORANGE);
		for(Hero hero : heroes){
			hero.render(renderer);
		}
		
		cursor.render(renderer);
		
		renderer.end();
	}

}