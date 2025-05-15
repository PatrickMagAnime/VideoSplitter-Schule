package videosplitter.gui;

import videosplitter.core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//hauptfenster des Programms.

public class MainWindow extends JFrame {
    private ProjectManager manager = new ProjectManager();
    private Settings settings = new Settings();

    public MainWindow() {
        setTitle("VideoSplitter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 500);

        //Bildpanel
        add(new ImagePanel("resources/sample.jpg"), BorderLayout.WEST);

        //buttons
        JPanel buttonPanel = new JPanel();
        JButton btnLoad = new JButton("Verzeichnis laden");
        JButton btnSaveSettings = new JButton("Einstellungen speichern");
        JButton btnLoadSettings = new JButton("Einstellungen laden");
        JButton btnSplit = new JButton("Video splitten");
        JButton btnExtractAudio = new JButton("Audio extrahieren");

        buttonPanel.add(btnLoad);
        buttonPanel.add(btnSplit);
        buttonPanel.add(btnExtractAudio);
        buttonPanel.add(btnSaveSettings);
        buttonPanel.add(btnLoadSettings);

        add(buttonPanel, BorderLayout.SOUTH);

        // Video liste
        DefaultListModel<MediaFile> listModel = new DefaultListModel<>();
        JList<MediaFile> mediaList = new JList<>(listModel);
        mediaList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.toString());
            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(new Color(200,230,255));
            }
            return label;
        });
        add(new JScrollPane(mediaList), BorderLayout.CENTER);

        //Umschalt Button
        JToggleButton toggleVideos = new JToggleButton("Zeige Videos", true);
        JToggleButton toggleAudios = new JToggleButton("Zeige Audios");
        ButtonGroup group = new ButtonGroup();
        group.add(toggleVideos);
        group.add(toggleAudios);

        JPanel togglePanel = new JPanel();
        togglePanel.add(toggleVideos);
        togglePanel.add(toggleAudios);
        add(togglePanel, BorderLayout.NORTH);

        //aktionen
        btnLoad.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = chooser.getSelectedFile();
                try {
                    manager.getVideos().clear();
                    manager.getAudios().clear();
                    manager.loadMediaFromDirectory(dir);
                    listModel.clear();
                    for (VideoFile vf : manager.getVideos()) listModel.addElement(vf);
                    settings.setLastDirectory(dir.getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fehler beim Laden: " + ex.getMessage());
                }
            }
        });

        toggleVideos.addActionListener(e -> {
            listModel.clear();
            for (VideoFile vf : manager.getVideos())
                listModel.addElement(vf);
        });

        toggleAudios.addActionListener(e -> {
            listModel.clear();
            for (AudioFile af : manager.getAudios())
                listModel.addElement(af);
        });

        btnSaveSettings.addActionListener(e -> {
            try {
                settings.saveToFile(new File("settings.json"));
                JOptionPane.showMessageDialog(this, "Einstellungen gespeichert.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Speichern fehlgeschlagen: " + ex.getMessage());
            }
        });

        btnLoadSettings.addActionListener(e -> {
            try {
                Settings s = Settings.loadFromFile(new File("settings.json"));
                settings.setLastDirectory(s.getLastDirectory());
                JOptionPane.showMessageDialog(this, "Einstellungen geladen.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Laden fehlgeschlagen: " + ex.getMessage());
            }
        });

        btnSplit.addActionListener(e -> {
            MediaFile mf = mediaList.getSelectedValue();
            if (!(mf instanceof VideoFile)) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Video aus.");
                return;
            }
            String input = JOptionPane.showInputDialog(this, "In wie viele Teile splitten?");
            if (input == null) return;
            String customName = JOptionPane.showInputDialog(this, "Basisname für Ausgabedateien?");
            if (customName == null || customName.trim().isEmpty()) {
                customName = "output";
            }
            try {
                int parts = Integer.parseInt(input);
                new VideoProcessor().splitVideo((VideoFile)mf, parts, customName);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ungültige Eingabe.");
            }
        });

        btnExtractAudio.addActionListener(e -> {
            MediaFile mf = mediaList.getSelectedValue();
            if (!(mf instanceof VideoFile)) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Video aus.");
                return;
            }
            VideoFile vf = (VideoFile) mf;
            String customName = JOptionPane.showInputDialog(this, "Name für die Audiodatei?");
            if (customName == null || customName.trim().isEmpty()) {
                customName = "audio";
            }
            AudioFormat[] audioFormats = AudioFormat.values();
            String[] formatNames = new String[audioFormats.length - 1];
            for (int i=0,k=0; i<audioFormats.length; i++) {
                if (audioFormats[i]!=AudioFormat.UNKNOWN) formatNames[k++] = audioFormats[i].name();
            }
            String selectedFormat = (String) JOptionPane.showInputDialog(this,
                    "Zielformat?", "Audioformat wählen",
                    JOptionPane.QUESTION_MESSAGE, null,
                    formatNames, formatNames[0]);
            if (selectedFormat == null) return;
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = chooser.getSelectedFile();
                new VideoProcessor().extractAudioFromVideo(
                        vf, customName, dir, AudioFormat.valueOf(selectedFormat));
            }
        });
    }
}
