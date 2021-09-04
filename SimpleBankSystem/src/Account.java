import java.util.ArrayList;

/**
 * A class defining an account and methods connected with it.
 */
public class Account {

    /**
     * Account's name.
     */
    private String accountsName;

    /**
     * Account's UUID (Universal and Unique Identifier).
     */
    private String accountsUUID;

    /**
     * An account's holder (owner).
     */
    private User holder;

    /**
     * An account's list of transactions.
     */
    private ArrayList<Transaction> transactions;


    /**
     * A public constructor creating an account.
     *
     * @param accountsName    Account's name.
     * @param holder  Account's UUID (Universal and Unique Identifier).
     * @param theBank The bank in which an account is created.
     */
    public Account(String accountsName, User holder, Bank theBank) {
        this.accountsName = accountsName;
        this.holder = holder;

        this.accountsUUID = theBank.getNewAccountUUID();

        this.transactions = new ArrayList<Transaction>();
    }


    /**
     * This method returns an account's UUID (Universal and Unique Identifier).
     *
     * @return An account's UUID (Universal and Unique Identifier).
     */
    public String getAccountsUUID() {
        return this.accountsUUID;
    }


    /**
     * This method returns an account's state summary.
     *
     * @return An account's state summary.
     */
    public String getSummaryLine() {

        double balance = this.getBalance();

        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.accountsUUID, balance, this.accountsName);
        } else {
            return String.format("%s : $(-%.02f) : %s", this.accountsUUID, balance, this.accountsName);
        }

    }


    /**
     * This method returns an account's current balance.
     *
     * @return An account's current balance.
     */
    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : this.transactions) {
            balance += t.getTransactionsAmount();
        }

        return balance;
    }


    /**
     * This method prints an account's transactions history.
     */
    public void printTransactionsHistory() {
        System.out.printf("\nTransaction history for account %s\n",
                this.accountsUUID);

        for (int t = this.transactions.size() - 1; t >= 0; --t) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }

        System.out.println();
    }


    /**
     * This method creates a new transaction to a target (this) account.
     *
     * @param amount A transaction's amount.
     * @param memo   An optional transaction's description.
     * @return A new transaction to a target account.
     */
    public Transaction createTransaction(double amount, String memo) {
        return new Transaction(amount, memo, this);
    }


    /**
     * This method adds a new transaction to an account's transactions list.
     * This method uses an another method createTransaction to create transaction.
     *
     * @param transaction A transaction to be considered.
     */
    public void addTransaction(Transaction transaction) {

        this.transactions.add(
                transaction
        );

    }
}
