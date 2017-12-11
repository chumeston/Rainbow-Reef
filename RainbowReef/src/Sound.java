import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    private URL url;
    private Clip clip;
    private AudioInputStream audio;

    Sound() {}

    public void playSound(String name) {
        url = Game.class.getResource(name);
        try {   audio = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) { }
    }
}