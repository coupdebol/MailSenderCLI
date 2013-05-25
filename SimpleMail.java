import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleMail {

	public SimpleMail() { 
		try { 
			Socket s = new Socket("mail.swin.edu.au", 25); 
			Scanner in = new Scanner(s.getInputStream()); //from SMTP server
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);

			System.out.println(in.nextLine());//SMTP server response

			String hostName = InetAddress.getLocalHost().getHostName(); 
			System.out.println("hostName: " + hostName); 
			
			String message = "HELO " + hostName + "\r\n";
			System.out.println(message);
			out.print(message); 
			out.flush(); 
			System.out.println(in.nextLine());
			

			/* Send MAIL FROM... */ 
			message = "MAIL FROM: " + "971801x@student.swin.edu.au" + "\r\n";
			out.print(message); 
			out.flush(); 
			System.out.println(in.nextLine()); //print out server response


			/* Send RCPT TO ... */
			message =  "RCPT TO: " + "971801x@student.swin.edu.au" + "\r\n";
			System.out.println(message);
			out.print(message); 
			out.flush(); 
			System.out.println(in.nextLine()); //print out server response

			/* Send an CC*/ 
			message =  "RCPT TO: " + "coupdebol@live.com" + "\r\n";
			System.out.println(message);
			out.print(message);
			out.flush(); 
			System.out.println(in.nextLine()); //print out server response



			/* Send the DATA command*/ out.print("DATA" + "\r\n"); 
			out.flush();
			System.out.println(in.nextLine()); //print out server response

			/* Send email Body*/
			out.print("SUBJECT: this is a test"+ "\r\n");
			out.flush(); 

			out.print("CC: coupdebol@live.com" + "\r\n");
			out.flush();

			out.print("How are you?" + "\r\n");
			out.flush();

			out.print("See you at 1pm." + "\r\n");
			out.flush();
			 
			out.print("." + "\r\n"); 
			out.flush(); 

			System.out.println(in.nextLine()); //print out server response


			s.close();
		}catch (IOException e) {
			System.err.println("ERROR: " + e);
		} 
	}
	
	public static void main(String[] args) {
		new SimpleMail();
	}
}