package videosplitter.core;

import com.google.gson.Gson;
import java.io.*;

//einstellungen werden als .json gespeichert

public class Settings {
    private String lastDirectory;

    public String getLastDirectory() {
        return lastDirectory;
    }

    public void setLastDirectory(String lastDirectory) {
        this.lastDirectory = lastDirectory;
    }

    public void saveToFile(File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            new Gson().toJson(this, writer);
        }
    }

    public static Settings loadFromFile(File file) throws IOException {
        try (Reader reader = new FileReader(file)) {
            return new Gson().fromJson(reader, Settings.class);
        }
    }
}
