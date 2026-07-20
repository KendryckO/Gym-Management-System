package ui;

import javax.swing.*;
import functional.BillingService;
import functional.MemberService;
import middleware.InputValidator;
import model.Payment;

public class Billing
{
    public Billing()
    {
        this("", 0);
    }

    public Billing(String memberName, double planPrice)
    {
        JFrame frame = new JFrame("billing");

        JLabel title = new JLabel("billing / payment");
        title.setBounds(105, 5, 150, 25);
        frame.add(title);

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

        member.setText(memberName);
        if (planPrice > 0)
        {
            amount.setText(String.valueOf(planPrice));
        }

        frame.add(member);
        frame.add(amount);
        frame.add(back);
        frame.add(pay);

        pay.addActionListener(e ->
        {
            String m = member.getText().trim();
            String a = amount.getText().trim();

            if (!InputValidator.notEmpty(m) || !InputValidator.notEmpty(a))
            {
                JOptionPane.showMessageDialog(frame, "enter member and amount");
            }
            else if (MemberService.findByName(m) == null)
            {
                JOptionPane.showMessageDialog(frame, "member not found");
            }
            else if (!InputValidator.isValidAmount(a))
            {
                JOptionPane.showMessageDialog(frame, "amount must be a number above 0");
            }
            else
            {
                Payment payment = BillingService.recordPayment(m, Double.parseDouble(a));
                JOptionPane.showMessageDialog(frame,
                        "payment recorded"
                        + "\nreceipt no: " + payment.id
                        + "\nmember: " + payment.memberName
                        + "\nplan: " + payment.plan
                        + "\namount: $" + payment.amount);
                new Dashboard();
                frame.dispose();
            }
        });

        back.addActionListener(e ->
        {
            new Dashboard();
            frame.dispose();
        });

        frame.setSize(320, 245);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
