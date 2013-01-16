import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PassEast implements Runnable {
	
	private int people_in_museum;
	private boolean flagOfTakeVisitors = false;
	public boolean opengate = false;
	public Director director; 
	public Controller controller; 
	public Visitors visitors;
	public boolean entrance; 
	public Lock flagOfTakeVisitorsLock; // ���������� ��� flagoftakevisitors
	public Lock peopleInMuseumLock; //���������� ��� people_in_museum
	public Lock opengateLock;
	
	public PassEast ( Visitors visitors_, Director director_,int input) {
	director = director_;
	visitors = visitors_;
	people_in_museum=input;
	flagOfTakeVisitorsLock = new ReentrantLock();
	peopleInMuseumLock = new ReentrantLock();
	opengateLock = new ReentrantLock();
	}
		
	public  int getInputPeople() {
		peopleInMuseumLock.lock();
		try {
			return people_in_museum;
		}
		finally {
			peopleInMuseumLock.unlock();
		}
	}
	

	public void setPeople_in_museum(int people_in_museum) {
		peopleInMuseumLock.lock();
		try {
			this.people_in_museum = people_in_museum;
		}
		finally {
			peopleInMuseumLock.unlock();
		}
	}

	public  void setFlagOfTakeVisitors(boolean flagOfTakeVisitors) {
		flagOfTakeVisitorsLock.lock();
		try {
			this.flagOfTakeVisitors = flagOfTakeVisitors;
		}
		finally {
			flagOfTakeVisitorsLock.unlock();
		}
	}

	public boolean isFlagOfTakeVisitors() {
		flagOfTakeVisitorsLock.lock();
		try {
			return flagOfTakeVisitors;
		}
		finally {
			flagOfTakeVisitorsLock.unlock();
		}
	}
	public boolean WhoIsFlagOfTakeVisitors () {
		flagOfTakeVisitorsLock.lock();
		try {
			if ( flagOfTakeVisitors == true ) {
				return true;
			}
			return false;
		}
		finally {
			flagOfTakeVisitorsLock.unlock();
		}
	}

	public void setOpengate(boolean opengate) {
		opengateLock.lock();
		try {
			this.opengate = opengate;
		}
		finally {
			opengateLock.unlock();
		}
	}

	public boolean isOpengate () {
		opengateLock.lock();
		try {
			if (opengate == true) {
				return true;
			}
			return false;
		}
		finally {
			opengateLock.unlock();
		}
	}
	public boolean permitPeople() throws InterruptedException { // ������� ����������� ���� ������ ����
		controller.ControllerFlagLock.lock();
		try 
		{
			if (controller.isFlag_of_entrance() == true ) //���� ���������� ��������� ��� ����� 
			{
				
				return true;
			}
			
		}
		finally 
		{
			controller.ControllerFlagLock.unlock();
		}
		return false;
	}
	
	public void takeVisitors () throws InterruptedException {	
		//director.getDirectorLock().lock();
	//	try {
		peopleInMuseumLock.lock();
		try {
			if (permitPeople() == true  ) // ���� ���� ��������
			{
				int count = visitors.getCountVisitorsToMuseum();
				
				people_in_museum = people_in_museum + count;
				visitors.setCountVisitorsToMuseum(0);
		
				System.out.println("Через EAST зашло - " + count + " людей");
				System.out.println("Всего в музее людей - " + people_in_museum );
			}
		}
		finally {
			peopleInMuseumLock.unlock();
		}

		
	}
	
	public void listenController () throws InterruptedException {
		controller.ControllerFlagLock.lock();
		try {
			
		if (controller.isFlag_of_entrance() == true )
			System.out.println("Турникет EAST открыт(контроллер)");
		else 
			System.out.println("Турникет EAST закрыт(контроллер)");
		}
		finally {
			controller.ControllerFlagLock.unlock();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{
			
			if ( isOpengate() == true ) {
				try {
					listenController();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				opengateLock.lock();
				try {
					opengate = false;
				}
				finally {
					opengateLock.unlock();
				}
			}
			if ( WhoIsFlagOfTakeVisitors() == true ) {
					try {
						takeVisitors();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					flagOfTakeVisitorsLock.lock();
					try {
						flagOfTakeVisitors = false;
					}
					finally {
						flagOfTakeVisitorsLock.unlock();
					}
			}
		}//endwhile
	}
		
	
	
}
