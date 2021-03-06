import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class UserBank implements ActionListener {
    // bank user interface and options

    private static JFrame frame;
    public static NormalUser activeUser;
    public static BaseBank bank;
    private final String filePath = "storage.json";

    public UserBank(NormalUser loggedIn) {
        bank= BaseBank.getBank();
        File tmpDir = new File(filePath);
        boolean exists = tmpDir.exists();
        if (exists) {
            bank.getStorage().readStorage();
        }
        String currUsername = loggedIn.getUsername();
        activeUser= (NormalUser) bank.getStorage().getUM().getUser(currUsername);

        //Creating Java Swing frame
        frame = new JFrame("Java Bank ATM");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setBackground(Color.PINK);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                bank.getStorage().writeStorage();
                frame.dispose();
                System.exit(0);
            }
        });

        addToPane(frame.getContentPane());
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    public void addToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addAButton("View Balances", pane);
        addAButton("Withdraw", pane);
        addAButton("Deposit", pane);
        addAButton("Take out a Loan", pane);
        addAButton("Investments", pane);
        addAButton("Pay off Loan", pane);
        addAButton("New Account", pane);

    }

    private void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        container.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Deposit")) {

            double answer = JOptionPane.showConfirmDialog(null, "Are you here to deposit?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                String dep = JOptionPane.showInputDialog("Enter the amount you would like to deposit : ");
                double depositAmount = Double.parseDouble(dep);

                ArrayList<Object> typesList = new ArrayList<Object>();
                for (Account acc: activeUser.getAccounts()) {
                    typesList.add(acc.toString());
                }

                Object[] types = typesList.toArray();

                String accountStr = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose which account you would like to deposit into",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        types,
                        "Checking");

                ArrayList<Object> currencyList = new ArrayList<Object>();
                HashMap eRates =bank.getCurrencies();
                for (Object cur : eRates.keySet()){
                    currencyList.add(cur);
                }
                Object [] currencies= currencyList.toArray();


                String currency = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose what currency you are depositing in",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        currencies,
                        currencies[0]);

                //deposits to securities acc

                Account account = null;
                for (Account a: activeUser.getAccounts()) {
                    if (a.toString().equals(accountStr)) {
                        account = a;
                        break;
                    }
                }

                if (account!=null){
                    account.deposit(depositAmount, currency);
                    JOptionPane.showMessageDialog(null,"Deposited to " + account.toString() + " ! Your balance is now: $" + account.getBalance("USD"), "Deposits", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,"No Deposit made", "Deposits", JOptionPane.PLAIN_MESSAGE);
                }

            }

        } else if (e.getActionCommand().equals("Withdraw")) {

            double ans = JOptionPane.showConfirmDialog(null, "Are you here to withdraw?", "Click it:", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {

                String withdraw = JOptionPane.showInputDialog("Enter the amount you would like to withdraw: $");
                double takeOut = Double.parseDouble(withdraw);

                ArrayList<Object> typesList = new ArrayList<Object>();
                for (Account acc: activeUser.getAccounts()) {
                    typesList.add(acc.toString());
                }

                Object[] types = typesList.toArray();

                String accountStr = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose which account you would like to withdraw from",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        types,
                        "Checking");

                ArrayList<Object> currencyList= new ArrayList<Object>();
                HashMap eRates =bank.getCurrencies();
                for (Object cur : eRates.keySet()){
                    currencyList.add(cur);
                }
                Object [] currencies= currencyList.toArray();

                String currency = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose what currency you want to withdraw in",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        currencies,
                        currencies[0]);



                Account account = null;
                for (Account a: activeUser.getAccounts()) {
                    if (a.toString().equals(accountStr)) {
                        account = a;
                        break;
                    }
                }

                if (account != null) {
                    if (account.withdraw(takeOut, currency).isSuccess()) {
                        account.withdraw(takeOut, currency);
                        JOptionPane.showMessageDialog(null, "Withdrew from " + account.toString() + "! Your balance is now: $" + account.getBalance("USD"), "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,"Not enough money. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                }

            }

        } else if (e.getActionCommand().equals("View Balances")) {
            //gets balances of all the active user's accounts

            for (Account accs : activeUser.getAccounts()) {
                if (accs.getType()== AccountType.SAVING) {
                    JOptionPane.showMessageDialog(null, "Your Savings account Balance is : $" + accs.balance , "Balances", JOptionPane.PLAIN_MESSAGE);
                } else if (accs.getType()== AccountType.CHECKING) {
                    JOptionPane.showMessageDialog(null, "Your Checking account Balance is : $" + accs.balance , "Balances", JOptionPane.PLAIN_MESSAGE);
                } else if (accs.getType()== AccountType.INVESTMENT) {
                    JOptionPane.showMessageDialog(null, "Your Investment account Balance is : $" + accs.balance , "Balances", JOptionPane.PLAIN_MESSAGE);
                }
            }

        } else if (e.getActionCommand().equals("Take out a Loan")) {
            double loanAnswer = JOptionPane.showConfirmDialog(null, "Would you like to apply for a new loan?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (loanAnswer == JOptionPane.YES_OPTION) {
                String loanAmount = JOptionPane.showInputDialog("Enter the amount you would like to be loaned: $");
                double takeOut = Double.parseDouble(loanAmount);
                //asking for collateral
                JOptionPane.showMessageDialog(null,"You must enter collateral with value greater than or equal to your loan value.", "Loan warning", JOptionPane.WARNING_MESSAGE);
                String colAmount = JOptionPane.showInputDialog("Enter the value of your collateral: $");
                double collateral= Double.parseDouble(colAmount);

                if (takeOut<= collateral) {

                    Account addLoanTo = null;
                    for (Account accs : activeUser.getAccounts()) {
                        if (accs.getType()== AccountType.CHECKING) {
                            addLoanTo=accs;
                        }
                    }

                    if (addLoanTo!=null) {
                        activeUser.borrowLoan(addLoanTo, takeOut,"USD");
                        JOptionPane.showMessageDialog(null,"Your loan has been approved!");

                    } else {
                        JOptionPane.showMessageDialog(null,"There is no checking account open to deposit a loan into.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Your loan has not been approved.");
                }

            }

        } else if (e.getActionCommand().equals("Investments")) {
            double investAnswer = JOptionPane.showConfirmDialog(null, "Would you like to invest/check investments?", "Please click below:", JOptionPane.YES_NO_OPTION);

            ArrayList<Object> typesList = new ArrayList<Object>();
            for (Account acc: activeUser.getAccounts()) {
                if (acc.getType()==AccountType.INVESTMENT) {
                    typesList.add(acc.toString());
                }
            }

            Object[] types = typesList.toArray();

            String accountStr = (String)JOptionPane.showInputDialog(
                    null,
                    "Please choose which account you would like to deposit into",
                    "Deposits",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    types,
                    "Checking");

            InvestmentAccount invAcc=null;
            for (Account acc : activeUser.getAccounts()) {
                if (acc.toString().equals(accountStr)){
                    invAcc = (InvestmentAccount) acc;
                    break;
                }
            }

            if (invAcc==null) {
                JOptionPane.showMessageDialog(null,"No investment accounts found", "Investments", JOptionPane.PLAIN_MESSAGE);
                investAnswer=JOptionPane.NO_OPTION;
            }

            if (investAnswer == JOptionPane.YES_OPTION) {

                Object[] options = {"Check stock gains",
                        "Buy Stock",
                        "Sell Stock",
                        "Cancel"};
                String invChoice = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose what you would like to do",
                        "Investments",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (invChoice== options[0]) {

                    JOptionPane.showMessageDialog(null, "Your realized gains are :" + invAcc.getRealizedGain() , "Realized Gains", JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Your unrealized gains are :" + invAcc.getUnrealizedGain() , "Unrealized Gains", JOptionPane.PLAIN_MESSAGE);



                } else if (invChoice==options[1]) {
                    //shows all stocks for buying

                    ArrayList<Object> stockList= new ArrayList<Object>();
                    HashMap curStocks =bank.getStorage().getSM().getStocks();
                    if(curStocks.size() > 0){
                        for (Object stockName : curStocks.keySet()){
                            stockList.add(stockName);
                        }
                        Object [] stocks= stockList.toArray();

                        String stock = (String)JOptionPane.showInputDialog(
                                null,
                                "Please choose a stock to invest in",
                                "Investments",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                stocks,stocks[0]);

                        String choose = JOptionPane.showInputDialog("Enter the number of "+ stock + " you would like to buy : ");
                        int numStocks = Integer.parseInt(choose);

                        invAcc.buyStock(stock, numStocks);

                        JOptionPane.showMessageDialog(null, "You bought stock!" , "Stocks", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No stocks currently available." , "Stocks", JOptionPane.PLAIN_MESSAGE);
                    }


                } else if (invChoice==options[2]) {
                    //get all current user stock investments available to sell

                    ArrayList<Object> stockList= new ArrayList<Object>();
                    HashMap total =invAcc.getHoldStocks();
                    if(total.size() > 0){
                        for (Object stockName : total.keySet()){
                            stockList.add(stockName);
                        }
                        Object [] heldStocks= stockList.toArray();

                        String theStock = (String)JOptionPane.showInputDialog(
                                null,
                                "Please choose which stock you want to sell",
                                "Stocks",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                heldStocks,
                                heldStocks[0]);
                        String selling = JOptionPane.showInputDialog("Enter the number you would like to sell : ");
                        int numSell = Integer.parseInt(selling);

                        invAcc.sellStock(theStock, numSell);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "You don't have any stocks." , "Stocks", JOptionPane.PLAIN_MESSAGE);
                    }
                }

            }

        } else if (e.getActionCommand().equals("Pay off Loan")) {
            double transferAnswer = JOptionPane.showConfirmDialog(null, "Are you sure you would like to pay back loans?", "Please click below:", JOptionPane.YES_NO_OPTION);
            //allows user to pay back loans

            boolean paidOff=false;
            while (!paidOff) {
                for (LoanReceipt loan : activeUser.getLoans()){
                    for (Account accs : activeUser.getAccounts()) {
                        paidOff= bank.getStorage().getLM().payBack(activeUser,accs,loan);
                        if (paidOff) {
                            JOptionPane.showMessageDialog(null,"Your loan has been paid off!", "Loans", JOptionPane.PLAIN_MESSAGE);
                            break;
                        }
                    }
                    if (paidOff) {
                        break;
                    }
                }
                if (paidOff) {
                    break;
                }
            }

            if (!paidOff) {
                JOptionPane.showMessageDialog(null,"You did not have enough money in your accounts to pay back a loan", "Loans", JOptionPane.PLAIN_MESSAGE);
            }



        } else if (e.getActionCommand().equals("New Account")) {
            double sendAnswer = JOptionPane.showConfirmDialog(null, "Would you like to open a new account?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (sendAnswer == JOptionPane.YES_OPTION) {

                Object[] types = {"Checking", "Savings", "Securities/Investments"};
                String newAcc = (String) JOptionPane.showInputDialog(
                        null,
                        "Please choose an account type to open",
                        "New Accounts",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        types,
                        "Checking");

                if (newAcc == types[0]) {
                    //open new checking account
                    bank.createAccount(activeUser, AccountType.CHECKING);
                    JOptionPane.showMessageDialog(null, "New Checking account opened!", "New Accounts", JOptionPane.PLAIN_MESSAGE);

                } else if (newAcc == types[1]) {
                    //open new savings account
                    bank.createAccount(activeUser, AccountType.SAVING);
                    JOptionPane.showMessageDialog(null, "New Savings account opened!", "New Accounts", JOptionPane.PLAIN_MESSAGE);

                } else if (newAcc == types[2]) {
                    //open new securities account if they have 5000+ in savings

                    boolean canInvest=false;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.getType() == AccountType.SAVING && accs.balance>=5000) {
                                canInvest=true;
                                break;
                        }
                    }

                    if (canInvest) {
                        bank.createAccount(activeUser, AccountType.INVESTMENT);
                        JOptionPane.showMessageDialog(null, "New Investment account opened!", "New Accounts", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "You must have at least $5000 in a savings account to open investments account. No account opened", "New Accounts", JOptionPane.WARNING_MESSAGE);
                    }

                }

            }
        }

    }
}
