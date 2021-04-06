package Controller;
import Model.Server;
import Model.Scheduler;
import Model.Task;
import View.SimulationFrame;
import View.SimulationFrame2;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;


import static java.lang.Thread.sleep;

public class Simulation implements Runnable{
    private int Q;
    private int N;
    private int t_simulation;
    private int t_aMin;
    private int t_sMin;
    private int t_aMax;
    private int t_SMax;
    private Scheduler scheduler; //client distribution & queue management
    private SimulationFrame frame;  //frame for dispose simulation
    private List<Task> generatedTasks; //clients shopping
    private int medieW;
    private int[] clienti;
    private SimulationFrame2 sim;
    private String hour;

    public Simulation(int Q,int N,int t1,int t2,int t3,int t4,int t5) {
        this.frame=new SimulationFrame();
        frame.apasa(new Buton());
        this.generatedTasks=new ArrayList<Task>();
        this.Q=Q;
        this.N=N;
        this.t_simulation=t1;
        this.t_aMin=t2;
        this.t_sMin=t3;
        this.t_aMax=t4;
        this.t_SMax=t5;
        this.medieW=0;
        date();
        scheduler=new Scheduler(Q,N);
        this.clienti=new int[Q];
        for(int i=0;i<Q;i++)
            this.clienti[i]=0;
       sim=new SimulationFrame2(Q,clienti,0);
       hour="";
    }

    private void generateNRandomTasks(){
        int nr=0;
        while(nr<this.N)
        {
            int tarriv=-1;
            int tservice=-1;
            Random rand=new Random();
            while(tarriv< this.t_aMin || tarriv> this.t_aMax) //sorted list by tarriv
            {
                tarriv= rand.nextInt(t_simulation);
            }
            while(tservice< this.t_sMin || tservice> this.t_SMax){
                tservice= rand.nextInt(t_simulation);
            }
            int n=this.generatedTasks.size();
            Task task=new Task(tarriv,tservice,n+1);
            this.generatedTasks.add(task);
            nr++;
        }
        Collections.sort(generatedTasks);
        for(int i=1;i<=this.N;i++)
            this.generatedTasks.get(i-1).setID(i);
    }

