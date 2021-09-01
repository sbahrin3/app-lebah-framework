package lebah.mail;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import lebah.api.Settings;


public class EmailSenderUtil {

	public static boolean authenticate;
	public static String username;
	public static String password;
	public static String plainPassword;
	public static String host;
	public static String port;
	
	public static String from;
	public static String to;
	public static String message;
	
	

	public static void send(String mailTo, String mailSubject, String mailText) throws Exception {
		send(mailTo, mailSubject, mailText, null);
	}
	
	public static void send(String mailTo, String mailSubject, String mailText, List<String> recepients)
			throws Exception {
		
		username = Settings.SENDMAIL_USERNAME;
		password = Settings.SENDMAIL_PASSWORD;
		host = Settings.SENDMAIL_HOST;
		port = Settings.SENDMAIL_PORT;
		authenticate = !"".equals(password);
		
		Properties props = new Properties();
		
		
		if ( authenticate ) {
			props.put("mail.smtp.auth", "true");
			if ( "587".equals(port) ) {
				System.out.println("starttls is enabled.");
				props.put("mail.smtp.starttls.enable", "true");
				
			} else if ( "465".equals(port)) {
				//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.ssl.enable", "true");
			}
		}
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		
		Session session = null;
		if ( authenticate ) {
			
	 		session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
		} else {
			session = Session.getInstance(props);
		}
		
		session.setDebug(true);
 	
		Message message = new MimeMessage(session);
		String mailFrom = EmailSenderUtil.from;
		if ( mailFrom == null || "".equals(mailFrom) ) {
			mailFrom = "nobody@email.com";
		}
		if ( mailFrom != null && !"".equals(mailFrom) ) {
			System.out.println("mail from: " + mailFrom);
			message.setFrom(new InternetAddress(mailFrom));
		}
		else {
			message.setFrom(new InternetAddress("nobody"));
		}
		
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
		
		if ( recepients != null ) {
			for ( String add : recepients ) {
		         message.addRecipient(Message.RecipientType.CC, new InternetAddress(add));
			}
		}
		
		message.setSubject(mailSubject);
		message.setContent(mailText, "text/html" );
		Transport.send(message);
	}
	
	public static void sendUsingTemplate(String to, String subject, VelocityEngine engine, VelocityContext context, String template) {
		try {
			String text = createEmailContent(engine, context, template);
			send(to, subject, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String createEmailContent(VelocityEngine engine, VelocityContext context, String templateName) throws Exception {
		StringBuffer sb = new StringBuffer("");
		try {
			Template template = engine.getTemplate(templateName); 
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			writer.close();
			sb = writer.getBuffer();
		} catch ( Exception ex ) {
		    ex.printStackTrace();
			throw ex;			
		}
		return sb.toString();
	}	
	
	public static void main(String[] args) throws Exception {
		
		username = "lebahemail@gmail.com";
		password = "xdvcsvsdauyhitxv";
		host = "smtp.gmail.com";
		port = "587";
	
		MailerDaemon.getInstance().addMessage("sbahrin3@gmail.com", "Test Send Email", "Test Send Email 1");
		Thread.sleep(2000);
		MailerDaemon.getInstance().addMessage("sbahrin3@gmail.com", "Test Send Email", "Test Send Email 2");
		
	}


	
}