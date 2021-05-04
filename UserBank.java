import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserBank implements ActionListener {
    // bank user interface and options

    private static JFrame frame;
    public static NormalUser activeUser;
    public static BaseBank bank;

    public UserBank(NormalUser loggedIn) {

        bank= BaseBank.getBank();
        activeUser= loggedIn;

        //Creating Java Swing frame
        frame = new JFrame("Java Bank ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        addAButton("Transfer Money", pane);
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

                Object[] types = {"Checking", "Savings", "Securities/Investments"};
                String acc = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose which account you would like to deposit into",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        types,
                        "Checking");
                Object[] currencies = {"USD", "CA", "MX"};
                String currency = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose what currency you are depositing in",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        currencies,
                        "USD");

                if (acc == types[0]) {
                    //deposit to checking acc

                    Account ckAcc= null;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.type == AccountType.CHECKING) {
                            ckAcc=accs;
                        }
                    }
                    if (ckAcc!=null){
                        ckAcc.deposit(depositAmount,currency);
                        JOptionPane.showMessageDialog(null,"Deposited to checking account! Your balance is now: $"+ ckAcc.getBalance("USD"), "Deposits", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,"Checking account not found. No Deposit made.", "Deposits", JOptionPane.PLAIN_MESSAGE);
                    }

                } else if (acc == types[1]) {
                    //deposit to savings acc

                    Account savAcc= null;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.type == AccountType.CHECKING) {
                            savAcc=accs;
                        }
                    }
                    if (savAcc!=null){
                        savAcc.deposit(depositAmount, currency);
                        JOptionPane.showMessageDialog(null,"Deposited to savings account! Your balance is now: $"+ savAcc.getBalance("USD"), "Deposits", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,"Savings account not found. No Deposit made", "Deposits", JOptionPane.PLAIN_MESSAGE);
                    }

                } else if (acc == types[2]) {
                    //deposits to securities acc

                    Account invAcc= null;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.type == AccountType.CHECKING) {
                            invAcc=accs;
                        }
                    }
                    if (invAcc!=null){
                        invAcc.deposit(depositAmount, currency);
                        JOptionPane.showMessageDialog(null,"Deposited to savings account! Your balance is now: $"+ invAcc.getBalance("USD"), "Deposits", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,"Savings account not found. No Deposit made", "Deposits", JOptionPane.PLAIN_MESSAGE);
                    }

                }

            }

        } else if (e.getActionCommand().equals("Withdraw")) {

            double ans = JOptionPane.showConfirmDialog(null, "Are you here to withdraw?", "Click it:", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {

                String withdraw = JOptionPane.showInputDialog("Enter the amount you would like to withdraw: $");
                double takeOut = Double.parseDouble(withdraw);

                Object[] types = {"Checking", "Savings", "Securities/Investments"};
                String acc = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose which account you would like to withdraw from",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        types,
                        "Checking");
                Object[] currencies = {"USD", "CA", "MX"};
                String currency = (String)JOptionPane.showInputDialog(
                        null,
                        "Please choose what currency you want to withdraw in",
                        "Deposits",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        currencies,
                        "USD");

                if (acc == types[0]) {
                    //withdraw from checking acc

                    Account ckAcc= null;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.type == AccountType.CHECKING) {
                            ckAcc=accs;
                        }
                    }

                    if (ckAcc!=null){
                        if (ckAcc.withdraw(takeOut, currency).isSuccess()) {
                            ckAcc.withdraw(takeOut, currency);
                            JOptionPane.showMessageDialog(null, "Withdrew from checking account! Your balance is now: $" + ckAcc.getBalance("USD"), "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,"Not enough money. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"Checking account not found. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                    }

                } else if (acc == types[1]) {
                    //withdraw from savings acc

                    Account ckAcc= null;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.type == AccountType.CHECKING) {
                            ckAcc=accs;
                        }
                    }

                    if (ckAcc!=null){
                        if (ckAcc.withdraw(takeOut, currency).isSuccess()) {
                            ckAcc.withdraw(takeOut, currency);
                            JOptionPane.showMessageDialog(null, "Withdrew from savings account! Your balance is now: $" + ckAcc.getBalance("USD"), "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,"Not enough money. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"Savings account not found. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                    }

                } else if (acc == types[2]) {
                    //withdraw from securities acc

                    Account secAcc= null;
                    for (Account accs: activeUser.getAccounts()) {
                        if (accs.type == AccountType.CHECKING) {
                            secAcc=accs;
                        }
                    }

                    if (secAcc!=null){
                        if (secAcc.withdraw(takeOut, currency).isSuccess()) {
                            secAcc.withdraw(takeOut, currency);
                            JOptionPane.showMessageDialog(null, "Withdrew from securities account! Your balance is now: $" + secAcc.getBalance("USD"), "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,"Not enough money. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"Securities account not found. No Withdrawal made", "Withdrawals", JOptionPane.PLAIN_MESSAGE);
                    }

                }

            }

        } else if (e.getActionCommand().equals("View Balances")) {
            //gets balances of all user accounts

            for (Account accs : activeUser.getAccounts()) {
                if (accs.type== AccountType.SAVING) {
                    JOptionPane.showMessageDialog(null, "Your Savings account Balance is : $" + accs.balance , "Balances", JOptionPane.PLAIN_MESSAGE);
                } else if (accs.type== AccountType.CHECKING) {
                    JOptionPane.showMessageDialog(null, "Your Checking account Balance is : $" + accs.balance , "Balances", JOptionPane.PLAIN_MESSAGE);
                } else if (accs.type== AccountType.INVESTMENT) {
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
                    JOptionPane.showMessageDialog(null,"Your loan has been approved");
                    // add loan

                    //activeUser.borrowLoan(account, takeOut,"USD");
                }

            }

        } else if (e.getActionCommand().equals("Investments")) {
            double investAnswer = JOptionPane.showConfirmDialog(null, "Would you like to invest/check investments?", "Please click below:", JOptionPane.YES_NO_OPTION);


            if (investAnswer == JOptionPane.YES_OPTION) {

                //check if they have $5000+ in savings
                Object[] options = {"New investments",
                        "Check current stock",
                        "Cancel"};
                int decision = JOptionPane.showOptionDialog(frame,
                        "What would you like to do?",
                        "Investments",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (decision == JOptionPane.YES_OPTION) {
                    //shows all stocks
                    Object[] stocks = {"Apple APPL", "stock2", "stock3"};
                    String s = (String)JOptionPane.showInputDialog(
                            null,
                            "Please choose a stock to invest in",
                            "Investments",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            stocks,
                            "Apple APPL");

                } else if (decision == JOptionPane.NO_OPTION) {

                    //get all current user stock investments

                }

            }

        } else if (e.getActionCommand().equals("Transfer Money")) {
            double transferAnswer = JOptionPane.showConfirmDialog(null, "Would you like to send money to someone?", "Please click below:", JOptionPane.YES_NO_OPTION);
            //allows money to be transferred to another account

            if (transferAnswer == JOptionPane.YES_OPTION) {
                String sendAmount = JOptionPane.showInputDialog("Enter the amount you would like to send: $");
                double transferring = Double.parseDouble(sendAmount);

                //if (transferring<= user.balance)

                String recipient = JOptionPane.showInputDialog("Enter the username of the person you would like to send money to :");

                //check if user exists

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


                    bank.createAccount(activeUser, AccountType.INVESTMENT);
                    JOptionPane.showMessageDialog(null, "New Investment account opened!", "New Accounts", JOptionPane.PLAIN_MESSAGE);


                }

            }
        }

    }
}