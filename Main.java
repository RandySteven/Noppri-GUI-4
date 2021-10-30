import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener{

	JPanel northPanel, centerPanel, southPanel;
	void components() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		north();
		center();
		south();
	}
	
	JLabel lblTitle;
	void north() {
		lblTitle = new JLabel("Form Registrasi");
		northPanel.add(lblTitle);
	}
	
	JButton buttons[] = {
			new JButton("Register"),
			new JButton("Reset")
	};
	
	void south() {
		southPanel.setLayout(new FlowLayout());
		for(int i = 0 ; i < buttons.length ; i++) {
			southPanel.add(buttons[i]);
		}
		buttons[0].addActionListener(this);
		
		buttons[1].addActionListener(this);
		
	}
	
//	JLabel lblFullName, lblEmail, lblPassword, lblAge, lblGender, lblAddress, lblMember;
	JTextField txtFullName, txtEmail, txtAge;
	JPasswordField txtPassword;
	JTextArea txtAddress;
	JRadioButton rbMale, rbFemale;
	JComboBox cbMember;
	
	String members[] = {
			"Silver", "Gold", "Diamond", "Platinum"
	};
	
	JLabel lables[] = {
			new JLabel("Full Name"),
			new JLabel("Email"),
			new JLabel("Password"),
			new JLabel("Age"),
			new JLabel("Gender"),
			new JLabel("Address"),
			new JLabel("Member")
	};
	
	ButtonGroup bgGender;
	void center() {
		centerPanel.setLayout(new GridLayout(7, 2, 20, 20));
		
		txtFullName = new JTextField();
		txtEmail = new JTextField();
		txtAge = new JTextField();
		txtPassword = new JPasswordField();
		txtAddress = new JTextArea();
		bgGender = new ButtonGroup();
		
		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(new FlowLayout());
		rbMale = new JRadioButton("Male");
		rbFemale = new JRadioButton("Female");
		genderPanel.add(rbMale);
		genderPanel.add(rbFemale);
		bgGender.add(rbMale);
		bgGender.add(rbFemale);
		
		cbMember = new JComboBox(members);
		
		centerPanel.add(lables[0]);
		centerPanel.add(txtFullName);
		centerPanel.add(lables[1]);
		centerPanel.add(txtEmail);
		centerPanel.add(lables[2]);
		centerPanel.add(txtPassword);
		centerPanel.add(lables[3]);
		centerPanel.add(txtAge);
		centerPanel.add(lables[4]);
		centerPanel.add(genderPanel);
		centerPanel.add(lables[5]);
		centerPanel.add(txtAddress);
		centerPanel.add(lables[6]);
		centerPanel.add(cbMember);
	}
	
	Connection conn;
	public Main() {
		conn = sqlConnector.connection();
		setVisible(true);
		setLayout(new BorderLayout());
		components();
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Register")) {
			String fullName, email, age, password, address, gender, member;
			fullName = txtFullName.getText().toString();
			email = txtEmail.getText().toString();
			age = txtAge.getText().toString();
			password = txtPassword.getText().toString();
			address = txtAddress.getText().toString();
			if(rbMale.isSelected()) {
				gender = "Male";
			}else {
				gender = "Female";
			}
			member = cbMember.getSelectedItem().toString();
			
			try {
				String query = "INSERT INTO users (full_name, email, password, age, gender, address, member) "
						+ "VALUES('"+fullName+"', '"+email+"', '"+password+"', "+age+", '"+gender+"', '"+address+"', '"+member+"')";
				/**
				 * 1. Siapin dulu String yang isinya query
				 * 2. Buat variable Statement 
				 * 3. Excute query
				 */
				Statement st = conn.createStatement();
				st.execute(query);
				JOptionPane.showMessageDialog(null, "Irashaimasen Goshujin sama " + fullName + "\n"
						+ "Email : " + email + "\n"
						+ "Password : " + password + "\n"
						+ "Gender : " + gender + "\n"
						+ "Member : " + member + "\n");
				st.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getActionCommand().equals("Reset")) {
			txtFullName.setText(null);
			txtPassword.setText(null);
			txtEmail.setText(null);
			txtAddress.setText(null);
			txtAge.setText(null);
			bgGender.clearSelection();
			cbMember.setSelectedIndex(0);
		}
	}
}
