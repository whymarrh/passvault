import java.net.URI;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import static java.lang.System.out;
import static java.lang.System.exit;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * The menubar for the Passvault main window.
 */
public class Menubar extends JMenuBar {

	/**
	 * Constructs the menubar and its individual menus
	 * and adds them to the window.
	 */
	public Menubar(Passvault pv) {
		this.pv = pv;
		addFileMenu();
		addViewMenu();
		desktopSupported = Desktop.isDesktopSupported();
		if (desktopSupported) desktop = Desktop.getDesktop();
		addHelpMenu();
	}
	/**
	 * Creates and adds the File menu to the menubar.
	 */
	private void addFileMenu() {
		menu = new JMenu("File");
		mi = new JMenuItem("Add Password");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new PasswordGenerator("Generate Secure Password", pv);
			}
		});
		menu.add(mi);
		menu.addSeparator();
		mi = new JMenuItem("Save All");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pv.save();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Quit Passvault");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pv.save();
				exit(0);
			}
		});
		menu.add(mi);
		add(menu);
	}
	/**
	 * Creates and adds the View menu to the menubar conditionally
	 * iff there exist two xkcd comics in a folder names pics.
	 * Specifically, http://xkcd.com/936/ and http://xkcd.com/538/
	 */
	private void addViewMenu() {
		final String file_1 = "xkcd/xkcd_password_strength.png";
		final String file_2 = "xkcd/xkcd_security.png";
		boolean img_1 = (new File(file_1)).exists();
		boolean img_2 = (new File(file_2)).exists();
		if (img_1 && img_2) {
			menu = new JMenu("View");
			mi = new JMenuItem("Related Hilarity #1");
			mi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					new ImageFrame("xkcd", file_1);
				}
			});
			menu.add(mi);
			mi = new JMenuItem("Related Hilarity #2");
			mi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					new ImageFrame("xkcd", file_2);
				}
			});
			menu.add(mi);
			add(menu);
		}
	}
	/**
	 * Creates and adds the Help menu to the menubar.
	 */
	private void addHelpMenu() {
		if (desktopSupported) {
			menu = new JMenu("Help");
			mi = new JMenuItem("What\'s a password?\t");
			mi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					try {
						desktop.browse(new URI("http://en.wikipedia.org/wiki/Password"));
					} catch (Exception e) {;}
				}
			});
			menu.add(mi);
			add(menu);
		}
	}

	private JMenuItem mi = null;
	private JMenu menu = null;
	private Desktop desktop = null;
	private boolean desktopSupported = false;
	private Passvault pv = null;
	// http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html
	private static final long serialVersionUID = 42L;

}
