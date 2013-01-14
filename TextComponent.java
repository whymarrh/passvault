import java.lang.String;
import java.lang.Exception;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JPanel;

/**
 * @author Whymarrh Whitby
 * @version 1.0
 * A panel that displays a string in large font.
 */
public class TextComponent extends JPanel {

	/**
	 * Constructs the TextComponent class and creates the panel
	 * populated with the text component.
	 * @param s the string of text to display.
	 */
	public TextComponent(String s) {
		text = s;
		font = createFont("fonts/bebas.ttf", 70);
		setPreferredSize(new Dimension(x, y));
	}
	/**
	 * Constructs the TextComponent class and creates the panel
	 * populated with the text component.
	 * @param s the string of text to display.
	 * @param f the integer size of font desired in points.
	 */
	public TextComponent(String s, int f) {
		text = s;
		font = createFont("fonts/bebas.ttf", f);
		setPreferredSize(new Dimension(x, y));
	}
	/**
	 * Creates and returns a new font from the given filename
	 * in the specified size.
	 * @param filename is the filename of the TTF file on disk.
	 * @param pts the integer size of font in points desired.
	 */
	private Font createFont(String filename, int pts) {
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
			return f.deriveFont((float) pts);
		} catch (Exception e) {/* Error Handling */}
		return new Font(Font.SANS_SERIF, Font.BOLD, pts);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int w = getWidth();
		int h = getHeight();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(font);
		int width = g2.getFontMetrics().stringWidth(text);
		int height = g2.getFontMetrics().getHeight() - 34;
		g2.drawString(text, (w - width) / 2, h / 2 + height / 2);
	}

	private final int x = 400; // px
	private final int y = 100; // px
	private String text = null;
	private Font font = null;
	// http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html
	private static final long serialVersionUID = 42L;

}
