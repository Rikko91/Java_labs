import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Controller implements Runnable {
	public boolean flag_of_entrance; // ������ ���� ��� �����
	public Director director;
	public Lock controllerLock;
	public Condition controllerFunds;
	
	
	public Controller(Director director_) {
		director=director_;
		controllerLock = new ReentrantLock();
		controllerFunds=controllerLock.newCondition();
	}
	
	
	public void test () {
		controllerLock.lock();
		try {
			if (director.State == MuseumState.Open) {//���� ����� ������
				flag_of_entrance=true; //���������� ��������� ������� �����
				System.out.println("Музей откыт - можно пускать людей");
			}
			else
				{// � ������ ������ ��������� ������ ������� �����
				flag_of_entrance=false;
				System.out.println("Музей закрты - людей пускать нельзя");				}
		}
		finally 
		{
			controllerLock.unlock();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{
			//System.out.println("POKA");
			controllerLock.lock();
			try {
				try {
					director.directorFunds.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				System.out.println("Privet");
			}
			finally 
			{
				controllerLock.unlock();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}
