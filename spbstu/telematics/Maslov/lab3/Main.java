
public class Main {
	public static void main(String[] args) {
		Director director = new Director(MuseumState.Open);
		Controller controller = new Controller(director);
		Museum museum = new Museum(controller,director,0,0);
		
		new Thread (director,"Director").start();
	
		new Thread(controller,"Controller").start();
	
		new Thread(museum,"Museum").start();
		
	}
}
