package Day3;

// Custom Exception for Insufficient Balance
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Bank Account Class
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Deposit method (Thread-safe)
    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited: " + amount + " | Balance: " + balance);
    }

    // Withdraw method (Thread-safe with exception handling)
    public synchronized void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal: " + amount);
        }
        balance -= amount;
        System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + " | Balance: " + balance);
    }

    // Get current balance
    public synchronized double getBalance() {
        return balance;
    }
}

// Transaction Class (Runnable for Multithreading)
class Transaction implements Runnable {
    private BankAccount account;
    private boolean isDeposit;
    private double amount;

    public Transaction(BankAccount account, boolean isDeposit, double amount) {
        this.account = account;
        this.isDeposit = isDeposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            if (isDeposit) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }
        } catch (InsufficientBalanceException e) {
            System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
        }
    }
}

// Main Class
public class BankingManagementSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance

        // Creating multiple transaction threads
        Thread t1 = new Thread(new Transaction(account, true, 500), "T1");  // Deposit 500
        Thread t2 = new Thread(new Transaction(account, false, 200), "T2"); // Withdraw 200
        Thread t3 = new Thread(new Transaction(account, false, 1500), "T3"); // Withdraw 1500
        Thread t4 = new Thread(new Transaction(account, true, 800), "T4");  // Deposit 800
        Thread t5 = new Thread(new Transaction(account, false, 300), "T5"); // Withdraw 300

        // Starting the threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Ensure all transactions complete before checking final balance
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final balance
        System.out.println("Final Account Balance: " + account.getBalance());
    }
}