import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleMail {

	public SimpleMail(){ 
		 
			Scanner user = new Scanner(System.in);
			Socket s = null;
			Scanner in= null;
			PrintWriter out =null;
			String command= "";
			String reply="";
			String message="";
			String response="";
			boolean connected= false;
			
			while(!connected){
				//Connecting to mail server
				while(parseReply(reply)!=220){		
					System.out.print("Mail Server:");
					command= user.nextLine();
					try {
						s = new Socket(command, 25); 
						in = new Scanner(s.getInputStream()); //from SMTP server
						out = new PrintWriter(s.getOutputStream(), true);
						reply = in.nextLine();
						System.out.println(reply);//SMTP server response
					}catch (IOException e) {
						System.err.println("ERROR: " + e);
					} 
				}	
				//HELO command
				try {
					String hostName = InetAddress.getLocalHost().getHostName(); 
					System.out.println("hostName: " + hostName); 
					
					message = "HELO " + hostName + "\r\n";
					System.out.println(message);
					out.print(message); 
					out.flush();
					reply = in.nextLine();
					System.out.println(reply);
					if(parseReply(reply)==250)
						connected =true;
				}catch (IOException e) {
					System.err.println("ERROR: " + e);
				}
			}
			
			
			/* Send MAIL FROM... */ 
			reply="";
			while(parseReply(reply)!=250){
				try{
					System.out.print("Email From:");
					command= user.nextLine();
					
					message = "MAIL FROM: " + command + "\r\n";
					out.print(message); 
					out.flush(); 
					reply = in.nextLine();
					System.out.println(reply); //print out server response
				}catch (Exception e) {
					System.err.println("ERROR: " + e);
				}
			}

			
			/* Send RCPT TO ... */
			boolean moreTo=false;
			do{
				reply="";
				while(parseReply(reply)!=250){
					try{
						System.out.print("Email To:");
						command= user.nextLine();
						
						message =  "RCPT TO: " + command + "\r\n";
						System.out.println(message);
						out.print(message); 
						out.flush(); 
						reply = in.nextLine();
						System.out.println(reply); //print out server response
					}catch (Exception e) {
						System.err.println("ERROR: " + e);
					}
				}
				System.out.print("Type y for another email:");
				response = user.nextLine();
				response = response.trim().toUpperCase();
				moreTo = false;
				if(response.equals("Y"))
					moreTo= true;
			}while(moreTo);
			

//			/* Send an CC*/ 
			System.out.println("Do you want to send to CC?");
			response = "";
			response = user.nextLine();
			if(response.trim().toUpperCase().equals("Y")){
				boolean moreCc=false;
				do{
					reply="";
					while(parseReply(reply)!=250){
						try{
							System.out.print("Email Cc:");
							command= user.nextLine();
							
							message =  "RCPT TO: " + command + "\r\n";
							System.out.println(message);
							out.print(message); 
							out.flush(); 
							reply = in.nextLine();
							System.out.println(reply); //print out server response
						}catch (Exception e) {
							System.err.println("ERROR: " + e);
						}
					}
					System.out.print("Type y for another email:");
					response= "";
					response = user.nextLine();
					response = response.trim().toUpperCase();
					moreCc = false;
					if(response.equals("Y"))
						moreCc= true;
				}while(moreCc);
			}
			

//			/* Send the DATA command*/ out.print("DATA" + "\r\n"); 
//			out.flush();
//			System.out.println(in.nextLine()); //print out server response
//
//			/* Send email Body*/
//			out.print("SUBJECT: this is a test"+ "\r\n");
//			out.flush(); 
//
//			out.print("CC: coupdebol@live.com" + "\r\n");
//			out.flush();
//
//			out.print("How are you?" + "\r\n");
//			out.flush();
//
//			out.print("See you at 1pm." + "\r\n");
//			out.flush();
//			 
//			out.print("." + "\r\n"); 
//			out.flush(); 
//
//			System.out.println(in.nextLine()); //print out server response
			
			try {
				user.close();
				in.close();
				s.close();
			} catch (IOException e) {
				System.err.println("ERROR: " + e);
			}
		
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to Simple Mail...");
		new SimpleMail();
		
	}
	
	/* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
    	String[] splitMessage = reply.split(" ");
    	int code = -1;
    	if( splitMessage.length > 0){
    		if(!splitMessage[0].equals(""))
    			code = Integer.parseInt(splitMessage[0]);
    	}
    	return code;
    }
	
	
}