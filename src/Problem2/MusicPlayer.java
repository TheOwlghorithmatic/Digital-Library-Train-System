package Problem2;

import javax.sound.sampled.*;
import java.io.*;

public class MusicPlayer {
    private Clip clip;
    private boolean isMuted = false;
    private boolean isAvailable = false;
    private float volume = 0.5f;

    public MusicPlayer(String filePath) {
        try {
            File musicFile = new File(filePath);
            
            if (!musicFile.exists()) {
                System.out.println("⚠️ bgm.wav not found. Running without music.");
                return;
            }
            
            // Get raw audio input stream
            AudioInputStream originalStream = AudioSystem.getAudioInputStream(musicFile);
            AudioFormat originalFormat = originalStream.getFormat();
            
            // Convert to PCM_SIGNED if it's not already
            AudioFormat pcmFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                originalFormat.getSampleRate(),
                16,
                originalFormat.getChannels(),
                originalFormat.getChannels() * 2,
                originalFormat.getSampleRate(),
                false
            );
            
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(pcmFormat, originalStream);
            
            // Read ALL bytes into a buffer (avoids the "Audio data < 0" bullshit)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = convertedStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] audioData = baos.toByteArray();
            
            // Create clip and open with raw data
            clip = AudioSystem.getClip();
            clip.open(pcmFormat, audioData, 0, audioData.length);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            setVolume(volume);
            isAvailable = true;
            
            // Clean up
            convertedStream.close();
            originalStream.close();
            baos.close();
            
            System.out.println("🎵 Background music loaded successfully!");
            
        } catch (Exception e) {
            System.out.println("⚠️ Could not load music: " + e.getMessage());
            System.out.println("   Running without background music.");
            e.printStackTrace();
        }
    }

    public void play() {
        if (!isAvailable || clip == null) return;
        if (!clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMuted = false;
        }
    }

    public void stop() {
        if (!isAvailable || clip == null) return;
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    public void toggleMute() {
        if (!isAvailable || clip == null) return;
        if (isMuted) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMuted = false;
        } else {
            clip.stop();
            isMuted = true;
        }
    }

    public boolean isMuted() {
        return isMuted;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }

    private void setVolume(float volume) {
        if (clip == null) return;
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log10(Math.max(volume, 0.0001)) * 20.0);
            gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(dB, gainControl.getMaximum())));
        } catch (Exception e) {
            // volume control not supported, no biggie
        }
    }
}