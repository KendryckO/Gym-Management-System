package ui;

import javax.swing.*;

public class LoginScreen {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("gym login");

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton login = new JButton("login");

        username.setBounds(100, 40, 150, 25);
        password.setBounds(100, 80, 150, 25);
        login.setBounds(125, 120, 90, 30);

        frame.add(new JLabel("username:")).setBounds(25, 40, 80, 25);
        frame.add(new JLabel("password:")).setBounds(25, 80, 80, 25);
        frame.add(username);
        frame.add(password);
        frame.add(login);

        login.addActionListener(e -> JOptionPane.showMessageDialog(frame, username.getText().equals("admin") ? "Welcome" : "Error"));

        frame.setSize(300, 220);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
