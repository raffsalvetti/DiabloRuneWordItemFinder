package dev.raffsalvetti.diruwfi.component;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URI;

public class AudioPlayerComponent {
    private AudioInputStream audio;
    private Clip clip;
    public AudioPlayerComponent() {
    }

    public AudioPlayerComponent(AudioInputStream audio) {
        this();
        this.audio = audio;
        try {
            this.clip = AudioSystem.getClip();
            this.clip.open(audio);
        } catch (Exception e) {
            System.out.printf("Erro carregando clip: %s\n", e.getMessage());
        }
    }

    public AudioPlayerComponent(InputStream audio) {
        this();
        try {
            this.audio = AudioSystem.getAudioInputStream(audio);
            this.clip = AudioSystem.getClip();
            this.clip.open(this.audio);
        } catch (Exception e) {
            System.out.printf("Erro carregando audio: %s\n", e.getMessage());
        }
    }

    public AudioPlayerComponent(URI audio) {
        this();
        try {
            this.audio = AudioSystem.getAudioInputStream(new File(audio));
            this.clip = AudioSystem.getClip();
            this.clip.open(this.audio);
        } catch (Exception e) {
            System.out.printf("Erro carregando audio: %s\n", e.getMessage());
        }
    }

    public void play() {
        try {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {
            System.out.printf("Error playing audio: %s\n", e.getMessage());
        }
    }

}
