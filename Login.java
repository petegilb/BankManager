import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Login implements ActionListener {
    //General base login

    private static JFrame loginFrame;
    private static JPanel panel;
    private static JButton button;
    private static JPasswordField passwordText;
    private static JTextField userText;
    private static JLabel userLabel;
    private static JLabel passwordLabel;
    private static JLabel success;
    private static BaseBank bank = new BaseBank();
    private final String filePath = "storage.json";



    public Login() {

        File tmpDir = new File(filePath);
        boolean exists = tmpDir.exists();
        if (exists) {
            bank.getStorage().readStorage();
        }

        //Creating Java Swing frame for login
        panel = new JPanel();
        loginFrame = new JFrame("Java Bank ATM");
        loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginFrame.setSize(400, 400);
        loginFrame.setVisible(true);
        loginFrame.setBackground(Color.pink);
        loginFrame.add(panel);
        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                bank.getStorage().writeStorage();
                loginFrame.dispose();
                System.exit(0);
            }
        });

        panel.setLayout(null);
        userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        //user adds username text here
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Pin Number");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        //user adds password/pin text here
        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10, 80, 160, 25);
        //Login button checks for correct username and pin
        button.addActionListener(this::actionPerformed);
        panel.add(button);

        button = new JButton("Create Account");
        button.setBounds(10, 110, 160, 25);
        // lets new user create an account
        button.addActionListener(this::actionPerformed);
        panel.add(button);

        success = new JLabel("");
        success.setBounds(10, 150, 300, 25);
        panel.add(success);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //get text of both text boxes
        String uText = userText.getText();
        String uPassword = passwordText.getText();

        if (e.getActionCommand().equals("Login")) {

            if (uText.equals("admin") && uPassword.equals("12345")) {

                success.setText("Welcome back to Java bank! Accessing bank information...");

                //opens bank manager interface
                new BMinterface();
                loginFrame.setVisible(false);

            } else if (bank.login(uText, uPassword)!= null) {
                success.setText("Welcome back to Java bank! Accessing bank information...");

                //opens user bank interface for user
                new UserBank(new NormalUser(uText, uPassword));
                loginFrame.setVisible(false);

            } else {
                success.setText("Invalid login information, please try again.");
            }

        } else if (e.getActionCommand().equals("Create Account")) {
            double answer = JOptionPane.showConfirmDialog(null, "Would you like to create an account?", "Please click below:", JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                String uName = JOptionPane.showInputDialog("Enter a username, this is case sensitive: ");
                String pass = JOptionPane.showInputDialog("Enter a password: ");
                if (bank.createUser(uName, pass) != null) {
                    bank.getStorage().writeStorage();
                    success.setText("Account has been created!");
                } else {
                    success.setText("Username is already in use");
                }

            }
        }
    }
}
