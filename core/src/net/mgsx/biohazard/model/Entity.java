package net.mgsx.biohazard.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

abstract public class Entity {
	public Vector2 position = new Vector2();
	public float radius;
	public boolean alive = true;
	
	abstract public void update(World world, float deltaTime);
	abstract public void render(ShapeRenderer renderer);
}