    void createFile()
    {
        try {
            File myObj = new File("a.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter("a.txt",false);
            myWriter.write("");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static Simulation gen;
    public void date()
    {
        generatedTasks=new ArrayList<Task>();
        generateNRandomTasks();
        for(Task t:this.generatedTasks) {
            System.out.println(t.getID()+" "+t.getArrivalTime()+" "+t.getProcessingTime());
        }
    }

    public class Buton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                Q= Integer.parseInt(frame.getT1().getText());
                N= Integer.parseInt(frame.getT2().getText());
                t_aMin= Integer.parseInt(frame.getT3().getText());
                t_aMax= Integer.parseInt(frame.getT4().getText());
                t_sMin= Integer.parseInt(frame.getT5().getText());
                t_SMax= Integer.parseInt(frame.getT6().getText());
                t_simulation= Integer.parseInt(frame.getT7().getText());
                date();
                gen.createFile();
                scheduler=new Scheduler(Q,N);
                clienti=new int[Q];
                for(int i=0;i<Q;i++)
                    clienti[i]=0;
                Thread t=new Thread(gen);  //un thread la fiecare apasare de buton
                t.start();
                sim.rename(Q,clienti,0);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                JDialog d=new JDialog();
                JOptionPane.showMessageDialog(null, "Datele introduse nu sunt corecte", "EROARE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void queuesDisplayClosed(Server s,FileWriter myWriter) {
        if(s.getCurrent()!=null)
            if(s.getCurrent().getProcessingTime()==0)
                s.setCurrent(null);
        try {
            myWriter.write("Queue " + s.getNbr() + ": ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(s.getTasks().size()==0)
        {
            try {
                myWriter.write("closed");
                myWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void afisare(Server s, FileWriter myWriter)
    {
        try{
            for (Task t1 : s.getTasks())
                if(!t1.equals(s.getCurrent()))
                {
                    myWriter.write("(" + t1.getID() + " " + t1.getArrivalTime() + " " + t1.getProcessingTime() + "); ");
                    this.clienti[s.getNbr()-1]++;
                }
            myWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queuesDisplay(Server s,FileWriter myWriter) {
        try {
            if (s.getCurrent() != null) {
                if(s.getCurrent().getProcessingTime()!=0) {
                    this.clienti[s.getNbr()-1]=1;
                }
                else {
                    this.clienti[s.getNbr()-1]=0;
                }
                Task tn = new Task();
                tn=s.getCurrent();
                myWriter.write("Queue " + s.getNbr() + ": ");
                if(tn.getProcessingTime()!=0)
                    myWriter.write("(" + tn.getID() + " " + tn.getArrivalTime() + " " + tn.getProcessingTime() + "); ");
                else {
                    if(s.getTasks().size()==0) {
                        myWriter.write("closed");
                    }
                }
                afisare(s,myWriter);
            }
            else {
                this.clienti[s.getNbr()-1]=0;
                queuesDisplayClosed(s,myWriter);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public int numar(){
        int ct = 0;
        for (Server s : this.scheduler.getServers()) {
            if (s.getCurrent() == null)
                ct++;
        }
        return ct;
    }
    public int verif(int currentTime) {
        if (currentTime > t_simulation || this.generatedTasks.size() == 0) {
            int ct = numar();
            if ((ct == Q && this.generatedTasks.size()==0) || currentTime>t_simulation) {
                this.sim.setVisible(false);
                double medie1 = (medieW * 1.0) / N;
                FileWriter myWriter = null;
                try {
                    myWriter = new FileWriter("a.txt", true);
                    for (Server s : this.scheduler.getServers()) {
                        double medie2=s.getMedieS() * 1.0;
                        if(s.getNrClienti()!=0)
                        {
                            medie2 =medie2/ s.getNrClienti();
                        }
                        myWriter.write("Queue " + s.getNbr() + ": average serving time= " + medie2 + "\n");
                    }
                }catch (IOException e) { e.printStackTrace(); }
                try {
                    myWriter.write("average waiting time= " + medie1 + "\n");
                    myWriter.write(hour+"\n");
                    myWriter.close();
                    } catch (IOException e) { e.printStackTrace(); }
                return 1;
            }
        }
        return 0;
    }

    public void adauga(List<Task> tl,int currentTime)
    {
        int i=0;
        int ct=0;
        while(i<this.generatedTasks.size()) {
            Task t = this.generatedTasks.get(i);
            if (t.getArrivalTime() == currentTime) {
                this.scheduler.dispatchTask(t);
                tl.add(t);
                ct++;
                medieW+=t.getBefore();
                hour=hour+"secunda: "+currentTime+" clientul cu ID-ul: "+t.getID()+" ";
            }
            i++;
        }
        for(i=0;i<ct;i++)
            this.generatedTasks.remove(tl.get(i));
    }
    @Override
    public void run() {
        int currentTime=0;
        while(currentTime<=t_simulation) {
            try {
                FileWriter myWriter = new FileWriter("a.txt",true);
                myWriter.write("Time "+currentTime+"\nWaiting clients: ");
                List<Task> tl=new ArrayList<>();
                adauga(tl,currentTime);
                for (Task t:generatedTasks) {
                    myWriter.write("("+t.getID()+" "+t.getArrivalTime()+" "+t.getProcessingTime()+"); ");
                }
                myWriter.write("\n");
                for(Server s:scheduler.getServers()) {
                    queuesDisplay(s,myWriter);
                }
                sim.rename(Q,clienti,currentTime);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            currentTime++;
            if(verif(currentTime)==1) break;
            try {
                sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
    public static void main(String[] args)
    {
        gen=new Simulation(2,4,20,2,2,10,10);
        gen.createFile();
        Thread t=new Thread(gen);  //un thread la fiecare apasare de buton
        t.start();
    }
}
