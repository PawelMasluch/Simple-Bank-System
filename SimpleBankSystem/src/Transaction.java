import java.util.Date;

/** A class defining a transaction and functionalities connected with it. */
public class Transaction {

    /** A transaction's amount. */
    private double transactionsAmount;

    /** A transaction's date.
     * A format's example: Fri Sep 03 11:39:14 CEST 2021
     * */
    private Date TansactionsTimestamp;

    /** A transaction's description. */
    private String transactionsMemo;

    /** A target account of transaction. */
    private Account transactionsInAccount;


    /**
     * A public constructor creating a new transaction (without a transactionsMemo - a transaction's description).
     * @param transactionsAmount A transaction's amount.
     * @param transactionsInAccount A target account of transaction.
     * */
    public Transaction(double transactionsAmount, Account transactionsInAccount) {
        this.transactionsAmount = transactionsAmount;
        this.transactionsInAccount = transactionsInAccount;

        this.TansactionsTimestamp = new Date();
        this.transactionsMemo = "";
    }


    /**
     * A public constructor creating a new transaction (with a transactionsMemo - a transaction's description).
     * @param transactionsAmount A transaction's amount.
     * @param transactionsInAccount A target account of transaction.
     * @param transactionsMemo A transaction's description.
     * */
    public Transaction(double transactionsAmount, String transactionsMemo, Account transactionsInAccount) {
        this(transactionsAmount, transactionsInAccount);
        this.transactionsMemo = transactionsMemo;
    }


    /**
     * This method returns a transaction's amount.
     * @return A transaction's amount.
     * */
    public double getTransactionsAmount() {
        return this.transactionsAmount;
    }


    /**
     * This method returns (in a text format) a transaction's summary.
     * @return A transaction's summary (in a text format).
     * */
    public String getSummaryLine() {

        if(this.transactionsAmount >= 0.0){
            return String.format("%s : $%.02f : %s",
                    this.TansactionsTimestamp.toString(),
                    this.transactionsAmount,
                    this.transactionsMemo);
        }
        else{
            return String.format("%s : $(-%.02f) : %s",
                    this.TansactionsTimestamp.toString(),
                    -this.transactionsAmount,
                    this.transactionsMemo);
        }

    }
}
