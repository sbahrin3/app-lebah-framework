package lebah.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import lebah.db.entity.Persistence;

/**
 * @author Shamsul Bahrin Abd Mutalib
 * @version 1.01
 */

public final class AppWatcher implements Runnable {
    
    private static AppWatcher instance = null;
    
    private int interval = 1; //in minute
    
	private Thread thread;
	private long totalMem, freeMem;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	private Persistence db = Persistence.db();
	
    private AppWatcher() {
        
    }
    
    public synchronized static AppWatcher getInstance() {
        if ( instance == null ) {
        	instance = new AppWatcher();
        	instance.start();
        }
        return instance;
    }
	
	public void run() {
		System.out.println("AppWatcher is running..");
		
		while ( thread != null ) {
			try {
				Thread.sleep(interval*60*60*1000);   //every how many minutes:
				totalMem = Runtime.getRuntime().totalMemory();
				freeMem = Runtime.getRuntime().freeMemory();
				//System.out.println("--- free memory = " + freeMem + " / " + totalMem);		
				
				//check point
				System.out.println("---- Check Point: " + df.format(new Date()));
				
			} catch ( Exception e ) {
				System.out.println("--- AppWatcher Error: ");
				e.printStackTrace();
			}			
		}
		
		System.out.println("AppWatcher is stopped!");
	}
	
	public void start() {
		thread = new Thread(this);
		thread.setName("Lebah Watcher.");
		thread.start();	
        System.out.println("Started!");
	}
	
	public void stop() {
		thread = null;
        System.out.println("Stopped!");
	}
    
    public static void main(String[] args) {
        AppWatcher w = AppWatcher.getInstance();
        w.start();
    }

    public long getFreeMem() {
        return freeMem;
    }

    public void setFreeMem(long freeMem) {
        this.freeMem = freeMem;
    }

    public long getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(long totalMem) {
        this.totalMem = totalMem;
    }
	
}
