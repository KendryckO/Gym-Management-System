package ui;

import javax.swing.*;

public class Dashboard
{
    public Dashboard()
    {
        JFrame frame = new JFrame("gym dashboard");

        JLabel title = new JLabel("gym management system");
        title.setBounds(80, 15, 200, 30);
        frame.add(title);

        String[] names = {"add member", "billing", "class schedule", "trainer", "attendance", "equipment", "reports"};

        for (int x = 0; x < names.length; x++)
        {
            JButton button = new JButton(names[x]);
            button.setBounds(70, 55 + (x * 40), 180, 30);
            frame.add(button);

            button.addActionListener(e ->
                    JOptionPane.showMessageDialog(frame, "not done :/"));
        }

        JButton logout = new JButton("logout");
        logout.setBounds(70, 335, 180, 30);
        frame.add(logout);

        logout.addActionListener(e ->
        {
            frame.dispose();
            LoginScreen.main(new String[0]);
        });

        frame.setSize(340, 420);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}