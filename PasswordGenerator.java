import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.lang.String;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import static java.lang.System.exit;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * The main password generator window.
 */
public class PasswordGenerator extends JFrame {

	/**
	 * Constructs the PasswordGenerator class and creates the
	 * main password generator window. Generates a random password
	 * and populates the window.
	 */
	public PasswordGenerator(String title, Passvault pv) {
		super(title);
		setBackground(Color.WHITE);
		this.pv = pv;
		add(prep(panel));
		pack();
		generatePassword();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	/**
	 * Generates a new random passsword and fills the password field with it.
	 */
	private void generatePassword() {
		int l = ((Integer) length.getValue()).intValue();
		StringBuilder sb = new StringBuilder(l);
		Random gen = new Random((new Random((new Random((new Random()).nextInt())).nextInt())).nextInt());
		for (int i = 0; i < l; i++) {
			char c = (char)(97 + gen.nextInt(26));
			sb.insert(i, c);
		}
		if (usePunct)
			for (int i = 0; i < l / 2; i++) {
			  int place = gen.nextInt(l);
			  char c = (char)(33 + gen.nextInt(15));
			  sb.deleteCharAt(place);
			  sb.insert(place, c);
			}
		if (useUppercase)
			for (int i = 0; i < l / 2; i++) {
			  int place = gen.nextInt(l);
			  char c = (char)(65 + gen.nextInt(26));
			  sb.deleteCharAt(place);
			  sb.insert(place, c);
			}
		if (useDigits)
			for (int i = 0; i < l / 2; i++) {
			  int place = gen.nextInt(l);
			  char c = (char)(48 + gen.nextInt(10));
			  sb.deleteCharAt(place);
			  sb.insert(place, c);
			}
		gp.setText(sb.toString());
		password = sb.toString();
	}
	/**
	 * Populates the main panel with all of the subpanels. Returns said panel.
	 * @return the populated main panel.
	 */
	private JPanel prep(JPanel pnl) {
		pnl = new JPanel(new BorderLayout());
		pnl.add(topPanel(), BorderLayout.PAGE_START);
		pnl.add(centerPanel(), BorderLayout.CENTER);
		pnl.add(bottomPanel(), BorderLayout.PAGE_END);
		return pnl;
	}
	/**
	 * Populates and returns the top panel.
	 * @return the top-most panel.
	 */
	private JPanel topPanel() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(new TextComponent("Passvault", 40), BorderLayout.CENTER);
		return pnl;
	}
	/**
	 * Populates and returns the middle panel.
	 * @return the center-most panel.
	 */
	private JPanel centerPanel() {
		int b = 50; int z = 10;
		JPanel row2 = new JPanel(new BorderLayout());
		gp = new JLabel(password, SwingConstants.CENTER);
		gp.setFont(new Font(Font.MONOSPACED, Font.BOLD, gp.getFont().getSize() + 10));
		gp.setBorder(BorderFactory.createEmptyBorder(b, z, b, z + z));
		row2.add(gp, BorderLayout.CENTER);
		JPanel row1 = new JPanel(new BorderLayout());
		JPanel row1x1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		int m = 5;
		Border bdr = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(m, m, m, m));
		name = new JTextField(13);
		name.setBorder(bdr);
		row1x1.add(new JLabel("This password is for: "));
		row1x1.add(name);
		row1.add(row1x1, BorderLayout.PAGE_START);
		JPanel row1x2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		row1x2.add(new JLabel("Password length: "));
		SpinnerNumberModel model = new SpinnerNumberModel(8, 6, 15, 1);
		length = new JSpinner(model);
		length.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				generatePassword();
			}
		});
		((DefaultEditor) length.getEditor()).getTextField().setEditable(false);
		row1x2.add(length);
		row1.add(row1x2, BorderLayout.CENTER);
		JPanel row1x3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JCheckBox lowcase = new JCheckBox("a-z");
		lowcase.setSelected(true);
		lowcase.setEnabled(false);
		final JCheckBox upcase = new JCheckBox("A-Z");
		upcase.setSelected(true);
		upcase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				useUppercase = !useUppercase;
				generatePassword();
			}
		});
		final JCheckBox digits = new JCheckBox("0-9");
		digits.setSelected(true);
		digits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				useDigits = !useDigits;
				generatePassword();
			}
		});
		final JCheckBox punct = new JCheckBox("Special");
		punct.setSelected(true);
		punct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				usePunct = !usePunct;
				generatePassword();
			}
		});
		row1x3.add(lowcase);
		row1x3.add(upcase);
		row1x3.add(digits);
		row1x3.add(punct);
		row1.add(row1x3, BorderLayout.PAGE_END);
		int e = 30;
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.setBorder(BorderFactory.createEmptyBorder(e, e, e, e));
		pnl.add(row1, BorderLayout.PAGE_START);
		pnl.add(row2, BorderLayout.CENTER);
		return pnl;
	}
	/**
	 * Populates and returns the bottom panel.
	 * @return the bottom-most panel.
	 */
	private JPanel bottomPanel() {
		JPanel pnl = new JPanel(new GridLayout(1, 3));
		JButton generate = new JButton("Generate new");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				generatePassword();
			}
		});
		JButton usepass = new JButton("Use this one");
		usepass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				service = name.getText();
				if (service == null || service.equals("")) {
					name.setBackground(new Color(1.0f, 0.0f, 0.0f, 0.5f));
					return;
				}
				pv.setNextPassword(service, password);
				dispose();
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		pnl.add(generate);
		pnl.add(usepass);
		pnl.add(cancel);
		return pnl;
	}

	private JPanel panel = null;
	private Passvault pv = null;
	private JLabel gp = null;
	private JSpinner length = null;
	private JTextField name = null;
	private JTextField udef = null;
	private String password = null;
	private String service = null;
	private boolean usePunct = true;
	private boolean useDigits = true;
	private boolean useUppercase = true;
	// http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html
	private static final long serialVersionUID = 42L;

}
