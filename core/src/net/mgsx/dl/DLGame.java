package net.mgsx.dl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class DLGame extends Game 
{
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
