import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




public class PassWest implements Runnable {
	private int people_in_museum;
	private boolean takeOutvisitors = false;
	public Director director; // �����njh
	public Controller controller; //����������
	public Visitors visitors;
	public PassEast museum;
	public Lock takeOutvisitorsLock; // ���������� ��� takeOutvisitors

	
	

	public void setTakeOutvisitors(boolean takeOutvisitors) {
		takeOutvisitorsLock.lock();
		try {
			this.takeOutvisitors = takeOutvisitors;
		}
		finally {
			takeOutvisitorsLock.unlock();
		}
	}


	public PassWest ( Visitors visitors_, Director director_,int input, PassEast museum) {
		director = director_;
		visitors = visitors_;
		people_in_museum=input;
		this.museum = museum;
		takeOutvisitorsLock = new ReentrantLock();			
	}
	
	
	
	public void takeOutVisitors () throws InterruptedException {	// ������� ����������� �� �����
		//director.getDirectorLock().lock();
	//	try {
				people_in_museum = visitors.getCountOutVisitorsToMuseum();
				
				while (people_in_museum != 0 ) {
					people_in_museum--;
					
				}
				System.out.println("Через WEST вышло - " + visitors.getCountOutVisitorsToMuseum() + " людей, осталось " + ( museum.getInputPeople() - visitors.getCountOutVisitorsToMuseum()) ) ;				
				int count = museum.getInputPeople() - visitors.getCountOutVisitorsToMuseum();
				museum.setPeople_in_museum(count); 
	//	}
		//finally {
		//	director.getDirectorLock().unlock();
		//}
	}
	

	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {	
			visitors.visitorsOutLock.lock();
			try {
				try {
					visitors.visitorsOutFunds.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					if (museum.getInputPeople() != 0)
					takeOutVisitors();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			finally {
				visitors.visitorsOutLock.unlock();
			}
			
		}
	}
	
}
