import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class BMinterface implements ActionListener {
    // bank user interface and options

    private static JFrame frame;
    public static BaseBank bank;
    public static BankManager manager;

    public BMinterface() {

        //Creating Java Swing frame
        frame = new JFrame("Java Bank ATM");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

    private void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        container.add(button);
    }

    public void addToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        addAButton("View All Transactions", pane);
        addAButton("Add a new stock", pane);
        addAButton("Update Stock Prices", pane);
        addAButton("Add a Currency", pane);
        addAButton("Change loan interest rate", pane);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("View All Transactions")) {
            //print all transactions
            HashMap<Integer, Account> history = bank.getAccounts();
            for (Account account : history.values()) {
                //prints each user accounts transactions in each dialog box
                JOptionPane.showMessageDialog(null, "Transactions for account: " + account + " " + account.getTransactions());
            }

        } else if (e.getActionCommand().equals("Update Stock Prices")) {

            double updateAnswer = JOptionPane.showConfirmDialog(null, "Would you like to update a stock price?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (updateAnswer==JOptionPane.YES_OPTION) {

                Object[] stocks ={" "};
                HashMap curStocks =bank.getStorage().getSM().getStocks();
                int i=0;
                for (Object stockName : curStocks.keySet()){
                    stocks[i]= stockName;
                    i++;
                }

                String update = (String) JOptionPane.showInputDialog(
                        null,
                        "Which stock price would you like to update?",
                        "Investment Management",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        stocks,
                        stocks[0]);

                String newPrice = JOptionPane.showInputDialog("Enter the new price of this stock $");
                double price = Double.parseDouble(newPrice);
                //overwrite stock price
                manager.setStockPrice(update, price);

                JOptionPane.showMessageDialog(null, "Stock price updated!", "Stock updates", JOptionPane.PLAIN_MESSAGE);

            }


        } else if (e.getActionCommand().equals("Add a new stock")) {
            double investAnswer = JOptionPane.showConfirmDialog(null, "Would you like to add a new stock?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (investAnswer == JOptionPane.YES_OPTION) {
                //lets bank manager add to stock list
                String name = JOptionPane.showInputDialog("Enter the name of this new stock");
                String theCost = JOptionPane.showInputDialog("Enter the price of this new stock $");
                double cost = Double.parseDouble(theCost);

                manager.addStock(name,cost);
                JOptionPane.showMessageDialog(null, "New Stock added!", "New Stocks", JOptionPane.PLAIN_MESSAGE);


            }

        } else if (e.getActionCommand().equals("Add a Currency")) {
            double investAnswer = JOptionPane.showConfirmDialog(null, "Would you like to add a new currency?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (investAnswer == JOptionPane.YES_OPTION) {

                String name = JOptionPane.showInputDialog("Enter the name of this new currency");
                String theRate = JOptionPane.showInputDialog("Enter the exchange rate of this new currency :");
                double rate = Double.parseDouble(theRate);

                bank.AddExchangeRate(name, rate);

                JOptionPane.showMessageDialog(null, "New Currency added!", "New Currencies", JOptionPane.PLAIN_MESSAGE);

            }

        }  else if(e.getActionCommand().equals("Change loan interest rate")){

            double investAnswer = JOptionPane.showConfirmDialog(null, "Would you like to change the loan interest rate?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (investAnswer == JOptionPane.YES_OPTION) {

                String theRate = JOptionPane.showInputDialog("Enter the new loan interest rate: ");
                double rate = Double.parseDouble(theRate);

                manager.setLoanInterestRate(rate);

                JOptionPane.showMessageDialog(null, "Loan interest rate changed!", "Loans", JOptionPane.PLAIN_MESSAGE);

            }

        }


    }

}