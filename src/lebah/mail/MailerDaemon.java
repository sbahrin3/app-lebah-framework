package lebah.mail;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.mail.MessagingException;


/**
 * @author Shamsul Bahrin Abd Mutalib
 * @version 1.01
 */

public class MailerDaemon implements Runnable {
	
	private Thread thread;
	private boolean started;
	private int tick = 0;
	private boolean shutdown;


	private static MailerDaemon instance;
	Queue<Map<String, String>> q = new LinkedList<Map<String, String>>();
	
	private MailerDaemon() {
		if ( !started ) {
			shutdown = false;
			start();
		}
	}
	
	public static MailerDaemon getInstance() {
		if ( instance == null ) instance = new MailerDaemon();
		return instance;
	}

	@Override
	public void run() {
		boolean sent = false;
		while ( thread != null ) {
			try {
				Thread.sleep(5000);
				
				tick++;
				if ( tick >= 10 ) {
					if ( q.size() == 0 ) {
						shutdown = true;
						thread = null;
					}
					tick = 0;
				}
								
				sent = false;
				if ( q.size() > 0 ) {
					Map<String, String> m = q.peek();
					try {
						System.out.println("MailerDaemon Sending Email to " + m.get("to"));
						sendEmail(m);
						sent = true;
						System.out.println("Successfully sent email to " + m.get("to"));
					} catch (Exception e) {
						System.out.println("MailerDaemon CAN'T SENT EMAIL to " + m.get("to"));
						e.printStackTrace();
					}
					q.poll();
					if ( sent ) {
						System.out.println("Que size left: " + q.size());
					}
				}
			} catch ( InterruptedException e ) {}
		}		
		System.out.println("MailerDaemon stopped.");
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		thread = null;
	}
	
	public synchronized void addMessage(String to, String subject, String text) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("to", to);
		m.put("subject", subject);
		m.put("text", text);
		q.add(m);
	}
	
	private void sendEmail(Map<String, String> m) throws Exception {
		String mailTo = m.get("to");
		String mailSubject = m.get("subject");
		String mailText = m.get("text");
		
		try {
			EmailSenderUtil.send(mailTo, mailSubject, mailText, null);
		} catch ( javax.mail.AuthenticationFailedException e ) {
			e.printStackTrace();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	

}
