package net.mgsx.biohazard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.mgsx.biohazard.audio.BiohazardAudio;

public class BiohazardGame extends Game 
{
	private BiohazardGameScreen gameScreen;
	private BiohazardMenuScreen menuScreen;
	private BiohazardEndScreen endScreen;

	@Override
	public void create () {
		gameScreen = new BiohazardGameScreen();
		menuScreen = new BiohazardMenuScreen();
		endScreen = new BiohazardEndScreen();
		
//		endScreen.set(36, 2343, "B");
//		setScreen(endScreen);
		
		showMenu();
	}

	public static BiohazardGame game() {
		return (BiohazardGame)Gdx.app.getApplicationListener();
	}

	public void startGame(int level) {
		BiohazardAudio.i().playMusicGame();
		BiohazardAudio.i().playSoundStart();
		gameScreen.set(level);
		setScreen(gameScreen);
	}

	public void showMenu() {
		BiohazardAudio.i().playMusicMenu();
		setScreen(menuScreen);
	}

	public void endGame(int maxCombo, int enemiesKilled, String rank) {
		endScreen.set(maxCombo, enemiesKilled, rank);
		setScreen(endScreen);
	}
}
