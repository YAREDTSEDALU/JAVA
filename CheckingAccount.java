
package bankaccountmanagementsystem;

public class CheckingAccount extends Account {
    private final double overdraftLimit = 5;

    public CheckingAccount(String accountNumber, String holder, double balance, int age) {
        super(accountNumber, holder, balance, age);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance - overdraftLimit) {
            balance -= amount;
            System.out.println("Checking Withdrawal: $" + amount);
        } else {
            throw new IllegalArgumentException("Overdraft limit exceeded or invalid amount.");
        }
    }

    @Override
    public void calculateInterest() {
        
    }
}