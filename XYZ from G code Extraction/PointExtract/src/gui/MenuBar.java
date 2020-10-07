
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Constructs the menu bar. <br>
 * <br>
 * 
 * @company The Boeing Company
 * @author Kenneth Baldridge
 * @version 1.1
 * 
 */
@SuppressWarnings ("serial")
public class MenuBar extends JMenuBar {

    /** Copyright symbol. */
    public static final String COPYRIGHT = "\u00a9";

    /** About Menu. */
    private final JMenu myAboutMenu = new JMenu("Help");

    public MenuBar() {

        super();
        start();
    }

    private void start() {

        buildHelpMenu();
        this.add(myAboutMenu);

    }

    private void buildHelpMenu() {

        myAboutMenu.setMnemonic(KeyEvent.VK_H);
        final JMenuItem AboutItem = new JMenuItem("About PointExtract",
                KeyEvent.VK_A);
        AboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {

                        JOptionPane.showMessageDialog(null,
                                "PointExtract\nVersion 1.0\n" + "COPYRIGHT "
                                        + COPYRIGHT + " 2019 The Boeing Company\n"
                                        + "Author: Kenneth Baldridge, AMS ME\n"
                                        + "Email: Kenneth.p.baldridge@boeing.com\n\n"
                                        + "PointExtract is a basic text retrieval tool built to extract data points \n from various G-code files.",
                                "About", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }
        });
        myAboutMenu.add(AboutItem);

    }

}
