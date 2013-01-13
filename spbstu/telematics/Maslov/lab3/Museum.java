import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Museum implements Runnable {
	
	public int people_in_museum;
	public int people_output;
	public Director director; // директор
	public Controller controller; //контроллер
	public Visitors visitors;
	public boolean entrance; // флаг на выход
	public Lock museumLock;
	public Condition museumFunds;
	
	
	
	public Museum (Controller controller_,Director director_,Visitors visitors_,int input,int output) {
	controller = controller_;
	director = director_;
	visitors = visitors_;
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
	

	public boolean permitPeople() throws InterruptedException { // слушаем контроллера если пришли люди
		controller.controllerLock.lock();
		try 
		{
			controller.controllerFunds.await();
			if (controller.flag_of_entrance == true ) //если контроллер разрешает нам войти 
			{
				entrance = true;
				return true;
			}
			
		}
		finally 
		{
			controller.controllerLock.unlock();
		}
		entrance = false;
		return false;
	}
	
	public void takeVisitors () throws InterruptedException {
		visitors.visitorsLock.lock();
		try {
			visitors.visitorsFunds.await();
			if (permitPeople() == true  ) // если вход разрешен
			{
				System.out.println("Музей открыт - турникеты работают");
				int count = visitors.countVisitorsToMuseum;
				while (visitors.countVisitorsToMuseum != 0 ) {
					people_in_museum++;
					visitors.countVisitorsToMuseum--;
					//System.out.println("В музей зашел один человек");
					//System.out.println("Всего в музее - " + people_in_museum);
					//System.out.println("Людей, стоящих на входе - " + visitors.countVisitorsToMuseum);
				}
				System.out.println("В музей зашло - " + count + "Людей");
				System.out.println("Всего в музее людей - " + people_in_museum );
			}
			else System.out.println("Музей закрыт - турникеты не работают !");
		}
		finally 
		{
			visitors.visitorsLock.unlock();
		}
	
	}
	
	public void listenController () throws InterruptedException {
		controller.controllerLock.lock();
		try 
		{
			controller.controllerFunds.await();
		
		
			
		}
		finally
		{
			controller.controllerLock.unlock();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{
				try {
					takeVisitors();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			
		}//endwhile
	}
		
	
	
}
