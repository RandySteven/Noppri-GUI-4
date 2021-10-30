import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TableUser extends JFrame{

	JPanel northPanel, centerPanel, bottomPanel;
	Connection conn;
	void init() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		north();
		center();
		south();
	}
	
	JLabel lblTitle;
	void north() {
		lblTitle = new JLabel("User Table");
		northPanel.add(lblTitle);
	}
	
	JPanel rightPanel;
	JScrollPane scrollUserPanel;
	void center() {
		rightPanel = new JPanel();
		scrollUserPanel = new JScrollPane();
		
		centerPanel.setLayout(new GridLayout(0, 2));
		
		centerPanel.add(scrollUserPanel);
		centerPanel.add(rightPanel);

		left();
		
		right();
	}
	
	JTable tblUser;
	void left() {
		tblUser = new JTable();
		scrollUserPanel.getViewport().add(tblUser);
		
		displayTableUser();
		selectedUser();
	}
	
	Integer userId = 0;
	void selectedUser() {
		tblUser.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblUser.rowAtPoint(e.getPoint());
				userId = Integer.parseInt(tblUser.getValueAt(row, 0).toString());
				String userName = tblUser.getValueAt(row, 1).toString();
				String userEmail = tblUser.getValueAt(row, 2).toString();
				Integer userAge = Integer.parseInt(tblUser.getValueAt(row, 3).toString());
				String userGender = tblUser.getValueAt(row, 4).toString();
				String userAddress = tblUser.getValueAt(row, 5).toString();
				
				txtFullName.setText(userName);
				txtEmail.setText(userEmail);
				txtAge.setText(userAge.toString());
				txtAddress.setText(userAddress);
				
				if(userGender.equals("Male")) {
					rbMale.setSelected(true);
				}else {
					rbFemale.setSelected(true);
				}
				
				try {
					String query = "SELECT * FROM users WHERE id="+ userId +"";
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(query);
					while(rs.next()) {
						String member = rs.getString("member");
						cbMember.setSelectedItem(member);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
	}
	
	void displayTableUser() {
		DefaultTableModel model = new DefaultTableModel();
		//model == database
		model.addColumn("User ID");
		model.addColumn("Full Name");
		model.addColumn("Email");
		model.addColumn("Age");
		model.addColumn("Gender");
		model.addColumn("Address");
		
		try {
			String query = "SELECT * FROM users"; //query database
			
			Statement st = conn.createStatement(); //statement untuk fungsi buat execute query
			
			ResultSet rs = st.executeQuery(query); //result/hasil yg didapatkan dari execute query
			
			while(rs.next()) { // Selama masih ada result maka 
				model.addRow(new Object[] {
						rs.getInt("id"),
						rs.getString("full_name"),
						rs.getString("email"),
						rs.getInt("age"),
						rs.getString("gender"),
						rs.getString("address")
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		tblUser.setModel(model);
	}
	
	JTextField txtFullName, txtEmail, txtAge;
	JPasswordField txtPassword;
	JTextArea txtAddress;
	JRadioButton rbMale, rbFemale;
	JComboBox cbMember;
	ButtonGroup bgGender;
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
	void right() {
		rightPanel.setLayout(new GridLayout(7, 2));
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
		
		rightPanel.add(lables[0]);
		rightPanel.add(txtFullName);
		rightPanel.add(lables[1]);
		rightPanel.add(txtEmail);
		rightPanel.add(lables[2]);
		rightPanel.add(txtPassword);
		rightPanel.add(lables[4]);
		rightPanel.add(genderPanel);
		rightPanel.add(lables[3]);
		rightPanel.add(txtAge);
		rightPanel.add(lables[5]);
		rightPanel.add(txtAddress);
		rightPanel.add(lables[6]);
		rightPanel.add(cbMember);
	}
	
	JButton btnUpdate, btnDelete;
	void south() {
		bottomPanel.setLayout(new FlowLayout());
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		bottomPanel.add(btnUpdate);
		bottomPanel.add(btnDelete);
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName, userEmail, userAddress, userGender, member;
				int userAge;
				userName = txtFullName.getText();
				userEmail = txtEmail.getText();
				userAddress = txtAddress.getText();
				if(rbMale.isSelected()) {
					userGender = "Male";
				}else {
					userGender = "Female";
				}
				userAge = Integer.parseInt(txtAge.getText().toString());
				member = cbMember.getSelectedItem().toString();
				String query = "UPDATE users SET "
						+ "full_name = '"+userName+"', "
								+ "email = '"+userEmail+"', "
										+ "gender = '"+userGender+"', "
												+ "address = '"+userAddress+"', "
														+ "member = '"+member+"',"
																+ "age= "+userAge+" "
																		+ "WHERE id="+userId+"";
				try {
					Statement st = conn.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Success update");
					st.close();
					displayTableUser();
					clear();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String query = "DELETE FROM users WHERE id="+userId+"";
				try {
					Statement st = conn.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Success delete");
					st.close();
					displayTableUser();
					clear();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
	}
	
	void clear() {
		txtFullName.setText(null);
		txtEmail.setText(null);
		txtAddress.setText(null);
		txtAge.setText(null);
		txtPassword.setText(null);
		cbMember.setSelectedIndex(0);
		bgGender.clearSelection();
	}
	
	public TableUser() {
		conn = sqlConnector.connection();
		setTitle("Table Users");
		setLayout(new BorderLayout());
		init();
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}

	public static void main(String[] args) {
		new TableUser();
	}

}
