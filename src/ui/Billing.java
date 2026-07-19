package ui;

import javax.swing.*;

public class Billing
{
    public Billing()
    {
        JFrame frame = new JFrame("billing");

        JTextField member = new JTextField();
        JTextField amount = new JTextField();
        JButton pay = new JButton("pay");
        JButton back = new JButton("back");

        frame.add(new JLabel("member:")).setBounds(30, 35, 80, 25);
        frame.add(new JLabel("amount:")).setBounds(30, 75, 80, 25);

        member.setBounds(105, 35, 160, 25);
        amount.setBounds(105, 75, 160, 25);
        back.setBounds(35, 125, 100, 30);
        pay.setBounds(165, 125, 100, 30);

        frame.add(member);
        frame.add(amount);
        frame.add(back);
        frame.add(pay);

        pay.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "payment submitted"));

        back.addActionListener(e ->
        {
            new Dashboard();
            frame.dispose();
        });

        frame.setSize(320, 220);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}