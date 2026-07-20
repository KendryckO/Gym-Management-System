package ui;

import javax.swing.*;

public class Trainer
{
    public Trainer()
    {
        JFrame frame = new JFrame("trainer");

        JLabel title = new JLabel("assign trainer");

        String[] trainers = 
{

        };

        JComboBox<String> trainer = new JComboBox<>(trainers);

        JButton assign = new JButton("assign");
        JButton back = new JButton("back");

        title.setBounds(100, 20, 120, 25);
        trainer.setBounds(60, 60, 180, 30);
        back.setBounds(40, 120, 90, 30);
        assign.setBounds(150, 120, 90, 30);

        frame.add(title);
        frame.add(trainer);
        frame.add(back);
        frame.add(assign);

        assign.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "trainer assigned"));

        back.addActionListener(e ->
        {
            new Dashboard();
            frame.dispose();
        });

        frame.setSize(320,220);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
