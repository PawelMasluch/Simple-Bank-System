import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Main application class.
 */
public class ATM {

    /**
     * This method shows a performance of all the application.
     *
     * @param args Input arguments' list.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank newBank = new Bank("Bank of Drousin");

        User newUser = newBank.addUser("John", "Doe", "1234");

        Account newAccount = new Account("Checking", newUser, newBank);
        newUser.addAccount(newAccount);
        newBank.addAccount(newAccount);

        User currentUser;

        while (true) {
            currentUser = ATM.mainMenuPrompt(newBank, scanner);

            ATM.printUserMenu(currentUser, scanner);
        }
    }


    /**
     * This method is responsible for user's logging in to a bank system process.
     *
     * @param theBank A bank the user tries to log in to.
     * @param scanner A stream in which a user enters data and the bank system sends its responses.
     *
     * @return An already logged in bank system user.
     */
    private static User mainMenuPrompt(Bank theBank, Scanner scanner) {

        String userID = "";
        String pin = "";
        User authentificatedUser = null;

        do {

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getBankName());

            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();

            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            authentificatedUser = theBank.userLogin(userID, pin);
            if (authentificatedUser == null) {
                System.out.println("\nIncorrect user ID/pin combination. " + "Please try again.");
            }

        } while (authentificatedUser == null);

