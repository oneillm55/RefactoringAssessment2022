
import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import java.util.Date;

public class Menu extends JFrame {

	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	private int position = 0;
	private String password,euro = "\u20ac";
	private Customer customer = null;
	private CustomerAccount acc = new CustomerAccount();
	JFrame f, f1;
	JLabel firstNameLabel, surnameLabel, pPSLabel, dOBLabel;
	JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
	JLabel customerIDLabel, passwordLabel;
	JTextField customerIDTextField, passwordTextField;
	Container content;
	JPanel panel2;
	String PPS, firstName, surname, DOB, CustomerID;

	public static void main(String[] args) {
		Menu driver = new Menu();
		driver.menuStart();
	}

	public void menuStart() {

		f = new JFrame("User Type");
		f.setSize(400, 300);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		JPanel userTypePanel = new JPanel();
		final ButtonGroup userType = new ButtonGroup();
		JRadioButton radioButton;
		userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
		radioButton.setActionCommand("Customer");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("Administrator"));
		radioButton.setActionCommand("Administrator");
		userType.add(radioButton);

		userTypePanel.add(radioButton = new JRadioButton("New Customer"));
		radioButton.setActionCommand("New Customer");
		userType.add(radioButton);

		JPanel continuePanel = new JPanel();
		JButton continueButton = new JButton("Continue");
		continuePanel.add(continueButton);

		Container content = f.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		content.add(userTypePanel);
		content.add(continuePanel);

		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String user = userType.getSelection().getActionCommand();
				
