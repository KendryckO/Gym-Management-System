package ui;

import javax.swing.*;
import functional.BillingService;
import functional.MemberService;
import model.Member;

public class MembershipPlan
{
    public MembershipPlan(String name, String email, String phone)
    {
        JFrame frame = new JFrame("membership plan");

        JLabel title = new JLabel("select membership plan");
        String[] plans = {"basic - $20", "standard - $30", "premium - $40"};

        JComboBox<String> planList = new JComboBox<>(plans);
        JButton confirm = new JButton("confirm");
        JButton back = new JButton("back");

        title.setBounds(85, 20, 180, 25);
        planList.setBounds(60, 65, 210, 30);
        back.setBounds(45, 120, 100, 30);
        confirm.setBounds(165, 120, 100, 30);

        frame.add(title);
        frame.add(planList);
        frame.add(back);
        frame.add(confirm);

        confirm.addActionListener(e ->
        {
            String plan = (String) planList.getSelectedItem();
            Member member = MemberService.registerMember(name, email, phone, plan);

            if (member == null)
            {
                JOptionPane.showMessageDialog(frame, "email already registered");
                new Dashboard();
                frame.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(frame,
                        "member #" + member.id + " " + member.name + " registered with " + plan);
                new Billing(member.name, BillingService.planPrice(plan));
                frame.dispose();
            }
        });

        back.addActionListener(e ->
        {
            new MemberRegistration();
            frame.dispose();
        });

        frame.setSize(330, 220);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
