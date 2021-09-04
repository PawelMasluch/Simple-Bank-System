import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * A class defining a bank system user and functionalities connected with him.
 */
public class User {

    /**
     * User's name.
     */
    private String firstName;

    /**
     * User's surname.
     */
    private String lastName;

    /**
     * User's unique ID.
     */
    private String usersUUID;

    /**
     * User's pin number's hash (MD5).
     */
    private byte usersPinHash[];

    /**
     * List of user's bank accounts.
     */
    private ArrayList<Account> usersAccounts;


    /**
     * A public constructor creating a new bank system user.
     *
     * @param firstName A name of a user.
     * @param lastName  A surname of a user.
     * @param usersPin       A user's pin.
     * @param theBank   A bank the user is associated with.
     */
    public User(String firstName, String lastName, String usersPin, Bank theBank) {

        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.usersPinHash = md.digest(usersPin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException during user's pin's encryption");
            e.printStackTrace();
            System.exit(1);
        }

        // new unique unique user ID
        this.usersUUID = theBank.createNewUserUUID();

        // empty list of usersAccounts
        this.usersAccounts = new ArrayList<Account>();

        // (! - printf, not println, because of %s operators) log message
        System.out.printf("New user %s, %s, with %s ID created.\n", lastName, firstName, this.usersUUID);

    }


    /**
     * This method returns a user's first name.
     *
     * @return A user's first name.
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * This method returns a user's unique identifier (usersUUID) in a given bank.
     *
     * @return A user's unique identifier (usersUUID).
     */
    public String getUsersUUID() {
        return usersUUID;
    }


    /**
     * This method adds a new account to a user's accounts list in a given bank.
     * @param account An account to be added to a user's accounts list in a given bank.
     */
    public void addAccount(Account account) {
        this.usersAccounts.add(account);
    }


    /**
     * This method checks the matching of a given text with with a user's pin during a logging process.
     *
     * @param pin A text to be checked for matching.
     *
     * @return true if an entered pin is correct, false otherwise.
     */
    public boolean validatePIN(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.usersPinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }


    /**
     * This method prints accounts' states' summary for a given bank system user.
     */
    public void printAccountsSummary() {

        System.out.printf("\n\n%s's usersAccounts summary\n", this.firstName);

        for (int a = 0; a < this.usersAccounts.size(); ++a) {
            System.out.printf("  %d) %s\n", a + 1,
                    this.usersAccounts.get(a).getSummaryLine());

        }

        System.out.println();
    }


    /**
     * This method returns a user's number of accounts in a given bank.
     *
     * @return A user's number of usersAccounts in a given bank.
     */
    public int getNumberOfAccounts() {
        return this.usersAccounts.size();
    }


    /**
     * This method prints a history of transactions for a given user's account (in a given bank).
     *
     * @param accountIdx A position on a user's usersAccounts list connected with an account to be considered.
     */
    public void printAccountsTransactionsHistory(int accountIdx) {

        this.usersAccounts.get(accountIdx).printTransactionsHistory();

    }


    /**
     * This method returns a current balance of a given user's account (in a given bank).
     *
     * @param accountIdx A position on a user's accounts list connected with an account to be considered.
     * @return A current balance of a given user's account (in a given bank).
     */
    public double getAccountBalance(int accountIdx) {
        return this.usersAccounts.get(accountIdx).getBalance();
    }


    /**
     * This method returns a given user's account's UUID (Universal and Unique Identifier) (in a given bank).
     *
     * @param accountIdx A position on a user's accounts list connected with an account to be considered.
     * @return A given user's account's UUID (Universal and Unique Identifier) (in a given bank).
     */
    public String getAcctUUID(int accountIdx) {
        return this.usersAccounts.get(accountIdx).getAccountsUUID();
    }


    /**
     * This method adds a transaction to a corresponding account's transaction list.
     *
     * @param accountIdx A position on a user's accounts list connected with an account to be considered.
     * @param amount  An amount of transaction.
     * @param memo    An optional transaction description.
     */
    public void addAccountTransaction(int accountIdx, double amount, String memo) {

        this.usersAccounts.get(accountIdx).addTransaction(
                new Transaction(
                        amount, memo, this.usersAccounts.get(accountIdx)
                )
        );

    }
}
