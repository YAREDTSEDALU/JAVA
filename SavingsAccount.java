
package bankaccountmanagementsystem;

public class SavingsAccount extends Account {
    private final double interestRate = 0.03;

   public SavingsAccount(String accountNumber, String holder, double balance, int age) {
        super(accountNumber, holder, balance, age);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Savings Withdrawal: $" + amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds or invalid amount in savings.");
        }
    }

    @Override
    public void calculateInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest Added (Savings): $" + interest);
    }
}