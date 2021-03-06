package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer("Bhagy", "Bhagy123");
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer("Christina", "Christina123");
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer("John", "John123");
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(userNameExists(userName) && customersPasswordIsCorrect(userName,password)) {
			return new CustomerID(userName);
		}
		return null;
	}

	private boolean userNameExists(String userName) {
		return customers.containsKey(userName);
	}

	private boolean customersPasswordIsCorrect(String userName, String password) {
		return customers.get(userName).validatePassword(password);
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "HELP" : return listOptions();
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	public String createNewCustomer(String username, String password) {
		Customer newCustomer = new Customer();
		newCustomer.addAccount(new Account("Checking", 0.0));
		customers.put(username, newCustomer);
		return("Account created" + username);
	}

	private String listOptions() {
		String newLine = System.getProperty("line.separator");

		return "Option 1 : Show balance"
				.concat(newLine)
				.concat("Option 2 : Add balance")
				.concat(newLine)
				.concat("Option 3 : Withdraw balance")
				.concat(newLine)
				.concat("Option 4 : Transfer balance")
				.concat(newLine)
				.concat("Option 5 : Send money/pay bill")
				.concat(newLine)
				.concat("Option 6 : Apply for loan");
	}

}
