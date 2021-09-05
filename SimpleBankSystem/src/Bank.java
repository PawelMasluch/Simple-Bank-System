import java.util.ArrayList;
import java.util.Random;


/**
 * A class defining a bank and its functionalities.
 */
public class Bank {

    /**
     * Bank's name.
     */
    private String bankName;

    /**
     * Bank's users' list.
     * Definition: the user is the person having min. 1 account in the bank.
     */
    private ArrayList<User> bankUsers;

    /**
     * Bank's users' accounts' list.
     */
    private ArrayList<Account> bankAccounts;


    /**
     * A public constructor creating a bank instance.
     *
     * @param bankName A bank's name.
     */
    public Bank(String bankName) {
        this.bankName = bankName;
        this.bankUsers = new ArrayList<User>();
        this.bankAccounts = new ArrayList<Account>();
    }


    /** This method returns a bank's name.
     *
     * @return A bank's name.
     * */
    public String getBankName() {
        return this.bankName;
    }


    /** This method creates a new unique user's ID.
     *
     * @return A new unique user's ID.
     * */
    public String createNewUserUUID() {

        String UUID;

        Random random = new Random();
        int UUIDLength = 6; // UUID's length
        boolean isCurrentUUIDNonUnique;

        do {

            UUID = "";

            for (int c = 0; c < UUIDLength; ++c) {
                UUID += ((Integer) random.nextInt(10)).toString();
            }

            isCurrentUUIDNonUnique = false;
            for (User u : this.bankUsers) {
                if (UUID.compareTo(u.getUsersUUID()) == 0) {
                    isCurrentUUIDNonUnique = true;
                    break;
                }
            }

        } while (isCurrentUUIDNonUnique);


        return UUID;
    }


    /**
     * This method returns a new account's UUID (Universal and Unique Identifier).
     *
     * @return A new account's UUID (Universal and Unique Identifier).
     * */
    public String getNewAccountUUID() {
        String UUID;

        Random random = new Random();
        int UUIDLength = 10;
        boolean isCurrentUUIDNonUnique;

        do {

            UUID = "";

            for (int c = 0; c < UUIDLength; ++c) {
                UUID += ((Integer) random.nextInt(10)).toString();
            }

            isCurrentUUIDNonUnique = false;
            for (Account a : this.bankAccounts) {
                if (UUID.compareTo(a.getAccountsUUID()) == 0) {
                    isCurrentUUIDNonUnique = true;
                    break;
                }
            }

        } while (isCurrentUUIDNonUnique);


        return UUID;
    }


    /**
     * This method adds a new account to a bank's accounts' list.
     *
     * @param account A new account to be added a bank's accounts' list.
     * */
    public void addAccount(Account account) {
        this.bankAccounts.add(account);
    }


    /*
    /**
     * This method adds a new user to a bank's bankUsers' list.
     * @param
     *
    public User addUser(User newUser) {
        this.bankUsers.add(newUser);


        Account newAccount = new Account("Savings", newUser, this);

        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    */


    /**
     * This method adds a new user (with a bank account) to a bank's users' list.
     *
     * @param firstName User's name.
     * @param lastName  User's surname.
     * @param pin       User's pin code.
     *
     * @return A new bank system user.
     */
    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.bankUsers.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);

        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }


    /**
     * This method returns a user corresponding to a entered pair (userID, pin) or null if such user does not exist.
     *
     * @param userID An entered user's ID.
     * @param pin An entered user's pin.
     *
     * @return A user corresponding to a entered pair (userID, pin) or null if such user does not exist.
     * */
    public User userLogin(String userID, String pin) {

        for (User u : this.bankUsers) {
            if (u.getUsersUUID().compareTo(userID) == 0 && u.validatePIN(pin) == true) {
                return u;
            }
        }

        return null;
    }
}
