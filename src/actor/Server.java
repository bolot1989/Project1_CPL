package actor;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import actor.MSG.AuthRequest;
import actor.MSG.LoginRequest;
//import cpl.travelers.auth.AuthComm;
import actor.MSG.SMS_Start;

public class Server implements Runnable {
	//private final static Logger LOG = Logger.getLogger(Server.class);
	private ServerSocket serverSocket;
	private Socket socket;
	private ExecutorService service = Executors.newCachedThreadPool();
	private Queue<Message> queue = new PriorityQueue<Message>();
	private String salt;
	private Random random = new Random(System.nanoTime()+this.hashCode());
	private MessageDigest digestAlgo;

	
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Server started listening on port 5000");
	}
	
	public void sendToQueue(Message message) {
		queue.add(message);
	}
	
	public Message getFromQueue() {
		return (Message)queue.remove();
	}
	
	public void receiveMessage() {
		try {
			ObjectInputStream in =  new ObjectInputStream(socket.getInputStream());
			sendToQueue((Message)in.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			//sendToQueue((Message)in.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server server = new Server(5000);
		Thread thread = new Thread(server);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Staring Server");
		while(true) {
			try {
				socket = serverSocket.accept();
				System.out.println("socket accepting");
				service.submit(new LoginCredentials());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private class LoginCredentials implements Runnable {
		private String salt;
		private MessageDigest digestAlgo;
		
		public LoginCredentials() {
						
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//receiveMessage();
			//SMS_Start sms = (SMS_Start)getFromQueue();
			//sms.equals("Message Notification is listening")
			DataInputStream in = null;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String sms = "";
			try {
				sms = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(socket.getRemoteSocketAddress().toString());
			System.out.println(sms);
			
//			if(sms.equals("Message Notification is listening")) {
//				System.out.println(socket.getRemoteSocketAddress().toString());
//				System.out.println("Sms notification service started");
//			}
//			else {
//				try {
//					System.out.println("Sms notification service failed");
//					socket.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			
//			LoginRequest m = (LoginRequest)getFromQueue();
//			System.out.println(m.username.toString());
//			if (!m.username.equals("Vaka")) {
//				System.out.println("Sending wrong username");
//				sendToQueue(new MSG.ResultResponse("Invalid username"));
//			}
//			else {
//				salt = ""+random.nextInt();
//				System.out.println(salt);
//				sendToQueue(new MSG.ParamsResponse(salt));
//				AuthRequest m2 = (AuthRequest)getFromQueue();
//				byte[] digest = digestAlgo.digest((salt+"mypassword").getBytes());
//				boolean success = Arrays.equals(digest, m2.digest);
//				if (success) {
//					sendToQueue(new MSG.ResultResponse("Connected"));
//				} else {
//					sendToQueue(new MSG.ResultResponse("Invalid password"));
//				}
//			}
		}
		
	}

}
