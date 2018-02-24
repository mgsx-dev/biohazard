package net.mgsx.dl.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class World {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	public static final Color homeColor = new Color(1, 0, 0, 1);
	public static final Color ballsColor = new Color(1, 0.3f, 0, 1);
	public static final Color rayColor = new Color(0, 1, 0, 1);
	public static final Color enemyColor = new Color(0, 0, 1, 1);

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
	
	private Enemy createEnemy(int energy){
		Enemy enemy = new Enemy();
		enemy.energy = energy;
		enemy.radius = 10 * (enemy.energy + MathUtils.random(.5f));
		enemies.add(enemy);
		entities.add(enemy);
		return enemy;
	}

	public void update(float delta) {
		enemyTimeout -= delta;
		if(enemyTimeout < 0){
			enemyTimeout += 1f;
			if(enemies.size < 100){ // XXX hard limit
				Enemy enemy = createEnemy(MathUtils.random(1, 3));
				float angle = MathUtils.random(360f);
				enemy.position.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).scl(WIDTH + enemy.radius);
			}
		}
		
		for(Entity entity : entities){
			entity.update(this, delta);
		}
		
		for(int i=0 ; i<enemies.size ; ){
			Enemy enemy = enemies.get(i);
			if(enemy.shieldTime <= 0)
			{
				for(Hero hero : heroes){
					float dst = enemy.radius + hero.radius;
					if(enemy.position.dst2(hero.position) < dst*dst){
						enemy.energy--;
						enemy.alive = false;
						if(enemy.energy > 1){
							for(int j=0 ; j<2 ; j++){
								Enemy child = createEnemy(enemy.energy-1);
								child.position.set(enemy.position).add(MathUtils.random(enemy.radius * 4), MathUtils.random(enemy.radius * 4));
								child.shieldTime = 1;
							}
						}
					}
				}
			}
			if(enemy.alive){
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
		
//		Gdx.gl.glEnable(GL20.GL_BLEND);
//		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		renderer.begin(ShapeType.Filled);
		
		renderer.setColor(homeColor);
		home.render(renderer);
		
		
		renderer.setColor(enemyColor);
		for(Enemy enemy : enemies){
			enemy.render(renderer);
		}
		
		renderer.setColor(ballsColor);
		for(Hero hero : heroes){
			hero.render(renderer);
		}
		
		renderer.setColor(rayColor);
		cursor.render(renderer);
		
		
		renderer.end();
	}

}
