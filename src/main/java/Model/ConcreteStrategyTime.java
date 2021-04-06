package Model;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        //auto-generated method stub
        //strategie: adaugam la coada cu timpul de asteptare cel mai mic
        int mini=10000;
        for(Server s: servers)
        {
            int nr=s.getWaitingPeriod().get();
            if(nr<mini)
                mini=nr;
        }
        for(Server s:servers)
        {
            int nr=s.getWaitingPeriod().get();
            if(nr==mini)
            {
                s.addTask(t);
                break;
            }
        }
    }
}
