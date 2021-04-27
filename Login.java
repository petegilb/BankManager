import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public static void main(String args[]) {
        Login login = new Login();
    }

    public Login() {

        //Creating Java Swing frame for login
        panel= new JPanel();
        loginFrame = new JFrame(" Java Bank ATM");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 400);
        loginFrame.setVisible(true);
        loginFrame.add(panel);


        panel.setLayout(null);
        userLabel = new JLabel("Username");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        //user adds username text here
        userText= new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passwordLabel= new JLabel("Pin Number");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        //user adds password/pin text here
        passwordText= new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        button =new JButton("Login");
        button.setBounds(10,80,80,25);
        //Login button checks for correct username and pin
        button.addActionListener(this::actionPerformed);
        panel.add(button);

        success= new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        //get text of both text boxes
        String uText = userText.getText();
        String uPassword= passwordText.getText();

        /*
        for (int i; i<range;i++) {
             use for going through database of KV pairs
        }
        */


        if(uText.equals("username") && uPassword.equals("pass")) {
            success.setText("Welcome back to Java bank! Accessing bank information...");

            //add conditional for if acc type is cx vs bank manager

            //opens user bank interface
            new UserBank();
            loginFrame.setVisible(false);

        } else {
            success.setText("Invalid login information, please try again.");
        }
    }


}
