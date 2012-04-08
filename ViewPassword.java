import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * A simple window displaying the password and site info.
 */
public class ViewPassword extends JFrame {
    /**
     * A simple panel that contains two labels.
     * Used in the main ViewPassword class.
     */
    private class Panel extends JPanel {
        private int b = 40;
        private int m = 5;
        /**
         * Constructs the panel and creates the two labels.
         * @param n the text of the first label - the name of the site/service.
         * @param p the text of the second label - the unencrypted password.
         */
        public Panel(String n, String p) {
            super(new BorderLayout());
            JLabel lbl = new JLabel("<html><b>-- " + n.toUpperCase() + " --</b></html>", SwingConstants.CENTER);
            lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, lbl.getFont().getSize() + 35));
            lbl.setBorder(BorderFactory.createEmptyBorder(b, b, m, b));
            add(lbl, BorderLayout.PAGE_START);
            lbl = new JLabel(p, SwingConstants.CENTER);
            lbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, lbl.getFont().getSize() + 20));
            lbl.setBorder(BorderFactory.createEmptyBorder(m, b, b, b));
            add(lbl, BorderLayout.PAGE_END);
        }
    }
    /**
     * Constructs the ViewPassword class and creates the
     * main window displaying the information.
     * @param n the name of the service.
     * @param pwd the unencrypted password
     */
    public ViewPassword(String n, String pwd) {
        super("Password For \"" + n + "\"");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(new Panel(n, pwd));
        pack();
        setVisible(true);
        setResizable(false);
    }
}