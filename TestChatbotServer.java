// @author Shree Harini Ravichandran
package a2;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestChatbotServer {

	@Mock
	public Chatbot mockChatbot; 
	
	@Mock
	public ServerSocket mockServerSocket;
	
	@Mock
	public Socket mockSocket; 
	
	public ChatbotServer myServer;
	
	@Before
	public void setUp(){
		myServer = new ChatbotServer(mockChatbot, mockServerSocket);
	}
	
	
	
	@Test
		public void testhandleOneClient() throws Exception{	 //tests a full communication
		try{
			 when(mockServerSocket.accept()).thenReturn(mockSocket);		 
		 }
		 catch(IOException e) {
				e.printStackTrace();
		 }
		 InputStream s = new ByteArrayInputStream("Hi".getBytes()); //Test should use the input stream I'm passing
		 when(mockSocket.getInputStream()).thenReturn(s);//server gets input from socket
		// System.out.println(s.toString());
		 when(mockChatbot.getResponse("Hi")).thenReturn("I am a Chatbot!");
		 //when(mockChatbot.getResponse(s.toString())).thenReturn("ChatbotResponse");	 
		 OutputStream myOutputStream = new ByteArrayOutputStream();
		 when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
		 
		 myServer.handleOneClient();
		 //System.out.println(myOutputStream.toString());
		 //assertEquals("ChatbotResponseHey\n", myOutputStream.toString());
		 assertEquals("I am a Chatbot!\n", myOutputStream.toString());
	 }
	 
	
	@Test
	    public void testIOException() throws Exception
	    {
	        when(mockServerSocket.accept()).thenReturn(mockSocket);
	        InputStream s = new ByteArrayInputStream("Hi".getBytes());
	        when(mockSocket.getInputStream()).thenThrow(new IOException()).thenReturn(s);
	        
	        when(mockChatbot.getResponse("Hi")).thenReturn("This is an IOException");

	        OutputStream myOutputStream = new ByteArrayOutputStream();
	        when(mockSocket.getOutputStream()).thenReturn(myOutputStream);

	        myServer.handleOneClient();
	        myServer.handleOneClient();

	        //System.out.println(myOutputStream.toString());
	        assertEquals("This is an IOException\n", myOutputStream.toString());
	    }
	
	
	@Test
	    public void testAIException() throws Exception
	    {
	        when(mockServerSocket.accept()).thenReturn(mockSocket);
	        InputStream s = new ByteArrayInputStream("Hi".getBytes());
	        when(mockSocket.getInputStream()).thenReturn(s);
	        
	        when(mockChatbot.getResponse("Hi")).thenThrow(new AIException("This is an AIException"));

	        OutputStream myOutputStream = new ByteArrayOutputStream();
	        when(mockSocket.getOutputStream()).thenReturn(myOutputStream);

	        myServer.handleOneClient();

	        assertEquals("Got AIException:"+"This is an AIException\n", myOutputStream.toString());
	    }
   
	
	@Test
	    public void testNoChatbotResponse() throws Exception
	    {
	        when(mockServerSocket.accept()).thenReturn(mockSocket);
	        InputStream s = new ByteArrayInputStream("Hi".getBytes());
	        when(mockSocket.getInputStream()).thenReturn(s);
	        //when(mockChatbot.getResponse("Hi")).thenReturn("");
	        when(mockChatbot.getResponse("Hi")).thenReturn(null); 

	        OutputStream myOutputStream = new ByteArrayOutputStream();
	        when(mockSocket.getOutputStream()).thenReturn(myOutputStream);

	        myServer.handleOneClient();

	        //assertEquals("\n", myOutputStream.toString());
	        assertEquals("null\n", myOutputStream.toString());
	    }


} 
