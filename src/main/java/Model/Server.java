package Model;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server implements Runnable{ //coada
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod=new AtomicInteger(0);
    private Task current;
    private int nbr;
    private int medieS,nrClienti;
    private AtomicInteger before;
    public Server(BlockingQueue<Task> t, int nr){
        //initializare coada si timp de asteptare
        this.tasks=t;
        this.nbr=nr;
        this.medieS=0;
        this.nrClienti=0;
        before=new AtomicInteger(0);
    }
    public void addTask(Task newT)
    {
        if(this.current==null && this.getTasks().size()==0)
            this.setCurrent(newT);
        waitingPeriod.addAndGet(newT.getProcessingTime()) ;
        tasks.add(newT); //adaugam clientul la coada
        medieS+=newT.getProcessingTime();
        nrClienti++;
        newT.setBefore(before.intValue());
        before.addAndGet(newT.getProcessingTime());

        for(Task t:tasks)
        {
            System.out.println("task "+t.getID()+" wp "+this.getWaitingPeriod()+" cu numarul "+this.nbr);
        }
    }
    @Override
    public void run() {
        while(true){
            try {
                    Task t=this.tasks.poll();
                    this.setCurrent(t);
                    assert t!=null;
                    try{
                        if(t.getProcessingTime()==0)
                            Thread.sleep(1000);
                        while(t.getProcessingTime()!=0)
                        {
                            waitingPeriod.decrementAndGet();
                            t.setProcessingTime(t.getProcessingTime()-1);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            } catch ( NullPointerException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    public Task getCurrent() {
        return current;
    }

    public void setCurrent(Task current) {
        this.current = current;
    }

    public BlockingQueue<Task> getTasks(){
        return this.tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getNbr() {
        return nbr;
    }

    public int getMedieS() {
        return medieS;
    }

    public int getNrClienti() {
        return nrClienti;
    }
}
