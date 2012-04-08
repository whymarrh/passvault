import java.awt.Color;
import java.io.File;
import java.lang.Exception;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.imageio.ImageIO;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * A simple picture viewer class.
 */
public class ImageFrame extends JFrame {
    private JLabel lbl = null;
    private int m = 1;
    /**
     * Constructs the ImageFrame and its JFrame superclass.
     * @param title the title of the window - usually the same as the name of the image.
     * @param filename the full path to and filename of the image.
     */
    public ImageFrame(String title, String filename) {
        super(title);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            lbl = new JLabel(new ImageIcon(ImageIO.read(new File(filename))));
        } catch (Exception e) {;}
        lbl.setBorder(BorderFactory.createEmptyBorder(m, m, m, m));
        add(lbl);
        pack();
        setResizable(false);
        setVisible(true);
    }
}