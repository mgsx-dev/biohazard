package net.mgsx.dl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DLGame extends Game 
{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new DLGame(), config);
	}

	private DLGameScreen gameScreen;
	private DLMenuScreen menuScreen;
	private DLEndScreen endScreen;

	@Override
	public void create () {
		gameScreen = new DLGameScreen();
		menuScreen = new DLMenuScreen();
		endScreen = new DLEndScreen();
		
//		endScreen.set(36, 2343, "B");
//		setScreen(endScreen);
		
		setScreen(menuScreen);
	}

	public static DLGame game() {
		return (DLGame)Gdx.app.getApplicationListener();
	}

	public void startGame(int level) {
		gameScreen.set(level);
		setScreen(gameScreen);
	}

	public void showMenu() {
		setScreen(menuScreen);
	}

	public void endGame(int maxCombo, int enemiesKilled, String rank) {
		endScreen.set(maxCombo, enemiesKilled, rank);
		setScreen(endScreen);
	}
}
