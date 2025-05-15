package videosplitter.gui;

import javax.swing.*;
import java.awt.*;

//panel zur Anzeige eines Bildes.

public class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(String path) {
        img = new ImageIcon(path).getImage();
        setPreferredSize(new Dimension(180, 180));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null)
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}
