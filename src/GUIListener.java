import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GUIListener extends JFrame implements PropertyChangeListener {
    private final JProgressBar progressBar;
    private final JButton startButton;

    public GUIListener() {

        setTitle("JProgressBar Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        UIManager.put("ProgressBar.background", new Color(141, 38, 99)); // Arkaplan rengi
        UIManager.put("ProgressBar.foreground", new Color(255, 0, 0));   // İlerleme çubuğu rengi
        // FlatLaf temasını güncelleme
        SwingUtilities.updateComponentTreeUI(progressBar);
        FlatDarkLaf.setup();
        JPanel panel = new JPanel();
        panel.add(progressBar);

        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            progressBar.setString("Started");
            new Thread(sender).start();
        });
        panel.add(startButton);

        add(panel);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
        if ("progress".equals(evt.getPropertyName())) {
            int progressValue = (int) evt.getNewValue();
            progressBar.setValue(progressValue);
        } else if ("done".equals(evt.getPropertyName())) {
            progressBar.setString("Done");
            startButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // FlatLaf temasını belirleme (Light veya Dark temasını seçebilirsiniz)
            /*try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }*/

            GUIListener guiListener = new GUIListener();
            Sender sender = new Sender();
            sender.addPropertyChangeListener(guiListener);
            guiListener.setSender(sender);

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            frame.add(guiListener.getContentPane(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }
    public Sender sender;
    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
