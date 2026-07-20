package ui;

import javax.swing.*;
import middleware.AuthService;

public class LoginScreen {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("gym login");

        JLabel title = new JLabel("gym management system");
        title.setBounds(85, 10, 200, 25);
        frame.add(title);

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton login = new JButton("login");

        username.setBounds(100, 45, 150, 25);
        password.setBounds(100, 85, 150, 25);
        login.setBounds(125, 125, 90, 30);

        frame.add(new JLabel("username:")).setBounds(25, 45, 80, 25);
        frame.add(new JLabel("password:")).setBounds(25, 85, 80, 25);
        frame.add(username);
        frame.add(password);
        frame.add(login);

        login.addActionListener(e ->
        {
            String user = username.getText().trim();
            String pass = new String(password.getPassword());

            if (user.isEmpty() || pass.isEmpty())
            {
                JOptionPane.showMessageDialog(frame, "enter username and password");
            }
            else if (AuthService.login(user, pass))
            {
                new Dashboard();
                frame.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "invalid username or password");
            }
        });

        frame.setSize(300, 230);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
