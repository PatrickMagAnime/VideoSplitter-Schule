package videosplitter.core;

//klasse nur für mediendateien
//diese klassen existieren nur um die aufgabenstellung zu erfüllen

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