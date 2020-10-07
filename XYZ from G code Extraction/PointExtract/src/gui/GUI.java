
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Constructs the graphical interface for the program. <br>
 * <br>
 * 
 * @company The Boeing Company
 * @author Kenneth Baldridge
 * @version 1.1
 * 
 */
@SuppressWarnings ("serial")
public class GUI extends JFrame {

    final private static Dimension DIMENSION = new Dimension(550, 300);

    final private static String COPYRIGHT = "\u00a9";

    public GUI() {

        super("GrabItXYZ!" + COPYRIGHT);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            setFrameIcon();
        } catch (final IOException e) {

            e.printStackTrace();
        }

        final JLabel label = new JLabel("Drop a file here to grab data");

        this.setPreferredSize(DIMENSION);

        this.setJMenuBar(new MenuBar());

        this.add(label, BorderLayout.NORTH);

        final JScrollPane spMyTable = new JScrollPane(new ExtractXYZArea(),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(spMyTable, BorderLayout.CENTER);

        this.pack();
        SetWindowPosition.setPosition(this);
        this.setVisible(true);
    }

    /**
     * Sets the Icon used in this JFrame.
     * 
     * @throws IOException
     */
    private final void setFrameIcon() throws IOException {

        final URL imageUrl = getClass().getResource("res/Boeing.png");
        final Image iconImg = ImageIO.read(imageUrl);
        this.setIconImage(iconImg);

    }

}
