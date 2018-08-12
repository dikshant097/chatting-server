import java.io.*;
import java.net.*;
import java.util.*;
class MyServer
{
	ArrayList al=new ArrayList();
	ServerSocket ss;
	Socket s;
	MyServer()
	{
		try
		{
			ss=new ServerSocket(10);
			while(true)
			{
				s=ss.accept();
				al.add(s);
				Runnable r=new MyThread(s,al);
				Thread t=new Thread(r);
				t.start();
			}
		}
		catch(Exception e)
		{System.out.println(e);}
		
	}
	public static void main(String... s)
	{
		new MyServer();
	}
}
class MyThread implements Runnable
{
	Socket s;
	ArrayList al;
	MyThread(Socket s,ArrayList al)
	{
		this.s=s;
		this.al=al;
	}
	public void run()
	{
		String s1;
		try
		{
			DataInputStream din=new DataInputStream(s.getInputStream());
			s1=din.readUTF();
			System.out.println(s1);
			Iterator i=al.iterator();
			while(i.hasNext())
			{
				try
				{
					Socket sc=(Socket)i.next();
					DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
					dout.writeUTF(s1);
					dout.flush();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		catch(Exception e)
				{
					System.out.println(e);
				}
		
	}
}