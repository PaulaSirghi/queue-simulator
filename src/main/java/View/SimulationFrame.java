package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private JTextField t1=new JTextField(10);
    private JTextField t2=new JTextField(10);
    private JTextField t3=new JTextField(10);
    private JTextField t4=new JTextField(10);
    private JTextField t5=new JTextField(10);
    private JTextField t6=new JTextField(10);
    private JTextField t7=new JTextField(10);
    private JLabel l1=new JLabel("numar cozi");
    private JLabel l2=new JLabel("numar clienti");
    private JLabel l3=new JLabel("timp minim de sosire");
    private JLabel l4=new JLabel("timp maxim de sosire");
    private JLabel l5=new JLabel("timp minim de servire");
    private JLabel l6=new JLabel("timp maxim de servire");
    private JLabel l7=new JLabel("timp de simulare");
    private JLabel l8=new JLabel("");
    JButton b1=new JButton("OK");
    public JPanel text_boxes_panel()
    {
        JPanel panou=new JPanel();
        panou.setLayout(new GridLayout(8, 1));
        Color c2=new Color(204,204,255);
        panou.setBackground(c2);
        panou.add(t1);
        panou.add(t2);
        panou.add(t3);
        panou.add(t4);
        panou.add(t5);
        panou.add(t6);
        panou.add(t7);
        panou.add(b1);
        return panou;
    }
    public JPanel label_panel()
    {
        JPanel panou=new JPanel();
        panou.setLayout(new GridLayout(8, 1));
        panou.add(l1);
        panou.add(l2);
        panou.add(l3);
        panou.add(l4);
        panou.add(l5);
        panou.add(l6);
        panou.add(l7);
        panou.add(l8);
        Color c=new Color(204,204,255);
        panou.setForeground(c);
        panou.setBackground(c);
        return panou;
    }
    public void labels_init()
    {
        Color c=new Color(127,0,255);
        l1.setForeground(c);
        l2.setForeground(c);
        l4.setForeground(c);
        l3.setForeground(c);
        l5.setForeground(c);
        l6.setForeground(c);
        l7.setForeground(c);
        b1.setForeground(c);
        Color c2=new Color(255,204,255);
        b1.setBackground(c2);
    }
    public SimulationFrame()
    {
        JPanel pPrinc=new JPanel();
        pPrinc.setLayout(new GridLayout(1,2));
        JPanel p2=text_boxes_panel();
        labels_init();
        JPanel p1=label_panel();
        pPrinc.add(p1);
        pPrinc.add(p2);
        this.setContentPane(pPrinc);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,500);
        this.setVisible(true);
    }

    public JTextField getT1() {
        return t1;
    }

    public JTextField getT2() {
        return t2;
    }

    public JTextField getT3() {
        return t3;
    }

    public JTextField getT4() {
        return t4;
    }

    public JTextField getT5() {
        return t5;
    }

    public JTextField getT6() {
        return t6;
    }

    public JTextField getT7() {
        return t7;
    }

    public void apasa(ActionListener mal) {
        b1.addActionListener(mal);
    }

}