				switch(user){
	            case "New Customer":
	            	f.dispose();
					f1 = new JFrame("Create New Customer");
					f1.setSize(400, 300);
					f1.setLocation(200, 200);
					f1.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					Container content = f1.getContentPane();
					content.setLayout(new BorderLayout());

					firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
					surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
					pPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					JPanel panel = new JPanel(new GridLayout(6, 2));
					panel.add(firstNameLabel);
					panel.add(firstNameTextField);
					panel.add(surnameLabel);
					panel.add(surnameTextField);
					panel.add(pPSLabel);
					panel.add(pPSTextField);
					panel.add(dOBLabel);
					panel.add(dOBTextField);

					JPanel panel2 = new JPanel();
					JButton addButton = new JButton("Add");
					JButton cancelButton = new JButton("Cancel");


					addButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							f1.dispose();
							boolean passwordValid = false;
							while (!passwordValid) {
								password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");

								if (password.length() != 7)// Making sure password is at least 7 characters
								{
									JOptionPane.showMessageDialog(null, null, "Password must be 7 charatcers long",
											JOptionPane.OK_OPTION);
								} else {
									passwordValid = true;
									PPS = pPSTextField.getText();
									firstName = firstNameTextField.getText();
									surname = surnameTextField.getText();
									DOB = dOBTextField.getText();
									
									CustomerID = "ID" + PPS;
									
									ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
									Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password,
											accounts);

									customerList.add(customer);

									JOptionPane.showMessageDialog(f, "CustomerID = " + CustomerID + "\n Password = " + password,
											"Customer created.", JOptionPane.INFORMATION_MESSAGE);
									customer(customer);
								}
							}

							
						}
					});

					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							f1.dispose();
							menuStart();
						}
					});

					panel2.add(addButton);
					panel2.add(cancelButton);

					content.add(panel, BorderLayout.CENTER);
					content.add(panel2, BorderLayout.SOUTH);

					f1.setVisible(true);

	                break;
	                
	            case "Administrator":
	            	boolean adminNameValid = false, adminPasswordValid  = false;
					while (!adminNameValid) {
						Object adminUsername = JOptionPane.showInputDialog(f, "Enter Administrator Username:");

						if (!adminUsername.equals("admin"))
						{
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.NO_OPTION) {
								f1.dispose();
								menuStart();
							}
						} else {
							adminNameValid=true;
						}
					}

					while (!adminPasswordValid) {
						Object adminPassword = JOptionPane.showInputDialog(f, "Enter Administrator Password;");

						if (!adminPassword.equals("admin11")){
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
									JOptionPane.YES_NO_OPTION);
							 if (reply == JOptionPane.NO_OPTION) {
								f1.dispose();
								menuStart();
							}
						} else {
							adminPasswordValid = true;
							admin();
						}
					}
	                break;
	            case "Customer":
	            	boolean passwordValid = false;
					boolean found = false;
					Customer customer = null;
					
					while (!found) {
						Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)){
								found = true;
								customer = aCustomer;
							}
						}

						if (!found) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.NO_OPTION) {
								found = true;
								f.dispose();
								menuStart();
							}
						} 

					}
					

					while (!passwordValid) {
						Object customerPassword = JOptionPane.showInputDialog(f, "Enter Customer Password;");

						if (!customer.getPassword().equals(customerPassword)){
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
									JOptionPane.YES_NO_OPTION);
							 if (reply == JOptionPane.NO_OPTION) {
								passwordValid = true;
								f.dispose();
								menuStart();
							}
						} else {
							customer(customer);
							passwordValid = true;
						}
					}
	                break;
				}

			
			}
		});
		f.setVisible(true);
	}

	public void admin() {
		dispose();

		f = new JFrame("Administrator Menu");
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setVisible(true);

		JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteCustomer = new JButton("Delete Customer");
		deleteCustomer.setPreferredSize(new Dimension(250, 20));
		deleteCustomerPanel.add(deleteCustomer);

		JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteAccount = new JButton("Delete Account");
		deleteAccount.setPreferredSize(new Dimension(250, 20));
		deleteAccountPanel.add(deleteAccount);

		JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bankChargesButton = new JButton("Apply Bank Charges");
		bankChargesButton.setPreferredSize(new Dimension(250, 20));
		bankChargesPanel.add(bankChargesButton);

		JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton interestButton = new JButton("Apply Interest");
		interestPanel.add(interestButton);
		interestButton.setPreferredSize(new Dimension(250, 20));

		JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton editCustomerButton = new JButton("Edit existing Customer");
		editCustomerPanel.add(editCustomerButton);
		editCustomerButton.setPreferredSize(new Dimension(250, 20));

		JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton navigateButton = new JButton("Navigate Customer Collection");
		navigatePanel.add(navigateButton);
		navigateButton.setPreferredSize(new Dimension(250, 20));

		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton summaryButton = new JButton("Display Summary Of All Accounts");
		summaryPanel.add(summaryButton);
		summaryButton.setPreferredSize(new Dimension(250, 20));

		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton accountButton = new JButton("Add an Account to a Customer");
		accountPanel.add(accountButton);
		accountButton.setPreferredSize(new Dimension(250, 20));

		JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton returnButton = new JButton("Exit Admin Menu");
		returnPanel.add(returnButton);

		JLabel label1 = new JLabel("Please select an option");

		content = f.getContentPane();
		content.setLayout(new GridLayout(9, 1));
		content.add(label1);
		content.add(accountPanel);
		content.add(bankChargesPanel);
		content.add(interestPanel);
		content.add(editCustomerPanel);
		content.add(navigatePanel);
		content.add(summaryPanel);
		content.add(deleteCustomerPanel);
		// content.add(deleteAccountPanel);
		content.add(returnPanel);

		bankChargesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {


				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {Customer getCust = getCustomer(customerList);
				if(!getCust.getCustomerID().isEmpty()) {
					customer = getCust;
					
							f.dispose();
							f = new JFrame("Administrator Menu");
							f.setSize(400, 300);
							f.setLocation(200, 200);
							f.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {

								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();
							boxPanel.add(box);

							JPanel buttonPanel = new JPanel();
							JButton continueButton = new JButton("Apply Charge");
							JButton returnButton = new JButton("Return");
							buttonPanel.add(continueButton);
							buttonPanel.add(returnButton);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must addButton acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								admin();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										acc = customer.getAccounts().get(i);
									}
								}

								continueButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {

										if (acc instanceof CustomerDepositAccount) {

											JOptionPane.showMessageDialog(f,
													"25" + euro + " deposit account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											acc.setBalance(acc.getBalance() - 25);
											JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										if (acc instanceof CustomerCurrentAccount) {

											JOptionPane.showMessageDialog(f,
													"15" + euro + " current account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											acc.setBalance(acc.getBalance() - 25);
											JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										f.dispose();
										admin();
									}
								});

								returnButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										menuStart();
									}
								});

							}
						
					}
				}

			}
		});

		interestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {
					Customer getCust = getCustomer(customerList);
					if(!getCust.getCustomerID().isEmpty()) {
						customer = getCust;
						f.dispose();
						f = new JFrame("Administrator Menu");
						f.setSize(400, 300);
						f.setLocation(200, 200);
						f.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent we) {
								System.exit(0);
							}
						});
						f.setVisible(true);

						JComboBox<String> box = new JComboBox<String>();
						for (int i = 0; i < customer.getAccounts().size(); i++) {

							box.addItem(customer.getAccounts().get(i).getNumber());
						}

						box.getSelectedItem();

						JPanel boxPanel = new JPanel();

						JLabel label = new JLabel("Select an account to apply interest to:");
						boxPanel.add(label);
						boxPanel.add(box);
						JPanel buttonPanel = new JPanel();
						JButton continueButton = new JButton("Apply Interest");
						JButton returnButton = new JButton("Return");
						buttonPanel.add(continueButton);
						buttonPanel.add(returnButton);
						Container content = f.getContentPane();
						content.setLayout(new GridLayout(2, 1));

						content.add(boxPanel);
						content.add(buttonPanel);

						if (customer.getAccounts().isEmpty()) {
							JOptionPane.showMessageDialog(f,
									"This customer has no accounts! \n The admin must addButton acounts to this customer.",
									"Oops!", JOptionPane.INFORMATION_MESSAGE);
							f.dispose();
							admin();
						} else {

							for (int i = 0; i < customer.getAccounts().size(); i++) {
								if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
									acc = customer.getAccounts().get(i);
								}
							}

							continueButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									double interest = 0;
									boolean loop = true;

									while (loop) {
										String interestString = JOptionPane.showInputDialog(f,
												"Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");// the
																																																			// numeric.
										if (isNumeric(interestString)) {

											interest = Double.parseDouble(interestString);
											loop = false;

											acc.setBalance(
													acc.getBalance() + (acc.getBalance() * (interest / 100)));

											JOptionPane.showMessageDialog(f,
													interest + "% interest applied. \n new balance = "
															+ acc.getBalance() + euro,
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										else {
											JOptionPane.showMessageDialog(f, "You must enter a numerical value!",
													"Oops!", JOptionPane.INFORMATION_MESSAGE);
										}

									}

									f.dispose();
									admin();
								}
							});

							returnButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									f.dispose();
									menuStart();
								}
							});

						}
						
					}
					
				}

			}
		});

		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {


				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {

					Customer getCust = getCustomer(customerList);
					if(!getCust.getCustomerID().isEmpty()) {
						
						customer = getCust;
						f.dispose();

						f.dispose();
						f = new JFrame("Administrator Menu");
						f.setSize(400, 300);
						f.setLocation(200, 200);
						f.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent we) {
								System.exit(0);
							}
						});

						firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
						surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
						pPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
						dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
						customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
						passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
						firstNameTextField = new JTextField(20);
						surnameTextField = new JTextField(20);
						pPSTextField = new JTextField(20);
						dOBTextField = new JTextField(20);
						customerIDTextField = new JTextField(20);
						passwordTextField = new JTextField(20);

						JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

						JPanel cancelPanel = new JPanel();

						textPanel.add(firstNameLabel);
						textPanel.add(firstNameTextField);
						textPanel.add(surnameLabel);
						textPanel.add(surnameTextField);
						textPanel.add(pPSLabel);
						textPanel.add(pPSTextField);
						textPanel.add(dOBLabel);
						textPanel.add(dOBTextField);
						textPanel.add(customerIDLabel);
						textPanel.add(customerIDTextField);
						textPanel.add(passwordLabel);
						textPanel.add(passwordTextField);

						firstNameTextField.setText(customer.getFirstName());
						surnameTextField.setText(customer.getSurname());
						pPSTextField.setText(customer.getPPS());
						dOBTextField.setText(customer.getDOB());
						customerIDTextField.setText(customer.getCustomerID());
						passwordTextField.setText(customer.getPassword());


						JButton saveButton = new JButton("Save");
						JButton cancelButton = new JButton("Exit");

						cancelPanel.add(cancelButton, BorderLayout.SOUTH);
						cancelPanel.add(saveButton, BorderLayout.SOUTH);
						Container content = f.getContentPane();
						content.setLayout(new GridLayout(2, 1));
						content.add(textPanel, BorderLayout.NORTH);
						content.add(cancelPanel, BorderLayout.SOUTH);

						f.setContentPane(content);
						f.setSize(340, 350);
						f.setLocation(200, 200);
						f.setVisible(true);
						f.setResizable(false);

						saveButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae) {

								customer.setFirstName(firstNameTextField.getText());
								customer.setSurname(surnameTextField.getText());
								customer.setPPS(pPSTextField.getText());
								customer.setDOB(dOBTextField.getText());
								customer.setCustomerID(customerIDTextField.getText());
								customer.setPassword(passwordTextField.getText());

								JOptionPane.showMessageDialog(null, "Changes Saved.");
							}
						});

						cancelButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								f.dispose();

								admin();
							}
						});
						
					}

					
				}
			}
		});

		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				f = new JFrame("Summary of Transactions");
				f.setSize(400, 700);
				f.setLocation(200, 200);
				f.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						System.exit(0);
					}
				});
				f.setVisible(true);

				JLabel label1 = new JLabel("Summary of all transactions: ");

				JPanel returnPanel = new JPanel();
				JButton returnButton = new JButton("Return");
				returnPanel.add(returnButton);

				JPanel textPanel = new JPanel();

				textPanel.setLayout(new BorderLayout());
				JTextArea textArea = new JTextArea(40, 20);
				textArea.setEditable(false);
				textPanel.add(label1, BorderLayout.NORTH);
				textPanel.add(textArea, BorderLayout.CENTER);
				textPanel.add(returnButton, BorderLayout.SOUTH);

				JScrollPane scrollPane = new JScrollPane(textArea);
				textPanel.add(scrollPane);
				
				 double total=0;

				for (int a = 0; a < customerList.size(); a++)//Loops trough each customer
				{
					for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {//Loops trough each customer account
						acc = customerList.get(a).getAccounts().get(b);
						for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {//Loops trough each customer account transaction

							textArea.append(acc.getTransactionList().get(c).toString());
							AccountTransaction ac = new AccountTransaction();
							 total = total + ((AccountTransaction) acc.getTransactionList().get(c)).getAmount(); //I was going to use
							// this to keep a running total but I couldn't get it working.

						}
					}
				}

				textPanel.add(textArea);
				content.removeAll();

				Container content = f.getContentPane();
				content.setLayout(new GridLayout(1, 1));
				content.add(textPanel);

				returnButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						f.dispose();
						admin();
					}
				});
			}
		});

		navigateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					admin();
				} else {

					JButton firstButton, previousButton, nextButton, lastButton, cancelButton;
					JPanel gridPanel, buttonPanel, cancelPanel;

					Container content = getContentPane();

					content.setLayout(new BorderLayout());

					buttonPanel = new JPanel();
					gridPanel = new JPanel(new GridLayout(8, 2));
					cancelPanel = new JPanel();

					firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					firstButton = new JButton("First");
					previousButton = new JButton("Previous");
					nextButton = new JButton("Next");
					lastButton = new JButton("Last");
					cancelButton = new JButton("Cancel");

					firstNameTextField.setText(customerList.get(0).getFirstName());
					surnameTextField.setText(customerList.get(0).getSurname());
					pPSTextField.setText(customerList.get(0).getPPS());
					dOBTextField.setText(customerList.get(0).getDOB());
					customerIDTextField.setText(customerList.get(0).getCustomerID());
					passwordTextField.setText(customerList.get(0).getPassword());

					firstNameTextField.setEditable(false);
					surnameTextField.setEditable(false);
					pPSTextField.setEditable(false);
					dOBTextField.setEditable(false);
					customerIDTextField.setEditable(false);
					passwordTextField.setEditable(false);

					gridPanel.add(firstNameLabel);
					gridPanel.add(firstNameTextField);
					gridPanel.add(surnameLabel);
					gridPanel.add(surnameTextField);
					gridPanel.add(pPSLabel);
					gridPanel.add(pPSTextField);
					gridPanel.add(dOBLabel);
					gridPanel.add(dOBTextField);
					gridPanel.add(customerIDLabel);
					gridPanel.add(customerIDTextField);
					gridPanel.add(passwordLabel);
					gridPanel.add(passwordTextField);

					buttonPanel.add(firstButton);
					buttonPanel.add(previousButton);
					buttonPanel.add(nextButton);
					buttonPanel.add(lastButton);

					cancelPanel.add(cancelButton);

					content.add(gridPanel, BorderLayout.NORTH);
					content.add(buttonPanel, BorderLayout.CENTER);
					content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
					firstButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							setFeilds(0);
						}
					});

					previousButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position >= 1) {
								position = position - 1;
								setFeilds(position);
							}
						}
					});

					nextButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							if (position != customerList.size() - 1) {
								position = position + 1;
								setFeilds(position);
							} 

						}
					});

					lastButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							position = customerList.size() - 1;
							setFeilds(position);
						}
					});

					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							dispose();
							admin();
						}
					});
					setContentPane(content);
					setSize(400, 300);
					setVisible(true);
				}
			}
		});

		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				
				

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();
				} else {
					Customer getCust = getCustomer(customerList);
					if(!getCust.getCustomerID().isEmpty()) {
						String[] choices = { "Current Account", "Deposit Account" };
						String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
								"Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

						if (account.equals("Current Account")) {
							// create current account
							boolean valid = true;
							double balance = 0;
							double overdraft = 100;
							String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10
									+ (getCust.getAccounts().size() + 1));// this simple algorithm generates the
																			// account number
							ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
							int randomPIN = (int) (Math.random() * 9000) + 1000;
							String pin = String.valueOf(randomPIN);

							ATMCard atm = new ATMCard(randomPIN, valid);

							CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance,
									transactionList,overdraft);

							getCust.getAccounts().add(current);
							JOptionPane.showMessageDialog(f, "Account number = " + number + "\n PIN = " + pin,
									"Account created.", JOptionPane.INFORMATION_MESSAGE);

							f.dispose();
							admin();
						}

						if (account.equals("Deposit Account")) {
							// create deposit account

							double balance = 0, interest = 0;
							String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10
									+ (getCust.getAccounts().size() + 1));// this simple algorithm generates the
																			// account number
							ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

							CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
									transactionList);

							getCust.getAccounts().add(deposit);
							JOptionPane.showMessageDialog(f, "Account number = " + number, "Account created.",
									JOptionPane.INFORMATION_MESSAGE);

							f.dispose();
							admin();
						}
					}
				}
			}
		});

		deleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					dispose();
					admin();
				} else {
					{
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Delete:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							if (customer.getAccounts().size() > 0) {
								JOptionPane.showMessageDialog(f,
										"This customer has accounts. \n You must delete a customer's accounts before deleting a customer ",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								customerList.remove(customer);
								JOptionPane.showMessageDialog(f, "Customer Deleted ", "Success.",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}
				}
			}
		});

		deleteAccount.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;
					Object customerID = JOptionPane.showInputDialog(f,
							"Customer ID of Customer from which you wish to delete an account");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
							loop = false;
						}
					}

					if (found == false) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							f.dispose();
							loop = false;

							admin();
						}
					} else {
						// Here I would make the user select a an account to delete from a combo box. If
						// the account had a balance of 0 then it would be deleted. (I do not have time
						// to do this)
					}

				
			}

		});
	
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				menuStart();
			}
		});
	}

	public void customer(Customer cust) {
		f = new JFrame("Customer Menu");
		f.setSize(400, 300);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setVisible(true);
		

		if (cust.getAccounts().isEmpty()) {
			JOptionPane.showMessageDialog(f,
					"This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
			menuStart();
		} else {
			JPanel buttonPanel = new JPanel();
			JPanel boxPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			JLabel label = new JLabel("Select Account:");
			labelPanel.add(label);

			JButton returnButton = new JButton("Return");
			buttonPanel.add(returnButton);
			JButton continueButton = new JButton("Continue");
			buttonPanel.add(continueButton);

			JComboBox<String> box = new JComboBox<String>();
			for(CustomerAccount account: cust.getAccounts()) {
				box.addItem(account.getNumber());
				
			}
			

			boxPanel.add(box);
			content = f.getContentPane();
			content.setLayout(new GridLayout(3, 1));
			content.add(labelPanel);
			content.add(boxPanel);
			content.add(buttonPanel);

			returnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					f.dispose();
					menuStart();
				}
			});

			continueButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					
					for(CustomerAccount account: cust.getAccounts()) {
						if(account.getNumber().equals(box.getSelectedItem())) {
							acc = account;
						}
					}

					f.dispose();

					f = new JFrame("Customer Menu");
					f.setSize(400, 300);
					f.setLocation(200, 200);
					f.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					f.setVisible(true);

					JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton statementButton = new JButton("Display Bank Statement");
					statementButton.setPreferredSize(new Dimension(250, 20));

					statementPanel.add(statementButton);

					JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton lodgementButton = new JButton("Lodge money into account");
					lodgementPanel.add(lodgementButton);
					lodgementButton.setPreferredSize(new Dimension(250, 20));

					JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton withdrawButton = new JButton("Withdraw money from account");
					withdrawalPanel.add(withdrawButton);
					withdrawButton.setPreferredSize(new Dimension(250, 20));

					JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					JButton returnButton = new JButton("Exit Customer Menu");
					returnPanel.add(returnButton);

					JLabel label1 = new JLabel("Please select an option");

					content = f.getContentPane();
					content.setLayout(new GridLayout(5, 1));
					content.add(label1);
					content.add(statementPanel);
					content.add(lodgementPanel);
					content.add(withdrawalPanel);
					content.add(returnPanel);

					statementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							f = new JFrame("Customer Menu");
							f.setSize(400, 600);
							f.setLocation(200, 200);
							f.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							f.setVisible(true);

							JLabel label1 = new JLabel("Summary of account transactions: ");

							JPanel returnPanel = new JPanel();
							JButton returnButton = new JButton("Return");
							returnPanel.add(returnButton);

							JPanel textPanel = new JPanel();

							textPanel.setLayout(new BorderLayout());
							JTextArea textArea = new JTextArea(40, 20);
							textArea.setEditable(false);
							textPanel.add(label1, BorderLayout.NORTH);
							textPanel.add(textArea, BorderLayout.CENTER);
							textPanel.add(returnButton, BorderLayout.SOUTH);

							JScrollPane scrollPane = new JScrollPane(textArea);
							textPanel.add(scrollPane);

							for (int i = 0; i < acc.getTransactionList().size(); i++) {
								textArea.append(acc.getTransactionList().get(i).toString());

							}

							textPanel.add(textArea);
							content.removeAll();

							Container content = f.getContentPane();
							content.setLayout(new GridLayout(1, 1));
							// content.add(label1);
							content.add(textPanel);
							// content.add(returnPanel);

							returnButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									f.dispose();
									customer(cust);
								}
							});
						}
					});

					lodgementButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean accountLocked = false;
							double balance = 0;

							if (acc instanceof CustomerCurrentAccount) {
								accountLocked = atmLogin(cust);

							}
							if (!accountLocked) {
								String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to lodge:");
								double check=checkNumber(balanceTest);
								if(check>0)	{
									balance=check;
								};

								acc.setBalance(acc.getBalance() + balance);
								Date date = new Date();
								String type = "Lodgement";
								double amount = balance;

								AccountTransaction transaction = new AccountTransaction(date.toString(), type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(f, balance + euro + " added do you account!", "Lodgement",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance() + euro,
										"Lodgement", JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					withdrawButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							boolean accountLocked = false;
							double withdraw = 0;

							if (acc instanceof CustomerCurrentAccount) {
								accountLocked = atmLogin(cust);

							}
							if (!accountLocked) {
								String balanceTest = JOptionPane.showInputDialog(f,
										"Enter amount you wish to withdraw (max 500):");
								
								double check=checkNumber(balanceTest);
								if(check>0)	{
									withdraw=check;
									};
								
								
								if (withdraw > 500) {
									JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time.",
											"Oops!", JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}
									if (acc.validateWithdrawl(withdraw)) {
										JOptionPane.showMessageDialog(f, "Insufficient funds.", "Oops!",
												JOptionPane.INFORMATION_MESSAGE);
										withdraw = 0;
									}else {


										acc.setBalance(acc.getBalance() - withdraw);
										Date date = new Date();

										String type = "Withdraw";
										double amount = withdraw;

										AccountTransaction transaction = new AccountTransaction(date.toString(), type, amount);
										acc.getTransactionList().add(transaction);

										JOptionPane.showMessageDialog(f, withdraw + euro + " withdrawn.", "Withdraw",
												JOptionPane.INFORMATION_MESSAGE);
										JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance() + euro, "Withdraw",
												JOptionPane.INFORMATION_MESSAGE);
										
									}
							}

						}
					});

					returnButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							menuStart();
						}
					});
				}
			});
		}
	}

	public static boolean isNumeric(String str)
	{
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public Customer getCustomer(ArrayList<Customer> list) {
		Customer c = new Customer();
		
		boolean loop = true;

		boolean customerFound = false;

		while (loop) {
			Object customerID = JOptionPane.showInputDialog(f,
					"Enter Customer ID:");

			for (Customer aCustomer : list) {

				if (aCustomer.getCustomerID().equals(customerID)) {
					customerFound = true;
					c = aCustomer;
					loop = false;
				}
			}

			if (!customerFound) {
				int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					loop = true;
				} else if (reply == JOptionPane.NO_OPTION) {
					f.dispose();
					loop = false;

					admin();
				}
			}
			}
		return c;
		
	}
	
	public void setFeilds(int position) {
		firstNameTextField.setText(customerList.get(position).getFirstName());
		surnameTextField.setText(customerList.get(position).getSurname());
		pPSTextField.setText(customerList.get(position).getPPS());
		dOBTextField.setText(customerList.get(position).getDOB());
		customerIDTextField.setText(customerList.get(position).getCustomerID());
		passwordTextField.setText(customerList.get(position).getPassword());
	}
	
	public boolean atmLogin(Customer cust) {

		boolean accountLocked = false;
		int count = 3;
		int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
		boolean loop = true;

		while (loop) {
			if (count == 0) {
				JOptionPane.showMessageDialog(f,
						"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
						JOptionPane.INFORMATION_MESSAGE);
				((CustomerCurrentAccount) acc).getAtm().setValid(false);
				customer(cust);
				loop = false;
				accountLocked = true;
			}

			String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
			int i = Integer.parseInt(Pin);

			if (!accountLocked) {
				if (checkPin == i) {
					loop = false;
					JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin",
							JOptionPane.INFORMATION_MESSAGE);

				} else {
					count--;
					JOptionPane.showMessageDialog(f,
							"Incorrect pin. " + count + " attempts remaining.", "Pin",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		}
		return accountLocked;
	}
	
	public double checkNumber(String s) {
		if (isNumeric(s)) {

			return Double.parseDouble(s);

		} else {
			JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
					JOptionPane.INFORMATION_MESSAGE);

			return -1; //ie if the method returns a negative number the check is invalid
		}
	}
}
