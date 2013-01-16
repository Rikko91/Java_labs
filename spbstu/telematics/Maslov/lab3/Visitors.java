import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Visitors implements Runnable {

	private int countVisitorsToMuseum;
	private int countOutVisitorsToMuseum;
	public PassEast museum;
	public PassWest museum2;
	public Director director;
	public Lock visitorsLock; //���������� ��� countVisitrosToMuseum
	public Lock visitorsOutLock; // ���������� ��� countOutVisitors
	public Condition visitorsOutFunds;
	
	public Visitors (int countVisitorsToMuseum) {
		this.countVisitorsToMuseum=countVisitorsToMuseum;
		visitorsLock = new ReentrantLock();
		visitorsOutLock = new ReentrantLock();
		visitorsOutFunds = visitorsOutLock.newCondition();
	}
	
	public int getCountVisitorsToMuseum() {
		visitorsLock.lock();
		try {
			return countVisitorsToMuseum;
		}
		finally {
			visitorsLock.unlock();
		}
	}

	public int getCountOutVisitorsToMuseum() {
		visitorsOutLock.lock();
		try {
			return countOutVisitorsToMuseum;
		}
		finally {
			visitorsOutLock.unlock();
		}
	}

	public void setCountVisitorsToMuseum(int countVisitorsToMuseum) {
		visitorsLock.lock();
		try {
			this.countVisitorsToMuseum = countVisitorsToMuseum;
		}
		finally {
			visitorsLock.unlock();
		}
	}

	public void setCountOutVisitorsToMuseum(int countOutVisitorsToMuseum) {
		visitorsOutLock.lock();
		try {
			this.countOutVisitorsToMuseum = countOutVisitorsToMuseum;
		}
		finally {
			visitorsOutLock.unlock();
		}
	}

	public void generateVisitors()  {
		visitorsLock.lock();
		try 
		{
			countVisitorsToMuseum = new Random().nextInt(45) + 1;
			System.out.println("Пришли посетители - " + countVisitorsToMuseum + " людей.");
			museum.setFlagOfTakeVisitors(true);
			//visitorsFunds.signalAll();
		}
		finally 
		{
			visitorsLock.unlock();
		}
	}
	
	public void generateOutVisitors () {
		visitorsOutLock.lock();
		try { 
			if ( museum.getInputPeople() > 5 ) {
			countOutVisitorsToMuseum = new Random().nextInt(museum.getInputPeople() / 3) +1;
			museum2.setTakeOutvisitors(true);
			}
			if (museum.getInputPeople() > 0 && museum.getInputPeople() < 5) {
				countOutVisitorsToMuseum = 1;
				museum2.setTakeOutvisitors(true);
			}
				
			visitorsOutFunds.signalAll();
		}
		finally {
			visitorsOutLock.unlock();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			generateVisitors();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			generateOutVisitors();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
