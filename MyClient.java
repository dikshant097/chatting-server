import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class MyClient implements ActionListener
{
	String clientName;
	JFrame f;
	JPanel jp,j1,j2;
	CardLayout c;
	JButton b1,b2,b3;
	JTextArea a1,a2,a3;
	JTextField f1;
	JLabel l;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	MyClient()
	{
			jp=new JPanel();
		c=new CardLayout();
		jp.setLayout(c);
		f=new JFrame();
		j1=new JPanel();
		l=new JLabel("Enter name to continue",0);
		j1.add(l);
		f1=new JTextField(20);
		f1.setText("");
		j1.add(f1);
		b1=new JButton("Continue");
		b1.addActionListener(this);
		j1.add(b1);
		//b1.setBounds(0, 0, 220, 30);
		jp.add(j1);
		f.add(jp);
		j2=new JPanel();
		l=new JLabel("Welcome",0);
		j2.add(l);
		a1=new JTextArea(5,25);
		a1.setEditable(false);
		j2.add(a1);
		a2=new JTextArea(5,25);
		a2.setEditable(false);
		j2.add(a2);
		a3=new JTextArea(5,25);
		j2.add(a3);
		b2=new JButton("Attach");
		b2.addActionListener(this);
		j2.add(b2);
		b3=new JButton("Send");
		b3.addActionListener(this);
		j2.add(b3);
		jp.add(j2);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(330,500);
		f.setVisible(true);
		
		//networking starts here
			try
			{
				s=new Socket("localhost",10);
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			My m=new My(clientName,din,this);
			Thread t1=new Thread(m);
			t1.start();
			}
			catch(Exception e)
			{System.out.println(e);}
		
	}
	public void startChatting() throws IOException
	{
		
		String s1=a3.getText();
		String s2=clientName+": "+s1+"\n";
		dout.writeUTF(s2);
		dout.flush();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			if(f1.getText().equals(""))
			{
				
			}
			else
			{
				clientName=f1.getText();
				c.next(jp);
			}
		}
		else if(e.getSource()==b2)
		{}
		else if(e.getSource()==b3)
		{
			
			try
			{
				startChatting();
				a3.setText("");
			}
			catch(Exception e1)
			{
				System.out.println(e1);
			}
		}
	}
	public static void main(String... s)
	{
		new MyClient();
	}
}
class My implements Runnable
{
	String name;
	DataInputStream din;
	MyClient c;
	My(String name,DataInputStream din,MyClient c)
	{
		this.name=name;
		this.din=din;
		this.c=c;
	}
	public void run()
	{
		String s2="";
		do
		{
			try
			{
				s2=din.readUTF();
				c.a1.append(s2);
				c.a2.setText(name);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}while(true);
	}
}