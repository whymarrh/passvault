import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import static java.lang.System.out;

/**
* @author Whymarrh Whitby
* @version 1.0
* A class supplying encryption and decryption functionality using
* the Advanced Encryption Algorithm. Note that the two classes,
* sun.misc.BASE64Encoder and sun.misc.BASE64Decoder are proprietary
* classes and may not be included in the future.
*/
public class Secure extends Object {

	/**
	 * Example of the class' usage.
	 */
	public static void main(String[] args) {
		Secure secure = new Secure();
		String msg = "This is a test message.";
		String enc = secure.encrypt(msg);
		String dec = secure.decrypt(enc);
		out.println("ORIG: " + msg);
		out.println("ENCR: " + enc);
		out.println("DECR: " + dec);
	}
	/**
	* Creates a Secure object.
	*/
	public Secure() {
		try {
			boolean keyExists = new File(filename).exists();
			if (keyExists) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
				s = (SecretKey) ois.readObject();
				ois.close();
				cipher = Cipher.getInstance("AES");
				return;
			}
			KeyGenerator keygenr = KeyGenerator.getInstance("AES");
			keygenr.init(128);
			s = keygenr.generateKey();
			cipher = Cipher.getInstance("AES");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(s);
			oos.close();
		} catch (Exception e) {
			secure = false;
		}
	}
	/**
	* Encrypts the given message using a 128-bit AES encryption key,
	* and returns the encrypted version as a {@link java.lang.String}
	* @param msg the message to be encrypted.
	* @return s the encrypted version of the message.
	*/
	public String encrypt(String msg) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, s);
			// encrypt the bytes using doFinal(bytes[] input) method
			byte[] bytes = cipher.doFinal(msg.getBytes());
			return new BASE64Encoder().encode(bytes);
		} catch (Exception e) {
			secure = false;
			return null;
		}
	}
	/**
	* Decrypts the given string using the secret key previously created,
	* and returns as a {@link java.lang.String}.
	* @param msg is the encrypted version of the message.
	* @return The unencrypted (decrypted) version of the message.
	*/
	public String decrypt(String msg) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, s, cipher.getParameters());
			// decrypt the bytes using doFinal(bytes[] input) method
			byte[] bytes = cipher.doFinal(new BASE64Decoder().decodeBuffer(msg));
			return new String(bytes);
		} catch (Exception e) {
			secure = false;
			return null;
		}
	}
	/**
	* Returns {@code true} if no errors have occurred in the setup of the class,
	* or the encryption or decryption of a string, thus the security of the app
	* has not been compromised.
	* @return The secure state of the object: {@code true} if the app is still secure.
	*/
	public boolean isSecure() {
		return secure;
	}

	private Cipher cipher = null;
	private boolean secure = true;
	private SecretKey s = null;
	private String filename = ".s.obj";

}
