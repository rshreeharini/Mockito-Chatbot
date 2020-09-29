package a2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Allow the Chatbot to be accessible over the network.  <br />
 * This class only handles one client at a time.  Multiple instances of ChatbotServer 
 * will be run on different ports with a port-based load balancer to handle multiple clients.
 * 
 * @author Shree Harini Ravichandran
 */
public class ChatbotServer{
	 /**
     * The instance of the {@link Chatbot}.
     */
	private Chatbot chatbot;
	/**
     * The instance of the {@link ServerSocket}.
     */
	private ServerSocket serversocket;
	/**
     * Constructor for ChatbotServer.
     *
     * @param chatbot The chatbot to use.
     * @param serversocket The pre-configured ServerSocket to use.
     */

	public ChatbotServer(Chatbot chatbot, ServerSocket serversocket) {
		this.chatbot = chatbot;
		this.serversocket = serversocket;
	}

    /**
     * Start the Chatbot server.  Does not return.
     */
	public void startServer() {
		while(true) handleOneClient();
	}

	 /**
     * Handle interaction with a single client.  See assignment description.
     */
	
	public void handleOneClient() {
		try {
			Socket s = serversocket.accept();
			BufferedReader clientIn = new BufferedReader(new InputStreamReader(s.getInputStream())); //get input from socket
			
			while(true) {
				String input = clientIn.readLine();	
				String botResponse = "";
				//System.out.println(input);
				
				if(input == null || input.equals("-1")){
					s.close();
					serversocket.close();
					break;
				}
				try{
					
					botResponse = this.chatbot.getResponse(input); 
				} 
				catch(AIException e) { 
				 //TODO Auto-generated catch block
					botResponse = "Got AIException:"+e.getMessage();
				}
			PrintWriter Finalout = new PrintWriter(s.getOutputStream(),true);
			//System.out.println(input);
			
			//System.out.println(botResponse);
			Finalout.println(botResponse);
			}
		}	
		catch(IOException e) {
			e.printStackTrace();
		}	
	}
}
	
	
