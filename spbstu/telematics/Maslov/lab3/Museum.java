import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Museum implements Runnable {
	
	public int people_in_museum;
	public int people_output;
	public Director director; // ��������
	public Controller controller; //����������
	public Visitors visitors;
	public boolean entrance; // ���� �� �����
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
	

	public boolean permitPeople() throws InterruptedException { // ������� ����������� ���� ������ ����
		controller.controllerLock.lock();
		try 
		{
			controller.controllerFunds.await();
			if (controller.flag_of_entrance == true ) //���� ���������� ��������� ��� ����� 
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
			if (permitPeople() == true  ) // ���� ���� ��������
			{
				System.out.println("����� ������ - ��������� ��������");
				int count = visitors.countVisitorsToMuseum;
				while (visitors.countVisitorsToMuseum != 0 ) {
					people_in_museum++;
					visitors.countVisitorsToMuseum--;
					//System.out.println("� ����� ����� ���� �������");
					//System.out.println("����� � ����� - " + people_in_museum);
					//System.out.println("�����, ������� �� ����� - " + visitors.countVisitorsToMuseum);
				}
				System.out.println("� ����� ����� - " + count + "�����");
				System.out.println("����� � ����� ����� - " + people_in_museum );
			}
			else System.out.println("����� ������ - ��������� �� �������� !");
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
