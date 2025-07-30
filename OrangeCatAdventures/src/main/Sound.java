package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30]; // stores all the music file paths
	
	public Sound() {
		
		// SOUND SET
		soundURL[0] = getClass().getResource("/sound/BackgroundNoise_1.wav"); // background noise theme 
		soundURL[1] = getClass().getResource("/sound/Pickup_1.wav"); // pickup sound effect 
		soundURL[2] = getClass().getResource("/sound/Open_1.wav"); // open sound effect 
		soundURL[3] = getClass().getResource("/sound/PowerUp_1.wav"); // power up sound effect
		soundURL[4] = getClass().getResource("/sound/Hit_1.wav"); // hit sound effect
		soundURL[5] = getClass().getResource("/sound/Closed_1.wav"); // closed sound effect
		soundURL[6] = getClass().getResource("/sound/Select.wav"); // closed sound effect
		soundURL[7] = getClass().getResource("/sound/Hit_2.wav"); // attack monster
		soundURL[8] = getClass().getResource("/sound/SwingSword_1.wav"); // swing sword
	}
	
	public void setFile(int i) { // format for opening an audio file
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			
		}
	}
	
	public void play() { // method to play a sound
		
		clip.start();
	}
	
	public void loop() { // method to loop a sound
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() { // method to stop the sound
		
		clip.stop();
	}

}