        return authentificatedUser;
    }


    /**
     * TODO: for each bank operation 1-5 ask the user whether he wants to exit from this operation after a failed trial.
     * TODO: Also don't write "Welcome [name]" every time.
     * This method is responsible for making bank operations declared by a bank system user.
     *
     * @param theUser A user who makes bank operations.
     * @param scanner A stream in which a user enters data and the bank system sends its responses.
     */
    private static void printUserMenu(User theUser, Scanner scanner) {

        //theUser.printAccountsSummary();

        int chosenBankOperationNumber = 6;

        do {

            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());


            System.out.println(" 1) Show accounts' summary");
            System.out.println(" 2) Show account transaction history");
            System.out.println(" 3) Withdraw");
            System.out.println(" 4) Deposit");
            System.out.println(" 5) Transfer");
            System.out.println(" 6) Quit");


            System.out.print("Enter number: ");

            try {
                chosenBankOperationNumber = scanner.nextInt();
            }
            catch (InputMismatchException ex) {
                chosenBankOperationNumber = 6;
            }
            finally {
                if (chosenBankOperationNumber < 1 || chosenBankOperationNumber > 6) {
                    System.out.println("Invalid number. Please number 1-6.\n");
                    scanner.nextLine();
                }
            }

        } while (chosenBankOperationNumber < 1 || chosenBankOperationNumber > 6);

        switch (chosenBankOperationNumber) {

            case 1:
                theUser.printAccountsSummary();
                break;

            case 2:
                ATM.showTransactionsHistory(theUser, scanner);
                break;

            case 3:
                ATM.withdrawFunds(theUser, scanner);
                break;

            case 4:
                ATM.depositFunds(theUser, scanner);
                break;

            case 5:
                ATM.transferFunds(theUser, scanner);
                break;

            case 6:
                scanner.nextLine();
                break;

        }

        if (chosenBankOperationNumber != 6) {
            ATM.printUserMenu(theUser, scanner);
        } else {
            System.out.println("I exit from the system.");
            System.exit(0);
        }
    }


    /**
     * This method transfer funds from a given account to another one.
     * Both accounts belongs to the same user.
     *
     * @param theUser A user who makes a transfer of funds.
     * @param scanner A stream in which a user enters data and the bank system sends its responses.
     */
    private static void transferFunds(User theUser, Scanner scanner) {


        int sourceAccount = -1;
        int targetAccount = -1;
        double amount = -1.00;
        double accountBalance = 0.00;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                            "to transfer from: ",
                    theUser.getNumberOfAccounts()
            );


            try {
                sourceAccount = scanner.nextInt() - 1;
            }
            catch(InputMismatchException ex) {
                sourceAccount = -1;
            }
            finally {
                if (sourceAccount < 0 || sourceAccount >= theUser.getNumberOfAccounts()) {
                    System.out.println("Invalid account. Please try again.\n");
                    scanner.nextLine();
                }
            }

        } while (sourceAccount < 0 || sourceAccount >= theUser.getNumberOfAccounts());


        accountBalance = theUser.getAccountBalance(sourceAccount);


        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                            "to transfer to: ",
                    theUser.getNumberOfAccounts()
            );

            try{
                targetAccount = scanner.nextInt() - 1;
            }
            catch(InputMismatchException ex){
                targetAccount = -1;
            }
            finally {
                if (targetAccount < 0 || targetAccount >= theUser.getNumberOfAccounts()) {
                    System.out.println("Invalid account. Please try again.\n");
                    scanner.nextLine();
                }
            }

        } while (targetAccount < 0 || targetAccount >= theUser.getNumberOfAccounts());



        if( sourceAccount == targetAccount ){
            System.out.println( "\nSource account and target account have to be different.\n" );
        }
        else {
            do {
                System.out.printf("Enter the amount to transfer (max $%.02f): $",
                        accountBalance);

                try{
                    amount = scanner.nextDouble();
                }
                catch (InputMismatchException ex){
                    amount = -1.00;
                }
                finally {
                    if (amount < 0.00) {
                        System.out.println("Amount must be greater than zero. Please try again.\n");
                        scanner.nextLine();
                    } else if (amount > accountBalance) {
                        System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", accountBalance);
                        scanner.nextLine();
                    }
                }

            } while (amount < 0.00 || amount > accountBalance);

            if (amount > 0.00) {
                theUser.addAccountTransaction(sourceAccount, -1 * amount, String.format("Transfer to account %s", theUser.getAccountUUID(targetAccount)));
                theUser.addAccountTransaction(targetAccount, amount, String.format("Transfer to account %s", theUser.getAccountUUID(sourceAccount)));
            }
        }


    }


    /**
     * This method makes a deposit of funds to a chosen user's bank account.
     *
     * @param theUser A user who makes a deposit of funds.
     * @param scanner A stream in which a user enters data and the bank system sends its responses.
     */
    private static void depositFunds(User theUser, Scanner scanner) {


        int sourceAccount = -1;
        double amount = -1.00;
        double accountBalance = 0.00;
        String memo = "";

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                            "to deposit in: ",
                    theUser.getNumberOfAccounts()
            );

            try{
                sourceAccount = scanner.nextInt() - 1;
            }
            catch(InputMismatchException ex) {
                sourceAccount = -1;
            }
            finally {
                if (sourceAccount < 0 || sourceAccount >= theUser.getNumberOfAccounts()) {
                    System.out.println("Invalid account. Please try again.\n");
                    scanner.nextLine();
                }
            }

        } while (sourceAccount < 0 || sourceAccount >= theUser.getNumberOfAccounts());


        accountBalance = theUser.getAccountBalance(sourceAccount);


        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBalance);

            try{
                amount = scanner.nextDouble();
            }
            catch(InputMismatchException ex) {
                System.out.println("\nIncorrect amount entered. Please try again.\n");
                amount = -1.00;
            }
            finally {
                if (amount < 0.00) {
                    System.out.println("Amount must be greater than zero.\n");
                    scanner.nextLine();
                }
            }

        } while (amount < 0.00);


        scanner.nextLine();


        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();


        if(amount > 0.00){
            theUser.addAccountTransaction(sourceAccount, amount, memo);
        }

    }


    /**
     * This method allows a user to withdraw funds from his chosen bank account.
     *
     * @param theUser A user who makes a withdrawal of funds from his chosen bank account.
     * @param scanner A stream in which a user enters data and the bank system sends its responses.
     */
    private static void withdrawFunds(User theUser, Scanner scanner) {

        int targetAccount = -1;
        double amount = 0.00;
        double accountBalance = 0.00;
        String memo = "";

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                            "to withdraw from: ",
                    theUser.getNumberOfAccounts()
            );

            try {
                targetAccount = scanner.nextInt() - 1;
            }
            catch (InputMismatchException ex) {
                targetAccount = -1;
            }
            finally {
                if (targetAccount < 0 || targetAccount >= theUser.getNumberOfAccounts()) {
                    System.out.println("Invalid account. Please try again.\n");
                    scanner.nextLine();
                }
            }

        } while (targetAccount < 0 || targetAccount >= theUser.getNumberOfAccounts());


        accountBalance = theUser.getAccountBalance(targetAccount);


        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    accountBalance);

            try{
                amount = scanner.nextDouble();
            }
            catch (InputMismatchException ex){
                System.out.println("\nIncorrect amount entered. Please try again.\n");
                amount = -1.00;
            }
            finally {
                if (amount < 0.00) {
                    System.out.println("Amount must be greater than zero.\n");
                    scanner.nextLine();
                } else if (amount > accountBalance) {
                    System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", accountBalance);
                    scanner.nextLine();
                }
            }

        } while (amount < 0.00 || amount > accountBalance);


        scanner.nextLine();


        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();


        if(amount > 0.00){
            theUser.addAccountTransaction(targetAccount, -1 * amount, memo);
        }

    }


    /**
     * This method prints an information about all transactions connected with a given user's account.
     *
     * @param theUser A user whom a transaction history of a his chosen account is shown.
     * @param scanner A stream in which a user enters data and the bank system sends its responses.
     */
    private static void showTransactionsHistory(User theUser, Scanner scanner) {

        int theAccount = -1;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                            "whose transactions you want to see: ",
                    theUser.getNumberOfAccounts());

            try {
                theAccount = scanner.nextInt() - 1;
            }
            catch (InputMismatchException ex){
                theAccount = -1;
            }
            finally {
                if (theAccount < 0 || theAccount >= theUser.getNumberOfAccounts()) {
                    System.out.println("Invalid account. Please try again.\n");
                    scanner.nextLine();
                }
            }

        } while (theAccount < 0 || theAccount >= theUser.getNumberOfAccounts());


        theUser.printAccountsTransactionsHistory(theAccount);


    }


}
