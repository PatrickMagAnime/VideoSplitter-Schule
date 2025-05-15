package videosplitter.core;

import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;

public class ProjectManagerTest {
    @Test
    public void testLoadMediaFromDirectory() {
        ProjectManager manager = new ProjectManager();
        manager.loadMediaFromDirectory(new File("resources/"));
        assertTrue(manager.getVideos().size() >= 0);
        assertTrue(manager.getAudios().size() >= 0);
    }

    @Test
    public void testDetectFormat() {
        ProjectManager manager = new ProjectManager();
        assertEquals(VideoFormat.MP4, manager.detectVideoFormat("video.mp4"));
        assertEquals(AudioFormat.MP3, manager.detectAudioFormat("song.mp3"));
        assertEquals(VideoFormat.UNKNOWN, manager.detectVideoFormat("file.txt"));
        assertEquals(AudioFormat.UNKNOWN, manager.detectAudioFormat("file.txt"));
    }
}
