package mocsbank;

public class MocsBankTransaction{
    /* Data Memebers */
    private String transactionType;
    private int accountNumber1;
    private int accountNumber2;
    private double amount;
    private double balanceBefore;
    private double balanceAfter;
    private static int numTransactions = 0;

    /* Constructors */
    // Constructor for opening/closing accounts
    public MocsBankTransaction(String transactionType, int accountNumber1, double amount) {
        this.transactionType = transactionType;
        this.accountNumber1 = accountNumber1;
        this.amount = amount;
        incrementTransactions();
    }

    // Constructor for withdraw/deposits
    public MocsBankTransaction(String transactionType, int accountNumber1, double amount, double balanceBefore, double balanceAfter) {
        this.transactionType = transactionType;
        this.accountNumber1 = accountNumber1;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        incrementTransactions();
    }

    // Constructor for transfer
    public MocsBankTransaction(String transactionType, int accountNumber1, int accountNumber2, double amount, double balanceBefore, double balanceAfter) {
        this.transactionType = transactionType;
        this.accountNumber1 = accountNumber1;
        this.accountNumber2 = accountNumber2;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        incrementTransactions();
    }

    /* Getters for data members */
    public String getTransactionType() {
        return transactionType;
    }
    public int getAccountNumber1() {
        return accountNumber1;
    }
    public int getAccountNumber2() {
        return accountNumber2;
    }
    public double getAmount() {
        return amount;
    }
    public double getBalanceBefore() {
        return balanceBefore;
    }
    public double getBalanceAfter() {
        return balanceAfter;
    }
    public static int getNumTransactions() {
        return numTransactions;
    }

    /* Setters for data members */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public void setAccountNumber1(int accountNumber1) {
        this.accountNumber1 = accountNumber1;
    }
    public void setAccountNumber2(int accountNumber2) {
        this.accountNumber2 = accountNumber2;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setBalanceBefore(double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }
    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    public static void setNumTransactions(int numTransactions) {
        MocsBankTransaction.numTransactions = numTransactions;
    }

    /* Methods */
    public void incrementTransactions(){
        numTransactions += 1;
    }
}
