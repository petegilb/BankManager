import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserBank implements ActionListener {
    // bank user interface and options

    private static JFrame frame;

    public UserBank() {

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

        addAButton("View Balance", pane);
        addAButton("Withdraw", pane);
        addAButton("Deposit", pane);
        addAButton("Take out a Loan", pane);
        addAButton("Investments", pane);

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

                //JOptionPane.showMessageDialog(null,"Your total balance is now: $"+ user.getBalance());
            }
        } else if (e.getActionCommand().equals("View Balance")) {
            JOptionPane.showMessageDialog(null, "Balance amount is: $ 100", "Your Bank Balance", JOptionPane.PLAIN_MESSAGE);


        } else if (e.getActionCommand().equals("Take out a Loan")) {
            double loanAnswer = JOptionPane.showConfirmDialog(null, "Would you like to apply for a new loan?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (loanAnswer == JOptionPane.YES_OPTION) {
                String loanAmount = JOptionPane.showInputDialog("Enter the amount you would like to be loaned: $");
                double takeOut = Double.parseDouble(loanAmount);
                //ask for collateral and confirm its >= amt of loan
            }

        } else if (e.getActionCommand().equals("Investments")) {
            double investAnswer = JOptionPane.showConfirmDialog(null, "Would you like to invest/check investments?", "Please click below:", JOptionPane.YES_NO_OPTION);
            //check if they have $5000+ in savings

        }

    }
}