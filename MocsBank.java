package mocsbank;

// Main File

// Imports required for program to run
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class MocsBank {
    // Method to create an account
    public static void OPENACCOUNT(MocsBankAccount[] accounts, MocsBankTransaction[] transactions, Scanner in, PrintWriter output) {
        // Data members from input being saved
        int ID = in.nextInt();
        String firstName = in.next();
        String lastName = in.next();
        double amount = in.nextDouble();

        // Creating a new instance of a member in the array with information provided
        accounts[MocsBankAccount.getNumAccounts()] = new MocsBankAccount(ID, ID, firstName, lastName, amount);

        // Displays the added Account.
        output.println("\tNew Account Opened");
        output.printf("\tAccount:          %d\n", accounts[MocsBankAccount.getNumAccounts()].getAccountNumber());
        output.printf("\tName:             %s %s\n", accounts[MocsBankAccount.getNumAccounts()].getCustomer().getFirstName(), accounts[MocsBankAccount.getNumAccounts()].getCustomer().getLastName());
        output.printf("\tOpening Balance:  %.2f\n\n", accounts[MocsBankAccount.getNumAccounts()].getAccountBalance());

        // Used to sort the array of accounts by ID by calling the SORT method given that there are enough accounts
        if (MocsBankAccount.getNumAccounts() > 1){
            SORT(accounts[MocsBankAccount.getNumAccounts()].getAccountNumber(), accounts, MocsBankAccount.getNumAccounts());
        }

        // Incrementing transactions and number of accounts
        transactions[MocsBankTransaction.getNumTransactions()] = new MocsBankTransaction("OPENACCOUNT", ID, amount);
        MocsBankAccount.incrementNumAccounts();
    }

    // Method for printing a users balance
    public static void PRINTBALANCE(MocsBankAccount[] accounts, Scanner in, PrintWriter output) {
        // Data members from input being saved
        int ID = in.nextInt();

        // Assuming there are enough accounts, will search for account in the array
        if (MocsBankAccount.getNumAccounts() >= 1){
            // Calls binary search method to save index of member
            int index = binarySearch(accounts, ID);
            // If the member isnt found
            if (index == -1){
                output.printf("\tError: cannot print balance. Account #%d was not found in the system.\n\n", ID);
                return;
            }
            // If member is found -> Prints the account and its balance
            output.printf("\tAccount:          %d\n", accounts[index].getAccountNumber());
            output.printf("\tName:             %s %s\n", accounts[index].getCustomer().getFirstName(), accounts[index].getCustomer().getLastName());
            output.printf("\tCurrent Balance:  %.2f\n\n", accounts[index].getAccountBalance());
        }

        // Error message if there are no accounts
        else{
            output.println("\tError: cannot print balance. There are no open accounts in MocsBank.\n");
        }
    }

    // Method for depositing money into a members account
    public static void DEPOSIT(MocsBankAccount[] accounts, MocsBankTransaction[] transactions, Scanner in, PrintWriter output) {
        // Data members from input being saved
        int ID = in.nextInt();
        double amount = in.nextDouble();

        // Assuming there are enough accounts, will search for account in the array
        if (MocsBankAccount.getNumAccounts() >= 1){
            // Calls binary search method to save index of member
            int index = binarySearch(accounts, ID);
            // If the member isnt found
            if (index == -1){
                output.printf("\tError: cannot make deposit. Account #%d was not found in the system.\n\n", ID);
                return;
            }
            // Prints out the transaction of depositing money to the account
            output.printf("\tAccount:         %d\n", accounts[index].getAccountNumber());
            output.printf("\tName:            %s %s\n", accounts[index].getCustomer().getFirstName(), accounts[index].getCustomer().getLastName());
            output.printf("\tDeposit Amount:  %.2f\n", amount);
            output.printf("\tNew Balance:     %.2f\n\n", accounts[index].getAccountBalance() + amount);

            // Stores the transaction and information
            transactions[MocsBankTransaction.getNumTransactions()] = new MocsBankTransaction("DEPOSIT", ID, amount, accounts[index].getAccountBalance(), accounts[index].getAccountBalance() + amount);

            // Updates the account balance
            accounts[index].setAccountBalance(accounts[index].getAccountBalance() + amount);
            return;
        }

        // Error message if there are no accounts
        else{
            output.println("\tError: cannot make deposit. There are no open accounts in MocsBank.\n");
        }
    }

    // Method for withdrawing money from an account
    public static void WITHDRAW(MocsBankAccount[] accounts, MocsBankTransaction[] transactions, Scanner in, PrintWriter output) {
        // Data members from input being saved
        int ID = in.nextInt();
        double amount = in.nextDouble();

        // Continues if there are enough accounts
        if (MocsBankAccount.getNumAccounts() >= 1){

            // Calls binary search method to save index of member
            int index = binarySearch(accounts, ID);

            // If the member isnt found
            if (index == -1){
                output.printf("\tError: cannot make withdrawal. Account #%d was not found in the system.\n\n", ID);
                return;
            }

            // Checks if account has enough money to withdraw
            if (accounts[index].getAccountBalance() - amount >= 0) {
                // Prints out the transaction of withdrawing money to the account
                output.printf("\tAccount:            %d\n", accounts[index].getAccountNumber());
                output.printf("\tName:               %s %s\n", accounts[index].getCustomer().getFirstName(), accounts[index].getCustomer().getLastName());
                output.printf("\tWithdrawal Amount:  %.2f\n", amount);
                output.printf("\tNew Balance:        %.2f\n\n", accounts[index].getAccountBalance() - amount);

                // Creates the transaction report and changes account balance
                transactions[MocsBankTransaction.getNumTransactions()] = new MocsBankTransaction("WITHDRAW", ID, amount, accounts[index].getAccountBalance(), accounts[index].getAccountBalance() - amount);
                accounts[index].setAccountBalance(accounts[index].getAccountBalance() - amount);
            }

            // Output if the account doesnt have the funds for transfer
            else {
                output.println("\tError: Insufficient funds.\n");
                return;
            }
        }

        // Error message if there are no accounts
        else{
            output.println("\tError: cannot make withdrawal. There are no open accounts in MocsBank.\n");
        }
    }

    // Method for transfering money from one account to another
    public static void TRANSFER(MocsBankAccount[] accounts, MocsBankTransaction[] transactions, Scanner in, PrintWriter output) {
        // Variables for finding the ID of sender and receiver + the amount
        int IDofSender = in.nextInt();
        int IDofReceiver = in.nextInt();
        double amount = in.nextDouble();

        // Loops through the accounts to find the people participating in the transaction
        if (MocsBankAccount.getNumAccounts() >= 1) {

            // Loops through the accounts to find the people participating in the transaction
            // Receiver
            for (int i = 0; i < MocsBankAccount.getNumAccounts(); i++) {
                if (accounts[i].getAccountNumber() == IDofReceiver) {

                    // Sender
                    for (int j = 0; j < MocsBankAccount.getNumAccounts(); j++) {
                        if (accounts[j].getAccountNumber() == IDofSender) {

                            // Checks if the account has enough money to send
                            if (accounts[j].getAccountBalance() - amount >= 0) {
                                output.printf("\tAccount (from):   %d\n", accounts[j].getAccountNumber());
                                output.printf("\tAccount (to):     %d\n", accounts[i].getAccountNumber());
                                output.printf("\tTransfer Amount:  %.2f\n\n", amount);
                                transactions[MocsBankTransaction.getNumTransactions()] = new MocsBankTransaction("TRANSFER", IDofSender, IDofReceiver, amount, accounts[j].getAccountBalance(), accounts[j].getAccountBalance() - amount);
                                accounts[j].setAccountBalance(accounts[j].getAccountBalance() - amount);
                                transactions[MocsBankTransaction.getNumTransactions()] = new MocsBankTransaction("TRANSFER", IDofSender, IDofReceiver, amount, accounts[i].getAccountBalance(), accounts[i].getAccountBalance() + amount);
                                accounts[i].setAccountBalance(accounts[i].getAccountBalance() + amount);
                                return;
                            }

                            // Insufficient funds output
                            else {
                                output.println("\tError: Insufficient funds.\n");
                                return;
                            }
                        }
                    }
                }
            }
            // Output if one of the account IDs are missing
            output.println("\tError: cannot make transfer. One (or more) of the accounts is not in the system.\n");
        }
        // Output if there are no accounts
        else{
            output.println("\tError: cannot make transfer. There are no open accounts in MocsBank.\n");
        }
    }

    // Method for closing accounts
    public static void CLOSEACCOUNT(MocsBankAccount[] accounts, MocsBankTransaction[] transactions, Scanner in, PrintWriter output) {
        // Saves the ID of the account being closed
        int ID = in.nextInt();

        // Checks if there are enough accounts
        if (MocsBankAccount.getNumAccounts() >= 1){

            // Scans all the objects
            for (int i = 0; i < MocsBankAccount.getNumAccounts(); i++) {

                // Checks if the index of i is the ID
                if (accounts[i].getAccountNumber() == ID) {

                    // Prints the success of closing the account
                    output.println("\tAccount Has Been Closed");
                    output.printf("\tAccount:          %d\n", accounts[i].getAccountNumber());
                    output.printf("\tName:             %s %s\n", accounts[i].getCustomer().getFirstName(), accounts[i].getCustomer().getLastName());
                    output.printf("\tClosing Balance:  %.2f\n\n", accounts[i].getAccountBalance());

                    // Makes a transaction record and adds it to the array
                    transactions[MocsBankTransaction.getNumTransactions()] = new MocsBankTransaction("CLOSEACCOUNT", ID, accounts[i].getAccountBalance());

                    // Shifting the array
                    for (int j = i; j < MocsBankAccount.getNumAccounts() - 1; j++) {
                        accounts[j] = accounts[j + 1];
                    }
                    Arrays.copyOf(accounts, accounts.length - 1);

                    // Reducing the number of accounts
                    MocsBankAccount.decrementNumAccounts();
                    return;
                }
            }
            // Output if the account wasnt found
            output.printf("\tError: cannot close account. Account #%d was not found in the system.\n\n", ID);
        }

        // Output if there are no accounts
        else{
            output.println("\tError: cannot close account. There are no open accounts in MocsBank.\n");
        }
    }

    // Method for printing a transaction report for the day
    public static void TRANSACTIONREPORT(MocsBankAccount[] accounts, MocsBankTransaction[] transactions, Scanner in, PrintWriter output, int day) {

        // Checks if there are any transactions
        if (MocsBankTransaction.getNumTransactions() >= 1){

            // Prints basic day information
            output.println("MocsBank Transaction Report");
            output.printf("\tDay:                %d\n", day + 1);
            output.printf("\t# of Transactions:  %d\n", MocsBankTransaction.getNumTransactions());

            // Scans through every transaction in order
            for (int i = 0; i < MocsBankTransaction.getNumTransactions(); i++) {
                output.println("\t-----------");

                // Prints open account and close account transactions
               if (transactions[i].getTransactionType().equals("OPENACCOUNT") || transactions[i].getTransactionType().equals("CLOSEACCOUNT")){
                   output.printf("\tTransaction #%d\n", i + 1);
                   output.printf("\t%s\n", transactions[i].getTransactionType());
                   output.printf("\t\tAccount:          %d\n", transactions[i].getAccountNumber1());
                   if (transactions[i].getTransactionType().equals("OPENACCOUNT")){
                       output.printf("\t\tOpening Balance:  %.2f\n", transactions[i].getAmount());
                   }
                   else{
                       output.printf("\t\tAmount returned:  %.2f\n", transactions[i].getAmount());
                   }
               }

               // Prints withdraw or deposit transactions
               else if (transactions[i].getTransactionType().equals("WITHDRAW") || transactions[i].getTransactionType().equals("DEPOSIT")) {
                   output.printf("\tTransaction #%d\n", i + 1);
                   output.printf("\t%s\n", transactions[i].getTransactionType());
                   output.printf("\t\tAccount:          %d\n", transactions[i].getAccountNumber1());
                   output.printf("\t\tOld Balance:      %.2f\n", transactions[i].getBalanceBefore());
                   if (transactions[i].getTransactionType().equals("WITHDRAW")){
                       output.printf("\t\tWithdraw Amount:  %.2f\n", transactions[i].getAmount());
                   }
                   else{
                       output.printf("\t\tDeposit Amount:   %.2f\n", transactions[i].getAmount());
                   }
                   output.printf("\t\tNew Balance:      %.2f\n", transactions[i].getBalanceAfter());
               }

               // Prints transfer transactions
               else{
                   output.printf("\tTransaction #%d\n", i + 1);
                   output.printf("\t%s\n", transactions[i].getTransactionType());
                   output.printf("\t\tAccount 1:        %d\n", transactions[i].getAccountNumber1());
                   output.printf("\t\tAccount 2:        %d\n", transactions[i].getAccountNumber2());
                   output.printf("\t\tOld Balance:      %.2f\n", transactions[i].getBalanceBefore());
                   output.printf("\t\tTransfer Amount:  %.2f\n", transactions[i].getAmount());
                   output.printf("\t\tNew Balance:      %.2f\n", transactions[i].getBalanceAfter());
               }
            }
            // Just a linebreak
            output.println();
        }

        // Output if there no transactions exist
        else{
            output.println("TRANSACTIONREPORT:");
            output.println("\tError: cannot print Transaction Report. There are no transactions currently in MocsBank.\n");
        }
    }

    // Method to SORT
    public static void SORT(int ID, MocsBankAccount[] accounts, int index){
        // Sorting the Students by ID, just tests if the next index ID of the array is bigger or not.
        // If the next ID is a bigger number will swap the elements.
        while (index - 1 >= 0){

            if (accounts[index].getAccountNumber() < accounts[index - 1].getAccountNumber()) {
                MocsBankAccount temp = accounts[index];
                accounts[index] = accounts[index - 1];
                accounts[index - 1] = temp;
            }

            index -= 1;
        }
    }

    // Method to binary search
    public static int binarySearch(MocsBankAccount[] array, int ID) {
        // variables for finding the proper index of a given ID
        int min = 0, mid = 0;
        int max = MocsBankAccount.getNumAccounts() - 1;

        // Changes mid according to if the value is higher or lower than the mid
        while(min <= max) {
            mid = (max+min)/2;
            if (array[mid].getAccountNumber() == ID){
                return mid;
            }
            else if (array[mid].getAccountNumber() > ID){
                max = mid - 1;
            }
            else {
                min = mid + 1;
            }
        }

        // Returns -1 if no index is found
        return -1;
    }

    // Main - where all the methods will be called from and where the program starts
    public static void main(String[] args) throws Exception {
        // Variables needed for program
        String command;

        // OPEN FILES
        // Input File:
        File inputFile = new File("MocsBank.in");
        if (!inputFile.exists()) {
            System.out.println("Input file, " + inputFile + ", does not exist.");
            System.exit(0);
        }
        // Output File:
        File outputFile = new File("MocsBank.out");

        // Scanner and printer functions to scan file and print to output file.
        Scanner in = new Scanner(inputFile);
        PrintWriter output = new PrintWriter(outputFile);

        // Saves initializing data for creating arrays and days
        MocsBankAccount[] accounts = new MocsBankAccount [in.nextInt()];
        MocsBankTransaction[] transactions = new MocsBankTransaction[in.nextInt()];
        int days = in.nextInt();

        // for loop for each day
        for (int j = 0; j < days; j++) {

            // Saves the number of commands for the day
            int commands = in.nextInt();

            // Prints the day title
            output.println("***************************************");
            output.printf("Welcome to MocsBank Day #%d\n", j + 1);
            output.println("***************************************\n");

            // Resets the number of transactions for the day
            MocsBankTransaction.setNumTransactions(0);

            // for loop for number of commands
            for (int i = 0; i < commands; i++) {

                // Saves the command
                command = in.next();

                // Reads command for printing balance
                if (command.equals("PRINTBALANCE") == true) {
                    output.println("PRINTBALANCE:");
                    PRINTBALANCE(accounts, in, output);
                }

                // Reads command for depositing money
                else if (command.equals("DEPOSIT") == true) {
                    output.println("DEPOSIT:");
                    DEPOSIT(accounts, transactions, in, output);
                }

                // Reads command for withdrawing money
                else if (command.equals("WITHDRAW") == true) {
                    output.println("WITHDRAW:");
                    WITHDRAW(accounts, transactions, in, output);
                }

                // Reads command for transfering money
                else if (command.equals("TRANSFER") == true) {
                    output.println("TRANSFER:");
                    TRANSFER(accounts, transactions, in, output);
                }

                // Reads command for closing an account
                else if (command.equals("CLOSEACCOUNT") == true) {
                    output.println("CLOSEACCOUNT:");
                    CLOSEACCOUNT(accounts, transactions, in, output);
                }

                // Reads command for opening an account
                else if (command.equals("OPENACCOUNT") == true) {
                    output.println("OPENACCOUNT:");
                    OPENACCOUNT(accounts, transactions, in, output);
                }

                // Reads command for printing transaction report
                else if (command.equals("TRANSACTIONREPORT") == true) {
                    TRANSACTIONREPORT(accounts, transactions, in, output, j);
                }

                // Command QUIT: Quit the Program
                else if (command.equals("QUIT") == true) {

                }

                // Invalid Command
                else {
                    System.out.println("Invalid Command: input invalid.");
                }

            }
        }

        // Close input and output
        in.close();
        output.close();
    }
}
