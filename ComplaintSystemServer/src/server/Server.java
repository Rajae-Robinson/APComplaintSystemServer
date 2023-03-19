package server;

import java.net.*;

import model.Complaint;
import model.Login;
import model.Query;
import model.Student;

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

                // read action from client
                String action = "";
				try {
					action = (String) input.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

                // handle action with switch statement
                try { 
	                switch (action) {
	                	case "authenticate":
	                		Login.authenticate(input, output);
	                		break;
	                		
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
	                    case "findComplaint":
	                    	int cID2 = Integer.parseInt((String) input.readObject());
	                    	output.writeObject(new Complaint().findComplaint(cID2));
	                    	break;
	                    case "deleteComplaint":
	                    	Complaint complaint2 = new Complaint();
	                    	int cID = Integer.parseInt((String) input.readObject());
	                    	complaint2.deleteComplaint(cID);
	                        output.writeObject(true);
	                        
	                    // Queries
	                    case "getQueries":
	                    	output.writeObject(new Query().readAll());
	                        break;
	                    case "createQuery":
	                    	Query query = (Query) input.readObject();
	                    	query.createQuery();
	                        output.writeObject(true);
	                        break;
	                    case "findQuery":
	                    	int qID = Integer.parseInt((String) input.readObject());
	                    	Query query2 = new Query().findQuery(qID);
	                    	output.writeObject(query2);
	                    	break;
	                    case "deleteQuery":
	                    	Query query3 = new Query();
	                    	int qID2 = Integer.parseInt((String) input.readObject());
	                    	query3.deleteQuery(qID2);
	                        output.writeObject(true);
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
}

