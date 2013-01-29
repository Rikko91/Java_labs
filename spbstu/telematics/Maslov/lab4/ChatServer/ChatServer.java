 import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 

public class ChatServer  {
	private ServerSocket serverSocket; 
	private int port; // порт 
	private Lock UsersLock = new ReentrantLock();
	private Lock ServerLock = new ReentrantLock();
	ArrayList<ServerThread> q = new ArrayList<ServerThread>();
	ArrayList<String> UsersNick = new ArrayList<String>();
	
	
	
	public ChatServer(int port) throws IOException {
		serverSocket = new ServerSocket(port); // создаем сервер-сокет
		this.port = port; // сохраняем порт.
	}
 

	void run() throws IOException {
		while (true) { 
			
			Socket s = serverSocket.accept();
			if (s != null){ 
				try {
					final ServerThread processor = new ServerThread(s); // создаем новую нить для обработки клиента
					final Thread thread = new Thread(processor); 
					
					thread.start(); 
					q.add(processor);
				}
				catch (IOException ignored) {} 
			}
		}
	}
 
 
 

	public static void main(String[] args) throws IOException {
		new ChatServer(45006).run(); 
	}
 

	private class ServerThread implements Runnable {
		Socket s; 
		ObjectInputStream inp; // для чтения
		ObjectOutputStream out; // для отправки
		String nickname;
		SendObject sendObject; // сообщения
		boolean exit = true; // на выход

		public void addUsers( String Nick) {
			UsersLock.lock();
			try {
				UsersNick.add(Nick);
			}
			finally {UsersLock.unlock();}
			}
		
		public  boolean  UsersNameIsBusy (String Nick) {
			UsersLock.lock();
			try {
				for (String Nickname : UsersNick) {
					if ( Nickname.equals(Nick) ) {
						return true;
					}
				}
			}
			finally {UsersLock.unlock();}
			return false;
		}
		
		public  boolean removeUsersName (String Nick) {
			UsersLock.lock();
			try {
				for (String Nickname : UsersNick) {
					if ( Nickname.equals(Nick) ) {
						UsersNick.remove(Nick);
						return true;
					}
				}
			} 
			finally {UsersLock.unlock();}
			return false;
		}
		public boolean removeFromS (String Nick) {
			ServerLock.lock();
			try {
				for (ServerThread sp : q) {
					if (sp.nickname.equals(Nick)) {
						q.remove(sp);
						return true;
					}
				}
			}
			finally {ServerLock.unlock();}
			return false;
		}
		public  boolean removeFromSbysocket (Socket s) {
			ServerLock.lock();
			try {
				for (ServerThread sp : q) {
					if (sp.s.equals(s)) {
						q.remove(sp);
						return true;
					}
				}
			}
			finally {ServerLock.unlock();}
			return false;
		}
		
		ServerThread(Socket socketParam) throws IOException {
			s = socketParam;
			sendObject = new SendObject("какаха", 1);
			inp = new ObjectInputStream(s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
			}
 

		@SuppressWarnings("deprecation")
		public void run() {
			
			try {
				try {
					sendObject = (SendObject) inp.readObject();
					nickname = sendObject.getMessage();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (UsersNameIsBusy(nickname) == true) { // если существует пользователь
				System.out.println("User with Nickname [ " + nickname + " ] is already exists, sorry!" );
				try {
					sendObject.setMessage("Nickname [ " + nickname + " ] is already exists, sorry!");
					sendObject.setType(2);
					out.writeObject(sendObject);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			removeFromSbysocket(this.s);
			return;
				
			}
			
				
				addUsers(nickname);//добавляем в список
				sendObject.setMessage("Users with Nick [ " + nickname + " ] connected!!");
				sendObject.setType(1); 
				
				try {//оповещаем всех 
					sendToAll(sendObject);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
//			System.out.println("Список имен:");
//			for (String sp : UsersNick) {
//				System.out.println(sp);
//			}
				
			//приветсвуем нового юзера
			try {
				sendObject.setMessage("Hellow, " + nickname);
				sendObject.setType(1);
				out.writeObject(sendObject);
				out.flush();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//пока все хорошо 
			while (exit) { 
				String line = null;
				try {
					try {
						sendObject = (SendObject) inp.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					line = sendObject.getMessage();
				
				} catch (IOException e) {
					close();
				}
 
			if (line == null) {
				close();
				}
				else { 
					if (line.equals("user:disconnect")) {
						System.out.println("attempt to disconnect " + nickname);
						if (removeUsersName(nickname) == true && removeFromS(nickname) == true ) {
							System.out.println("Attempt is good");
							try {
								sendObject.setMessage("Users [ " + nickname + " ] disconnected!");
								sendObject.setType(1);
								sendToAll(sendObject);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
							exit = false;
						}
						else System.out.println("Attempt failed");
												
						
					}
					else { 
						line = nickname + " says: " + line;
						ServerLock.lock(); 
						try {
							for (ServerThread sp:q) {
								sendObject.setMessage(line);
								sendObject.setType(1);
								sp.send(sendObject);
							}
						}
						finally {ServerLock.unlock();}
					}
				}
			}
		}
		public  void sendToAll (SendObject sendObj) throws IOException {
			ServerLock.lock();
			try {
				for (ServerThread sp : q) {
					if (sp.s != this.s) {
						System.out.println(s.toString());
						sp.out.writeObject(sendObj);
						sp.out.flush();
					}
				}
			}
			finally {ServerLock.unlock();}
		}
 

		public void send(SendObject sendObj) {
			try {
				out.writeObject(sendObj);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
 

		public  void close() {
			ServerLock.lock();
			try {
				q.remove(this);
			}
			finally {ServerLock.unlock();}
			if (!s.isClosed()) {
				try {
					s.close(); 
				} catch (IOException ignored) {}
			}
		}
 

	}
	
	}