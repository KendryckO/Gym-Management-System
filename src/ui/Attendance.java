package ui;

import javax.swing.*;
public class Attendance
{
    public Attendance()
    {
        JFrame frame = new JFrame("attendance");

        JTextField memberName = new JTextField();

        JButton checkIn = new JButton("check in");
        JButton checkOut = new JButton("check out");
        JButton backButton = new JButton("back");

        frame.add(new JLabel("member name:")).setBounds(25, 35, 100, 25);

        memberName.setBounds(125, 35, 150, 25);

        checkIn.setBounds(30, 85, 100, 30);
        checkOut.setBounds(165, 85, 100, 30);
        backButton.setBounds(95, 135, 100, 30);

        frame.add(memberName);
        frame.add(checkIn);
        frame.add(checkOut);
        frame.add(backButton);

        checkIn.addActionListener(e ->
        {
            if (memberName.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(frame, "enter member name");
            }
            else
            {
                JOptionPane.showMessageDialog(
                        frame,
                        memberName.getText() + " checked in"
                );
            }
        });

        checkOut.addActionListener(e ->
        {
            if (memberName.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(frame, "enter member name");
            }
            else
            {
                JOptionPane.showMessageDialog(frame, memberName.getText() + " checked out");
            }
        });

        backButton.addActionListener(e ->
        {
            new Dashboard();
            frame.dispose();
        });

        frame.setSize(320, 230);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
