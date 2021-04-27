import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMinterface implements ActionListener {
    // bank user interface and options

    private static JFrame frame;

    public BMinterface() {

        //Creating Java Swing frame
        frame = new JFrame("Java Bank ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //addToPane(frame.getContentPane());
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //add bank manager abilities
    }
}