package mocsbank;

public class MocsBankAccount {
    /* Data Memebers */
    private int accountNumber;
    private FSCmember customer;
    private double accountBalance;
    private static int numAccounts = 0;

    /* Constructor */
    public MocsBankAccount(){

    }
    public MocsBankAccount(int accountNumber, int ID, String firstName, String lastName, double accountBalance) {
        this.accountNumber = accountNumber;
        this.customer = new FSCmember(ID, firstName, lastName);
        this.accountBalance = accountBalance;
    }

    /* Getters for data members */
    public int getAccountNumber() {
        return accountNumber;
    }
    public FSCmember getCustomer() {
        return customer;
    }
    public double getAccountBalance() {
        return accountBalance;
    }
    public static int getNumAccounts() {
        return numAccounts;
    }

    /* Setters for data members */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setCustomer(FSCmember customer) {
        this.customer = customer;
    }
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public static void setNumAccounts(int numAccounts) {
        MocsBankAccount.numAccounts = numAccounts;
    }

    /* Methods */
    static public void incrementNumAccounts(){
        numAccounts += 1;
    }
    static public void decrementNumAccounts(){
        numAccounts -= 1;
    }
}
