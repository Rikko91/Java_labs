import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Museum implements Runnable {
	
	public int people_in_museum;
	public int people_output;
	public Director director; // директор
	public Controller controller; //контроллер
	public boolean flag_on_exit; // флаг на выход
	public Lock museumLock;
	public Condition museumFunds;
	
	
	
	public Museum (Controller controller_,Director director_,int input,int output) {
	controller = controller_;
	director = director_;
	people_in_museum=input;
	people_output=output;
	museumLock = new ReentrantLock();
	museumFunds = museumLock.newCondition();
	}
	
	public int getOutputPeople() {
		return people_output;
	}
	
	public int getInputPeople() {
		return people_in_museum;
	}
	
	public void countPeople() {
		museumLock.lock();
		
		try {
			if (controller.flag_of_entrance == true ) //открыт вход, начинаем считать людей, сколько зашло
			{ 
				people_in_museum++;
				System.out.println("Пришел человек");
			}
		
			else { 
				people_output++;
				people_in_museum--;
				System.out.println("Ушел человек");
			}
		}
		finally {
			museumLock.unlock();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{
			try {
				director.directorFunds.await();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			countPeople();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//endwhile
	}
		
	
	
}
