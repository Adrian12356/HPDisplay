import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

public class DisplayChange implements ActionListener {
    private JButton jcomp1 = new JButton ("Upload");
    private JTextField jcomp2 = new JTextField (5);
    private JLabel jcomp3 = new JLabel ("IP Address");
    private JTextArea jcomp4 = new JTextArea (5, 5);
    private JLabel jcomp5 = new JLabel ("Message");
    
    JFrame dispchange;
    
    static DisplayChange DisplayChange = new DisplayChange();
 
    public DisplayChange() {
    	dispchange = new JFrame ("Display Changer");
        //construct components
        //adjust size and set layout
    	dispchange.getContentPane().setPreferredSize (new Dimension (259, 178));
    	dispchange.getContentPane().setLayout (null);

        //add components
    	dispchange.getContentPane().add (jcomp1);
    	dispchange.getContentPane().add (jcomp2);
    	dispchange.getContentPane().add (jcomp3);
    	dispchange.getContentPane().add (jcomp4);
    	dispchange.getContentPane().add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (10, 150, 100, 20);
        jcomp2.setBounds (5, 25, 245, 25);
        jcomp3.setBounds (15, 5, 100, 25);
        jcomp4.setBounds (5, 65, 240, 80);
        jcomp5.setBounds (20, 45, 100, 25);
        
        dispchange.pack();
        dispchange.setResizable(false);
        
        jcomp1.addActionListener(this);
    }


    public static void main (String[] args) {
        DisplayChange.dispchange.setVisible(true);
    }
	public void actionPerformed(ActionEvent e) {
		String ipaddr = jcomp2.getText();
		String mess = jcomp4.getText();
		try {
			upload(mess,ipaddr);
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(null, "Unable to Connect to host");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void upload(String message, String ip) throws UnknownHostException, IOException{
		Socket socket = new Socket(ip,9100);
		socket.setKeepAlive(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		int c = 0;
		String finmessage;
		finmessage = "@PJL RDYMSG DISPLAY=\"" + message + "\"";
		out.println(finmessage + "\n");
		socket.close();
		return;
	}
}
