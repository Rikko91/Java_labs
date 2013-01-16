import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Controller implements Runnable {
	private boolean flag_of_entrance; 
	private boolean event = false;
	public Director director;
	public PassEast museum;
	public PassWest museum2;
	public MuseumState st;
	public Lock ControllerFlagLock; //для контроллерфлага
	public Condition ControllerFlagFunds;
	public Lock eventLock; // блокировка для event
	
	
	public Controller(Director director_, PassEast museum_, PassWest museum2_) {
		director=director_;
		museum = museum_;
		museum2 = museum2_;
		ControllerFlagLock = new ReentrantLock();
		ControllerFlagFunds = ControllerFlagLock.newCondition();
		eventLock = new ReentrantLock();
		
	}
	
	
	public boolean isEvent() {
		eventLock.lock();
		try {
			return event;
		}
		finally {
			eventLock.unlock();
		}
	}


	public void setEvent(boolean event) {
		eventLock.lock();
		try {
			this.event = event;
		}
		finally {
			eventLock.unlock();
		}
	}

	public boolean whoIsEvent () {
		eventLock.lock();
		try {
			if (event == true) {
				return true;
			}
			return false;	
		}
		finally {
			eventLock.unlock();
		}
	}
	

	public boolean isFlag_of_entrance() {
		ControllerFlagLock.lock();
		try {
			return flag_of_entrance;
		}
		finally {
			ControllerFlagLock.unlock();
		}
	}

	
	public void test () {
		ControllerFlagLock.lock();
		try {
			
			if (director.getState() == MuseumState.Open) {//если музей открыт
				flag_of_entrance=true; //контроллер разрешает пускать людей и выпускать людей 
				museum.setOpengate(true);
				
				if ( st != director.getState() ) 
				System.out.println("Контроллер открыл турникет");
				st = director.getState();
			}
			else
				{// в другом случае разрешает только уходить людям
				flag_of_entrance=false;
				if ( st != director.getState() ) 
				System.out.println("Контроллер закрыл турникет");
				st = director.getState();
				}
			ControllerFlagFunds.signalAll();
		}
		finally 
		{
			ControllerFlagLock.unlock();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{	
			director.stateLock.lock();
			try {
				try {
					director.stateFunds.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				test();		
			}
			finally {
				director.stateLock.unlock();
			}
			
		}
	}
	
	
	
}
