import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class BMinterface implements ActionListener {
    // bank user interface and options

    private static JFrame frame;
    public static BaseBank bank;
    public static BankManager manager;

    public BMinterface() {

        //Creating Java Swing frame
        frame = new JFrame("Java Bank ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addToPane(frame.getContentPane());
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    private void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        container.add(button);
    }

    public void addToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addAButton("View All Transactions", pane);
        addAButton("Update Stock Prices", pane);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("View All Transactions")) {
            //print all transactions
            HashMap<Integer, Account> history = bank.getAccounts();
            for (Account account : history.values()) {
                //prints each user accounts transactions in each dialog box
                JOptionPane.showMessageDialog(null, "Transactions for account: "+ account+ " " + account.getTransactions());
            }

        } else if (e.getActionCommand().equals("Update Stock Prices")) {

            Object[] stocks = {"Apple AAPL", "stock2", "stock3"};
            String update = (String)JOptionPane.showInputDialog(
                    null,
                    "Which stock price would you like to update?",
                    "Investment Management",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    stocks,
                    "Apple APPL");

            if (update == stocks[0] ) {
                String newPrice = JOptionPane.showInputDialog("Enter the new price of this stock $");
                double price = Double.parseDouble(newPrice);
                //overwrite apple stock price
                manager.setStockPrice("APPL", price);

            } else if (update ==stocks[1]) {
                String newPrice = JOptionPane.showInputDialog("Enter the new price of this stock $");
                double price = Double.parseDouble(newPrice);
                //overwrite cost of stock2

            } else if (update == stocks[2]) {
                String newPrice = JOptionPane.showInputDialog("Enter the new price of this stock $");
                double price = Double.parseDouble(newPrice);
                //overwrite cost of stock3

            }
        }

    }
}