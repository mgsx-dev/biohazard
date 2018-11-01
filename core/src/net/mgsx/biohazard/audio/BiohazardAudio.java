package net.mgsx.biohazard.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class BiohazardAudio {
	private static BiohazardAudio i;
	public static BiohazardAudio i(){
		return i== null ? i = new BiohazardAudio() : i;
	}
	
	private Music menuMusic, gameMusic, endMusic, currentMusic;
	private Sound sndStart, sndSmaller, sndBigger, sndX, sndY, sndZ;
	
	public BiohazardAudio() {
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Biohazard menu.ogg"));
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Biohazard ingame.ogg"));
		endMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Biohazard gameover.ogg"));
		
		menuMusic.setLooping(true);
		gameMusic.setLooping(true);
		
		sndStart = Gdx.audio.newSound(Gdx.files.internal("sfx/Biohazard fx1 Start.ogg"));
		sndSmaller = Gdx.audio.newSound(Gdx.files.internal("sfx/Biohazard fx2 Retreci.ogg"));
		sndBigger = Gdx.audio.newSound(Gdx.files.internal("sfx/Biohazard fx4 grossi.ogg"));
		sndX = Gdx.audio.newSound(Gdx.files.internal("sfx/Biohazard fx5.ogg"));
		sndY = Gdx.audio.newSound(Gdx.files.internal("sfx/Biohazard fx6.ogg"));
		sndZ = Gdx.audio.newSound(Gdx.files.internal("sfx/Biohazard fx7.ogg"));
	}
	private void changeMusic(Music music){
		if(currentMusic != null){
			currentMusic.stop();
		}
		currentMusic = music;
		currentMusic.play();
	}
	public void playMusicMenu(){
		changeMusic(menuMusic);
	}
	public void playMusicGame(){
		changeMusic(gameMusic);
	}
	public void playMusicEnd(){
		changeMusic(endMusic);
	}
	public void playSoundStart() {
		sndStart.play();
	}
	public void playSoundSmaller() {
		sndSmaller.play(.5f, MathUtils.random(.8f, 1.3f), 0);
	}
	public void playSoundBigger() {
		sndBigger.play();
	}
	public void playSoundKill() {
		sndY.play(.1f, MathUtils.random(8f, 16f), 0);
	}
	public void playSoundStats() {
		sndZ.play();
	}

}
