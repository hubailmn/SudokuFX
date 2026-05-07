package sa.edu.uqu.cs4108.sudokufx.util;

import javafx.scene.media.AudioClip;

public class SoundManager {

    private static final AudioClip HOVER_SOUND = loadSound("/sounds/hover.mp3");
    private static final AudioClip FAH_SOUND = loadSound("/sounds/fah.mp3");
    private static final AudioClip ACCEPT_SOUND = loadSound("/sounds/accept.mp3");
    private static final AudioClip DENY_SOUND = loadSound("/sounds/deny.mp3");
    private static final AudioClip PUNISHED_SOUND = loadSound("/sounds/punished.mp3");
    private static final AudioClip SWITCH_SOUND = loadSound("/sounds/switch.mp3");


    private static AudioClip loadSound(String path) {
        try {
            return new AudioClip(SoundManager.class.getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Could not load sound: " + path);
            return null;
        }
    }

    public static void playHover() {
        if (HOVER_SOUND != null) {
            HOVER_SOUND.play(0.35);
        }
    }

    public static void playFah() {
        if (FAH_SOUND != null) {
            FAH_SOUND.play(0.5);
        }
    }


    public static void playAccept() {
        if (ACCEPT_SOUND != null) {
            ACCEPT_SOUND.play(1);
        }
    }

    public static void playDeny() {
        if (DENY_SOUND != null) {
            DENY_SOUND.play(0.5);
        }
    }

    public static void playPunished() {
        if (PUNISHED_SOUND != null) {
            PUNISHED_SOUND.play(0.5);
        }
    }

    public static void playSwitch() {
        if (SWITCH_SOUND != null) {
            SWITCH_SOUND.play(0.5);
        }
    }

}