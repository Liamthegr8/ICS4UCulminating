import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

//Partially AI generated class
public class Sound {
    private Clip clip;
    private long pauseTime = 0;

    // 1. LOAD THE SOURCE
    public boolean load(String path) {
        try {
            AudioInputStream stream;
            
            URL resource = getClass().getResource(path);
            if (resource != null) {
                stream = AudioSystem.getAudioInputStream(resource);
            } else {
                // If not found, try loading as an external system file
                stream = AudioSystem.getAudioInputStream(new File(path));
            }
            
            clip = AudioSystem.getClip();
            clip.open(stream);
            return true;
        } catch (Exception e) {
            System.err.println("Error loading sound file: " + path);
            e.printStackTrace();
            return false;
        }
    }

    // 2. PLAY / RESUME
    public void play() {
        if (clip == null) return;
        
        // If it was paused, resume from that frame. Otherwise, start from beginning.
        clip.setMicrosecondPosition(pauseTime);
        clip.start();
    }

    // 3. PAUSE
    public void pause() {
        if (clip == null || !clip.isRunning()) return;
        
        pauseTime = clip.getMicrosecondPosition(); // Save current time
        clip.stop();
    }

    // 4. STOP (Reset completely)
    public void stop() {
        if (clip == null) return;
        
        clip.stop();
        pauseTime = 0;
        clip.setFramePosition(0);
    }

    // 5. LOOP
    public void loop() {
        if (clip == null) return;
        
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}