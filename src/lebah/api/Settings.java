/**
 * 
 */
package lebah.api;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Shamsul Bahrin
 *
 */
public class Settings {
	
	private static ResourceBundle rb;
	
	public static String API_URL;
	
	public static String EMAIL_NOTIFY_ENABLED;
	public static String SENDMAIL_USERNAME;
	public static String SENDMAIL_PASSWORD;
	public static String SENDMAIL_HOST;
	public static String SENDMAIL_PORT;	
	public static String EMAIL_TEMPLATE_DIR;
	
		
    static {
    	init();
    }
    
    public static void init() {
    	readProperties();
    	
    	System.out.println("===");
		System.out.println("API_URL: " + Settings.API_URL);
		
		System.out.println("EMAIL_NOTIFY_ENABLED: " + Settings.EMAIL_NOTIFY_ENABLED);
		System.out.println("SENDMAIL_USERNAME: " + Settings.SENDMAIL_USERNAME);
		System.out.println("SENDMAIL_PASSWORD: " + Settings.SENDMAIL_PASSWORD);
		System.out.println("SENDMAIL_HOST: " + Settings.SENDMAIL_HOST);
		System.out.println("SENDMAIL_PORT: " + Settings.SENDMAIL_PORT);
		System.out.println("EMAIL_TEMPLATE_DIR: " + Settings.EMAIL_TEMPLATE_DIR);
		
		System.out.println("===");
    }
    
	public static void readProperties() {
		try {
			rb = ResourceBundle.getBundle("settings");
			readProps();
		} catch ( MissingResourceException e ) {
			e.printStackTrace();
		}
       
	}

	private static void readProps() {
		API_URL = rb.getString("API_URL");
		
		EMAIL_NOTIFY_ENABLED = rb.getString("EMAIL_NOTIFY_ENABLED");
		SENDMAIL_USERNAME = rb.getString("SENDMAIL_USERNAME");
		SENDMAIL_PASSWORD = rb.getString("SENDMAIL_PASSWORD");
		SENDMAIL_HOST = rb.getString("SENDMAIL_HOST");
		SENDMAIL_PORT = rb.getString("SENDMAIL_PORT");	
		EMAIL_TEMPLATE_DIR = rb.getString("EMAIL_TEMPLATE_DIR");
	}
	
	public static void main(String[] args) {
		
		Settings.init();

		
	}
}
