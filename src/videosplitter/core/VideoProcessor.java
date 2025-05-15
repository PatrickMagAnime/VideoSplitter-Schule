package videosplitter.core;

import javax.swing.*;
import java.io.File;


//Dummy Klasse für Video und Audio bearbeitung.

public class VideoProcessor {

    //Simuliert das Splitten eines Videos in mehrere Teile.

    public void splitVideo(VideoFile file, int parts, String customName) {
        //dummy implementierung
        JOptionPane.showMessageDialog(null, "Video '" + file.getPath() +
                "' wurde als '" + customName + "_partX' in " + parts + " Teile gesplittet (Demo).");
    }

    //simuliert das Extrahieren von Audio aus Video.

    public void extractAudioFromVideo(VideoFile videoFile, String customAudioName, File targetDir, AudioFormat af) {
        //dummy implementierung (hier könnte in echt FFmpeg eingebunden werden) //TODO:ffmpeg ding
        JOptionPane.showMessageDialog(null,
                "Audio aus '" + videoFile.getPath() + "' als '" + customAudioName + "." + af.name().toLowerCase() +
                        "' extrahiert (Demo).\nZielordner: " + targetDir.getAbsolutePath());
    }
}
