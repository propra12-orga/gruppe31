package gruppe31.bomberman;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * 
 * @author gruppe 31-Bomberman
 *
 */
public class AudioEffects {

	private URL bgmUrl;
	private URL bombExplosionUrl;
	public static Clip bgmClip;
	
	public AudioEffects() {
		bgmUrl = this.getClass().getClassLoader().getResource("bomberman-bgm.wav");
		bombExplosionUrl = this.getClass().getClassLoader().getResource("bomb.wav");
	}
	/**
	 * Audio wird gespielt
	 */
	public void playBgm() {
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(bgmUrl);
			bgmClip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioIn.getFormat()));
			//Clip clip = AudioSystem.getClip();
			bgmClip.open(audioIn);
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
			bgmClip.start();
			
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
    /**
     * Audio wird gestoppt
     */
	public void pauseBgm() {
		bgmClip.stop();
	}
    /**
     * überprüft ob das Audio spielt
     * @return true wenn bgmClip ist nicht null und bgmClip läuft,sonst false
     */
	public boolean isAudioOn() {
		if (bgmClip != null && bgmClip.isRunning()) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 */
	public void playBombExplosion() {
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(bombExplosionUrl);
			Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, audioIn.getFormat()));
			//Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
}
