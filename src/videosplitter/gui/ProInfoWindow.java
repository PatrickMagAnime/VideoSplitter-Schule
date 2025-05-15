package videosplitter.gui;

import javax.swing.*;
import java.awt.*;

//fenster für die Pro Version
public class ProInfoWindow extends JFrame {
    public ProInfoWindow() {
        setTitle("Pro-Version");
        setSize(300, 120);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Pro Version ist nicht aktiviert! :(", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label);
    }
}
