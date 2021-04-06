package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Scheduler {  //distributia clientilor in cozi
    private List<Server> servers;  //lista de cozi
    private int maxNrServers;      //numar maxim de cozi
    private int maxTasksPerServer;  //numar maxim de task-uri pe coada (numar maxim de clienti)
    private Strategy strat;  //strategia dupa care ne ghidam
    public Scheduler(int max, int maxT)
    {
        this.maxNrServers=max;
        this.maxTasksPerServer=maxT;
        this.servers=new ArrayList<>();
        for(int i=0;i<maxNrServers;i++)  //generate Q queues
        {
            BlockingQueue<Task> tasks=new ArrayBlockingQueue<>(maxTasksPerServer);
            Server s=new Server(tasks,i+1); //coada
            servers.add(s);
            Thread tr=new Thread(servers.get(servers.size()-1)); //one thread for each queue
            tr.start();
        }
        changeStrategy(SelectionPolicy.SHORTEST_TIME); //abordam strategia cu shortest time
    }

    public void dispatchTask(Task t)
    {
        strat.addTask(this.servers,t);
    }

    public List<Server> getServers(){
        return servers;
    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy== SelectionPolicy.SHORTEST_TIME){
            strat = new ConcreteStrategyTime();
        }
    }
}
