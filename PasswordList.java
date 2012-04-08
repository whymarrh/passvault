import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.lang.String;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * The JPanel that holds and controls the password listing.
 * Uses getters ans setters to access the passwords.
 */
public class PasswordList extends JPanel {
    private final int x = 400;
    private final int y = 400;
    private final int m = 10; // margin in px
    private DefaultListModel model = null;
    private JList list = null;
    private Secure secure = null;
    private String filename = ".l.obj";
    private JScrollPane scroller = null;
    private HashMap<String, String> pwdList = null;
    /**
     * Constructs the password listing conatiner and
     * populates the panel.
     */
    public PasswordList() {
        super(new BorderLayout());
        secure = new Secure();
        setBorder(BorderFactory.createEmptyBorder(m, m, m, m));
        popList();
        prepScroller();
        add(scroller, BorderLayout.CENTER);
        setPreferredSize(new Dimension(x, y));
    }
    /**
     * Adds or overwrites a password to the list.
     * @param name the service or site for which the password is to.
     * @param password the unencrypted password.
     */
    public void addNew(String name, String password) {
        if (pwdList.containsKey(name)) {
            pwdList.remove(name);
            model.removeElement(name);
        }
        model.addElement(name);
        pwdList.put(name, secure.encrypt(password));
    }
    /**
     * Serializes the list and saves it to the disk.
     */
    public void save() {
        try {
            new File(filename).delete();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(pwdList);
            oos.close();
        } catch (Exception e) {
            ;
        }
        return;
    }
    /**
     * Either repopulates the list with the saved entires,
     * or creates a new empty list if there exists no saved
     * entries.
     */
    private void popList() {
        model = new DefaultListModel();
        boolean listExists = new File(filename).exists();
        if (listExists) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
                pwdList = (HashMap<String, String>) ois.readObject();
                ois.close();
                for (String s : pwdList.keySet()) {
                    model.addElement(s);
                }
            } catch (Exception e) {
                pwdList = new HashMap<String, String>();
            }
        } else {
            pwdList = new HashMap<String, String>();
        }
        list = new JList(model);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        String key = (String) model.getElementAt(index);
                        String value = pwdList.get(key);
                        new ViewPassword(key, secure.decrypt(value));
                    }
                }
            }
        });
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
    }
    /**
     * Puts the password listing into a scrolling pane. Thus allowing
     * for a lot more passwords to be accessible.
     */
    private void prepScroller() {
        final int m = 10; // padding in px
        scroller = new JScrollPane(list);
        scroller.setViewportBorder(BorderFactory.createEmptyBorder(m, m, m, m));
        scroller.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}