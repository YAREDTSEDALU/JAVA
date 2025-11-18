package bankaccountmanagementsystem;

import java.util.*;

public class BankAccountManagementSystem {
    private static int savingsCounter = 1111;
    private static int checkingCounter = 2222;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Account> accounts = new HashMap<>();
        List<Transaction> transactions = new ArrayList<>();

        accounts.put("SA111", new SavingsAccount("SA111", "yeab", 5000000, 19));
        accounts.put("SA112", new SavingsAccount("SA112", "biruk", 5000, 21));
        accounts.put("SA113", new SavingsAccount("SA113", "esuyawkal", 50000, 20));
        accounts.put("CA444", new CheckingAccount("CA444", "reem", 5000000, 20));
        accounts.put("CA445", new CheckingAccount("CA445", "fanuel", 2000, 21));
        accounts.put("CA446", new CheckingAccount("CA446", "ermiyas", 7000, 21));

        System.out.print("Enter role (admin/user): ");
        String role = scanner.nextLine().trim().toLowerCase();

        if(role.equals("admin")) {
            runAdminMenu(scanner, accounts, transactions);
        } else if (role.equals("user")) {
            runUserMenu(scanner, accounts);
        } else {
            System.out.println("Invalid role entered. Exiting.");
        }

        scanner.close();
    }


    private static void runAdminMenu(Scanner scanner, Map<String, Account> accounts, List<Transaction> transactions) {
        int choice;
        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Calculate Interest");
            System.out.println("4. Display Account Details");
            System.out.println("5. Show Transactions");
            System.out.println("6. Add Account");
            System.out.println("7. Delete Account");
            System.out.println("8. Update Account Holder Name");
            System.out.println("9. Transfer Money");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        System.out.print("Enter deposit amount: ");
                        double amount = scanner.nextDouble();
                        acc.deposit(amount);
                        transactions.add(new Transaction("Deposit", amount));
                    }
                    case 2 -> {
                        System.out.print("Enter account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        System.out.print("Enter withdrawal amount: ");
                        double amount = scanner.nextDouble();
                        acc.withdraw(amount);
                        transactions.add(new Transaction("Withdraw", amount));
                    }
                    case 3 -> {
                        System.out.print("Enter account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        acc.calculateInterest();
                    }
                    case 4 -> {
                        System.out.print("Enter account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        acc.displayAccountDetails();
                    }
                    case 5 -> {
                        System.out.print("Enter account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        acc.showTransactions();
                    }
                    case 6 -> {
                        System.out.print("Enter account type (savings/checking): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter holder name: ");
                        String holder = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();

                        if (age < 18) {
                            System.out.println("Account creation failed: Minimum age is 18.");
                            break;
                        }

                        System.out.print("Enter initial balance: ");
                        double initBalance = scanner.nextDouble();

                        String accNo = generateAccountNumber(type);
                        if (type.equalsIgnoreCase("savings")) {
                            accounts.put(accNo, new SavingsAccount(accNo, holder, initBalance, age));
                        } else if (type.equalsIgnoreCase("checking")) {
                            accounts.put(accNo, new CheckingAccount(accNo, holder, initBalance, age));
                        } else {
                            System.out.println("Invalid account type.");
                            break;
                        }

                        System.out.println("Account created successfully. Account Number: " + accNo);
                    }
                    case 7 -> {
                        System.out.print("Enter account number to delete: ");
                        String accNo = scanner.nextLine();
                        if (accounts.remove(accNo) != null) {
                            System.out.println("Account deleted successfully.");
                        } else {
                            throw new AccountNotFoundException("Account not found: " + accNo);
                        }
                    }
                    case 8 -> {
                        System.out.print("Enter account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        System.out.print("Enter new holder name: ");
                        String newName = scanner.nextLine();
                        acc.setAccountHolderName(newName);
                        System.out.println("Account holder name updated.");
                    }
                    
                    case 9 -> {
                        System.out.print("Enter source account number: ");
                        String sourceAccNo = scanner.nextLine();
                        Account sourceAcc = getAccount(accounts, sourceAccNo);

                        System.out.print("Enter destination account number: ");
                        String destAccNo = scanner.nextLine();
                        Account destAcc = getAccount(accounts, destAccNo);

                        System.out.print("Enter amount to transfer: ");
                        double amount = scanner.nextDouble();

                        if (amount <= 0) {
                        System.out.println("Transfer amount must be greater than zero.");
                                     break;
                    }

                        if (sourceAcc.getBalance() < amount) {
                         System.out.println("Insufficient funds in source account.");
                                     break;
                    }

                        sourceAcc.withdraw(amount);
                        destAcc.deposit(amount);

                          transactions.add(new Transaction("Transfer from " + sourceAccNo + " to " + destAccNo, amount));
                          System.out.println("Transfer successful.");
                    }

                    case 0 -> System.out.println("Exiting admin menu...");
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine(); // clear buffer
                choice = -1;
            } catch (IllegalArgumentException | AccountNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
                choice = -1;
            }
        } while (choice != 0);
    }

    private static void runUserMenu(Scanner scanner, Map<String, Account> accounts) {
        int choice;
        do {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Display Account Details");
            System.out.println("2. Show Transactions");
            System.out.println("3. Transfer Money");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter your account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        acc.displayAccountDetails();
                    }
                    case 2 -> {
                        System.out.print("Enter your account number: ");
                        String accNo = scanner.nextLine();
                        Account acc = getAccount(accounts, accNo);
                        acc.showTransactions();
                    }
                    
                    case 3 -> {
                          System.out.print("Enter source account number: ");
                          String sourceAccNo = scanner.nextLine();
                           Account sourceAcc = getAccount(accounts, sourceAccNo);

                          System.out.print("Enter destination account number: ");
                          String destAccNo = scanner.nextLine();
                            Account destAcc = getAccount(accounts, destAccNo);

                           System.out.print("Enter amount to transfer: ");
                           double amount = scanner.nextDouble();

                           if (amount <= 0) {
                         System.out.println("Transfer amount must be greater than zero.");
                                           break;
                    }

                       if (sourceAcc.getBalance() < amount) {
                          System.out.println("Insufficient funds in source account.");
                                  break;
                    }

                             sourceAcc.withdraw(amount);
                            destAcc.deposit(amount);

                        transactions.add(new Transaction("Transfer from " + sourceAccNo + " to " + destAccNo, amount));
                           System.out.println("Transfer successful.");
}

                    case 0 -> System.out.println("Exiting user menu...");
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.nextLine();
                choice = -1;
            } catch (IllegalArgumentException | AccountNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
                choice = -1;
            }
        } while (choice != 0);
    }

  
    private static Account getAccount(Map<String, Account> accounts, String accNo) throws AccountNotFoundException {
        Account acc = accounts.get(accNo);
        if (acc == null) {
            throw new AccountNotFoundException("Account not found: " + accNo);
        }
        return acc;
    }

    private static String generateAccountNumber(String type) {
        if (type.equalsIgnoreCase("savings")) {
            return "SA" + savingsCounter++;
        } else if (type.equalsIgnoreCase("checking")) {
            return "CA" + checkingCounter++;
        } else {
            throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
