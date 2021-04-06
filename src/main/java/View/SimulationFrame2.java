package View;
import javax.swing.*;
import java.awt.*;

public class SimulationFrame2 extends JFrame{
    private JLabel[] labels;
    private JTextField[] texts;
    private JTextField timp;
    private JPanel princ;
    private int q;
    private int []clienti;

    public void design(JPanel p2,JPanel p)
    {
        Color c=new Color(204,204,255);
        Color c2=new Color(127,0,255);
        princ.setLayout(new GridLayout(2,1));
        p.setLayout(new GridLayout(q,3));
        p.setBackground(c);
        p2.setBackground(c);
        this.princ.setBackground(c);
        p2.add(this.timp);
        for(int i=0;i<q;i++) {
            labels[i].setForeground(c2);
            p.add(labels[i]);
            p.add(texts[i]);
        }
        princ.add(p2);
        princ.add(p);
        this.setContentPane(princ);
    }
    public void rename(int Q,int []clienti,int timp) {
        int i=1;
        this.timp.setText(String.valueOf(timp));
        this.q=Q; this.clienti=clienti; this.labels=new JLabel[q]; this.texts=new JTextField[q];
        for(int j=0;j<q;j++) {
            this.labels[j]=new JLabel("Queue "+i);
            this.texts[j]=new JTextField("");
            i++;
        }
        for(int j=0;j<q;j++) {
            String textul="";
            for(i=0;i<clienti[j];i++)
                textul=textul+" *";
            this.texts[j].setText(textul);
        }
        JPanel p=new JPanel();
        JPanel p2=new JPanel();
        princ=new JPanel();
        design(p2,p);
        this.setVisible(true);
    }

    public void renameLabels() {
        int i=1;
        labels=new JLabel[q];
        texts=new JTextField[q];
        for(int j=0;j<q;j++)
        {
            labels[j]=new JLabel("Queue "+i);
            texts[j]=new JTextField();
            texts[j].setText("");
            i++;
        }
        for(int j=0;j<q;j++)
        {
            for(i=0;i<clienti[j];i++)
                 texts[j].setText(texts[j].getText()+" *");
        }
    }

    public SimulationFrame2(int q, int[] clienti,int time){
        this.q=q;
        this.clienti=clienti;
        labels=new JLabel[q];texts=new JTextField[q];
        timp=new JTextField(3);timp.setText(String.valueOf(time));
        renameLabels();
        JPanel p=new JPanel();
        JPanel p2=new JPanel();
        princ=new JPanel();
        design(p2,p);
        this.setSize(300,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
