package server;

import java.net.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import factories.SessionFactoryBuilder;
import models.Complaint;
import models.Login;
import models.Student;
import models.QueryModel;

import java.io.*;

public class Server {
	private static final int PORT = 8888;
	
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        try {
        	while (true) {
        		System.out.println("Server listening on port " + PORT + "...");
        		Socket clientSocket = serverSocket.accept();
        		// spawn a new thread to handle the client request
        		ClientHandler handler = new ClientHandler(clientSocket);
        		new Thread(handler).start();
        	}        	
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

                // authenticate
                boolean authenticated = authenticate(input);
                if (!authenticated) {
                    output.write("Authentication failed".getBytes());
                    clientSocket.close();
                    return;
                }
                // read action from client
                byte[] buffer = new byte[1024];
                int bytesRead = input.read(buffer);
                String action = new String(buffer, 0, bytesRead);

                // handle action with switch statement
                try { 
	                switch (action) {
	                	// Students
	                    case "getStudents":
	                        output.writeObject(new Student().readAll());
	                        break;
	                        
	                    // Complaints
	                    case "getComplaints":
	                        output.writeObject(new Complaint().readAll());
	                        break;
	                        
	                    case "createComplaint":
	                    	Complaint complaint = (Complaint) input.readObject();
	                    	complaint.createComplaint();
	                        output.writeObject(true);
	                        break;
	                        
	                    // Queries
	                    case "getQueries":
	                    	output.writeObject(new Complaint().readAll());
	                        break;
	                    case "createQuery":
	                    	QueryModel query = (QueryModel) input.readObject();
	                    	query.createQuery();
	                        output.writeObject(true);
	                        break;
	                    default:
	                        output.writeObject("Unknown action: " + action);
	                }
                } catch (ClassNotFoundException ex) { 
					ex.printStackTrace();
				}

                // close the connection
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static boolean authenticate(InputStream input) throws IOException {
    	byte[] buffer = new byte[1024];
        int bytesRead = input.read(buffer);
        String id = new String(buffer, 0, bytesRead).trim();

        bytesRead = input.read(buffer);
        String password = new String(buffer, 0, bytesRead).trim();
        
	    Session session = null;
	    try {
	        session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
	        session.beginTransaction();
	        Query<Login> query = session.createQuery("from Login where id = :id and password = :password", Login.class);
	        query.setParameter("id", id);
	        query.setParameter("password", password);
	        Login login = query.uniqueResult();
	        session.getTransaction().commit();
	        return login != null;
	    } catch (HibernateException e) {
	        if (session != null && session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    }
	}
}

