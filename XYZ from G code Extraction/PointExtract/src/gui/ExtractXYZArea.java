
package gui;

import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import work.Extract;

/*
 * constructs the drop area and the actions behind extracting the text and
 * searching for a specified pattern. <br> <br>
 * 
 * @company The Boeing Company
 * 
 * @author Kenneth Baldridge
 * 
 * @version 1.1
 * 
 */
@SuppressWarnings ("serial")
public class ExtractXYZArea extends JTextPane implements DropTargetListener {
    
    private int fileCount = 0;

    private static final Font FONT = new Font("Times New Roman", Font.PLAIN, 20);

    public ExtractXYZArea() {
        this.setEditable(false);
        StyledDocument doc = this.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        this.setText("\n\n\n[Drop Files Here]");
        this.setFont(FONT);
        new DropTarget(this, this);

    }

    public final void drop(DropTargetDropEvent dtde) {

        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = dtde.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        for (DataFlavor flavor : flavors) {

            try {

                if (flavor.isFlavorJavaFileListType()) {

                    @SuppressWarnings ("unchecked")
                    List<File> droppedFiles = (List<File>) transferable
                            .getTransferData(flavor);

                    for (File dp : droppedFiles) {

                        String fileName = dp.getName();
                        String fileExtension = fileName.substring(
                                fileName.lastIndexOf(".") + 1, fileName.length());
                        String acceptable_NC_Extension = "nc";
                        String acceptable_MCD1_Extension = "mcd1";
                        String acceptable_PRG_Extension = "prg";
                        String acceptable_DAT_Extension = "dat";

                        if (dp.isFile()
                                & acceptable_NC_Extension
                                        .equalsIgnoreCase(fileExtension)
                                || acceptable_MCD1_Extension
                                        .equalsIgnoreCase(fileExtension)
                                || acceptable_PRG_Extension
                                        .equalsIgnoreCase(fileExtension)
                                || acceptable_DAT_Extension
                                        .equalsIgnoreCase(fileExtension)) {
                            fileCount++;
                            Extract.extract(dp, this);

                        } else {

                            this.setText(
                                    "That was not a G CODE file.\nPlease drop a G Code file. \nExample: .nc, .mcd1, .prg, .dat");
                        }
                    }

                } else {
                    this.setText(
                            "That was not a G CODE file.\nPlease drop a G Code file. \nExample: .nc, .mcd1, .prg, .dat");
                }

            } catch (Exception e) {

                e.printStackTrace();

            }
            
        }

        dtde.dropComplete(true);
        this.setText(fileCount + " Files Created.");
    }

    @Override
    public void dragEnter(DropTargetDragEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void dragExit(DropTargetEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void dragOver(DropTargetDragEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent arg0) {

        // TODO Auto-generated method stub

    }
}
