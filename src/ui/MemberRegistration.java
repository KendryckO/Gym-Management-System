package ui;

import javax.swing.*;

public class MemberRegistration
{
    public MemberRegistration()
    {
        JFrame frame = new JFrame("member registration");

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JButton next = new JButton("continue");
        JButton back = new JButton("back");

        frame.add(new JLabel("name:")).setBounds(30, 30, 80, 25);
        frame.add(new JLabel("email:")).setBounds(30, 70, 80, 25);
        frame.add(new JLabel("phone:")).setBounds(30, 110, 80, 25);

        name.setBounds(100, 30, 170, 25);
        email.setBounds(100, 70, 170, 25);
        phone.setBounds(100, 110, 170, 25);

        next.setBounds(155, 155, 115, 30);
        back.setBounds(30, 155, 90, 30);

        frame.add(name);
        frame.add(email);
        frame.add(phone);
        frame.add(next);
        frame.add(back);

        next.addActionListener(e ->
        {
            if (name.getText().isEmpty() || email.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(frame, "enter name and email");
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "member information saved");
            }
        });

        back.addActionListener(e ->
        {
            new Dashboard();
            frame.dispose();
        });

        frame.setSize(330, 250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}