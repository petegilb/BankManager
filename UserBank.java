import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserBank implements ActionListener {
    // bank user interface and options

    private static JFrame frame;
    public static User activeUser;

    public UserBank(User loggedIn) {

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
                //user.balance+=depositAmount;

                //JOptionPane.showMessageDialog(null,"Your current balance is now: $"+ user.getBalance() );
            }

        } else if (e.getActionCommand().equals("Withdraw")) {

            double ans = JOptionPane.showConfirmDialog(null, "Are you here to withdraw?", "Click it:", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                String withdraw = JOptionPane.showInputDialog("Enter the amount you would like to withdraw: $");
                double takeOut = Double.parseDouble(withdraw);
                //double amt= takeOut + user.getBalance();
                //user.setBalance(amt);

                //JOptionPane.showMessageDialog(null,"Your total balance is now: $"+ activeUser.getbalances;
            }
        } else if (e.getActionCommand().equals("View Balances")) {
            //change to get balances
            JOptionPane.showMessageDialog(null, "Balance amount is: $" , "Your Bank Balance", JOptionPane.PLAIN_MESSAGE);

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
                    JOptionPane.showMessageDialog(null,"Your loan has been accepted");
                    // add loan
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

            Object[] types = {"Checking", "Savings", "Securities"};
            String newAcc = (String)JOptionPane.showInputDialog(
                    null,
                    "Please choose an account type to open",
                    "New Accounts",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    types,
                    "Checking");

            if (newAcc == types[0]) {
                //open new checking account

            } else if (newAcc == types[1]) {
                //open new savings account

            } else if (newAcc == types[2]) {
                //open new securities account if they have 5000+ in savings
                String accAmount = JOptionPane.showInputDialog("Enter the amount you would like to transfer to securities account $");
                double bal = Double.parseDouble(accAmount);


            }

        }

    }
}