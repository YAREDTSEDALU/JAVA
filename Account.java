
package bankaccountmanagementsystem;

import java.util.ArrayList;
import java.util.List;

   public abstract class Account {
    private String accountNumber;
    private String accountHolderName;
    protected double balance;
    protected int age;
    public Account(String accountNumber, String accountHolderName, double initialBalance, int age) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.age = age;
    }
    
    public void showTransactions() {
        List<Transaction> transactions = new ArrayList<>();
    
    if (transactions == null || transactions.isEmpty()) {
        System.out.println("No transactions found.");
    } else {
        System.out.println("Transaction History:");
        for (Transaction tx : transactions) {
            System.out.println(tx);
        }
    }
}
    
    public int getAge() { return age;}
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String name) { this.accountHolderName = name; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    public abstract void withdraw(double amount);
    public abstract void calculateInterest();

    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder: " + accountHolderName);
        System.out.println("Balance: $" + balance);
          System.out.println("Age: " + age);
    }
}
