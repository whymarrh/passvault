import static java.lang.System.exit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.security.MessageDigest;
import java.lang.Integer;
import java.lang.String;
import java.lang.StringBuffer;
import java.lang.Exception;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * This Passvault class is the main class for the Passvault
 * application. Here the main window is created and all sub
 * windows are offered. From here user authentication also occurs.
 */
public class Passvault extends JFrame {
    private PasswordList pwdList = null;
    private JPanel panel = null;
    private String label = null;
    private BufferedWriter wtr = null;
    private String filename = ".pwd";
    /**
     * Constructs the main Passvault class and its JFrame
     * superclass as well. Creates the main window
     * and ensures that the user gets authenticated.
     */
    public Passvault(String title) {
        super(title);
        setBackground(Color.WHITE);
        this.label = title;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new Menubar(this));
        add(populatePanel(panel));
        pack();
        int x = 800;
        int y = 400;
        setMinimumSize(new Dimension(x, y));
        setVisible(true);
        if (!authenticate()) exit(0);
    }
    /**
     * Adds or overwrites a password to the list.
     * @param name the service or site for which the password is to.
     * @param s the unencrypted password.
     */
    public void setNextPassword(String name, String s) {
        pwdList.addNew(name, s);
    }
    /**
     * Calls the save method of the password list
     * contained in this window.
     */
    public void save() {
        pwdList.save();
    }
    /**
     * Returns the SHA-512 hashcode of the given string.
     * @param s the string to be hashed.
     * @return the SHA-512 hashcode of {@code s}.
     */
    private String sha512(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(s.getBytes());
        byte[] bytes = md.digest();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String tmp = Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
            buffer.append(tmp);
        }
        return buffer.toString();
    }
    /**
     * Pops up a small window to allow the user to enter a/the password.
     * @param prompt the message to show as the prompt for the user.
     * @return the raw password that the user entered.
     */
    private String askForPassword(String prompt) {
        JPasswordField blackbox = new JPasswordField(40);
        JLabel lbl = new JLabel(prompt);
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 5));
        final JComponent inpts[] = {lbl, blackbox};
        JOptionPane.showMessageDialog(this, inpts, "Master Password", JOptionPane.PLAIN_MESSAGE);
        return new String(blackbox.getPassword());
    }
    /**
     * Either creates or authenticates the user's password
     * and stores the password hash in a file on disk. The
     * same file is then used to reauthenticate the user.
     * @return the user authenticated, {@code true} or {@code false}.
     */
    private boolean authenticate() {
        boolean pwdFileExists = new File(filename).exists();
        if (!pwdFileExists) {
            try {
                String pass = null;
                while (true) {
                    pass = askForPassword("Please create a master password with more than six (6) characters: ");
                    if (pass.equals("")) throw new Exception();
                    if (pass.length() < 6) continue;
                    else break;
                }
                String hash = sha512(pass);
                wtr = new BufferedWriter(new FileWriter(filename));
                wtr.write(hash);
                wtr.newLine();
                wtr.flush();
                wtr.close();
            } catch (Exception e) { return false; }
            return true;
        } else {
            try {
                String pass = null;
                while (true) {
                    pass = askForPassword("Authenticate yourself, please: ");
                    if (pass.equals("")) throw new Exception();
                    if (pass.length() < 6) continue;
                    String hash = sha512(pass);
                    pass = (new Scanner(new File(filename))).nextLine();
                    if (pass.equals(hash)) return true;
                }
            } catch (Exception e) { return false; }
        }
    }
    /**
     * Fills the given panel with the needed components.
     * @param panel the empty panel.
     * @return the populated panel.
     */
    private JPanel populatePanel(JPanel panel) {
        panel = new JPanel(new BorderLayout());
        pwdList = new PasswordList();
        panel.add(new TextComponent(label), BorderLayout.LINE_START);
        panel.add(pwdList, BorderLayout.CENTER);
        return panel;
    }
    /**
     * The main method instantiates the Passvault object.
     */
    public static void main(String[] args) {
        Passvault ps = new Passvault("Passvault");
    }
}