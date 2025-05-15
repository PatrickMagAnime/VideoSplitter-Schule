package videosplitter;

import videosplitter.gui.*;
import javax.swing.*;

//start des programms
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProInfoWindow proInfo = new ProInfoWindow();
            proInfo.setVisible(true);

            MainWindow main = new MainWindow();
            main.setLocationRelativeTo(null);
            main.setVisible(true);
        });
    }
}
