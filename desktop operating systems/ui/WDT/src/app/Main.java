package app;

import app.Configuration.JavaFXStarter;
import app.Common.Options;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static JFrame frame = new JFrame(Options.APPLICATION_TITLE);

    private static final int width = 711;
    private static final int height = 440;

    public static void main(String[] args) throws IOException {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setLocation(
                (dimension.width / 2) - (width / 2),
                (dimension.height / 2) - (height / 2)
        );
        URL logoImage = Options.class.getClassLoader()
                .getResource("resource/IMG/ic_launcher-playstore.png");
        assert logoImage != null;
        frame.setIconImage(ImageIO.read(logoImage));
        JPanel panel = new JPanel();
        Image log = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        log.getGraphics().drawImage(ImageIO.read(logoImage), 0, 0, 100, 100, null);
        log.getGraphics().dispose();
        JLabel label = new JLabel(new ImageIcon(log));
        label.setPreferredSize(new Dimension(width, height - 20));
        panel.add(label);
        panel.setOpaque(true);
        panel.setBackground(Color.white);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        new JavaFXStarter().start();
    }

}