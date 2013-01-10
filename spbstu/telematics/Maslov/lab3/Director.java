import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum MuseumState {
	Open,
	Close;
}

public class Director implements Runnable{
	public MuseumState State;
	public Lock directorLock;
	public Condition directorFunds;
	
	public Director (MuseumState State) {
		super();
		this.State = State;
		directorLock = new ReentrantLock();
		directorFunds = directorLock.newCondition();
		
	}
	
	
	public Lock getDirectorLock() {
		return directorLock;
	}




	public Condition getDirectorFunds() {
		return directorFunds;
	}

	
	public void RandomState (MuseumState value) {	
		int number = new Random().nextInt(2);
		directorLock.lock();
		try 
		{
			if (number == 1) 
			{   
				State = MuseumState.Open;
				System.out.println("Музей открыт");
			}
			else
			{
				State = MuseumState.Close;
				System.out.println("Музей закрты");
			}
		
			directorFunds.signalAll();
		}
		finally 
		{
			directorLock.unlock();
		}
	}


	
	
	
	@Override
	public void run() {
		 //TODO Auto-generated method stub
		while (true) 
		{
			try 
			{
				RandomState(State);
				//museumFunds.signalAll();
				Thread.sleep(1000);
			}
			catch (InterruptedException e) 
			{e.printStackTrace();}
			
		}// endwhile
			
		
	}
	
	
}
