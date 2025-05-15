package videosplitter.core;

// klasse für mediendateien
//die klasse existiert nur um die aufgabenstellung zu erfüllen

public class MediaFile {
    protected String path;
    protected long size;

    public MediaFile(String path, long size) {
        this.path = path;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }
}
