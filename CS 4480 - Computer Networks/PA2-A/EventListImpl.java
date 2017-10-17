import java.util.Vector;

public class EventListImpl implements EventList
{
    private Vector data;
    
    public EventListImpl()
    {
        data = new Vector();
    }
    
    public boolean add(Event e)
    {
        data.addElement(e);
        return true;
    }
    
    public Event removeNext()
    {
        if (data.isEmpty())
        {
            return null;
        }
    
        int firstIndex = 0;
        double first = ((Event)data.elementAt(firstIndex)).getTime();
        for (int i = 0; i < data.size(); i++)
        {
            if (((Event)data.elementAt(i)).getTime() < first)
            {
                first = ((Event)data.elementAt(i)).getTime();
                firstIndex = i;
            }
        }
        
        Event next = (Event)data.elementAt(firstIndex);
        data.removeElement(next);
    
        return next;
    }
    
    public String toString()
    {
        return data.toString();
    }

    public Event removeTimer(int entity)
    {
        int timerIndex = -1;
        Event timer = null;
        
        for (int i = 0; i < data.size(); i++)
        {
            if ((((Event)(data.elementAt(i))).getType() == 
                                           NetworkSimulator.TIMERINTERRUPT) &&
                (((Event)(data.elementAt(i))).getEntity() == entity))
            {
                timerIndex = i;
                break;
            }
        }
        
        if (timerIndex != -1)
        {
            timer = (Event)(data.elementAt(timerIndex));
            data.removeElement(timer);
        }
        
        return timer;
            
    }
    
    public double getLastPacketTime(int entityTo)
    {
        double time = 0.0;
        for (int i = 0; i < data.size(); i++)
        {
            if ((((Event)(data.elementAt(i))).getType() == 
                                           NetworkSimulator.FROMLAYER3) &&
                (((Event)(data.elementAt(i))).getEntity() == entityTo))
            {
                time = ((Event)(data.elementAt(i))).getTime();
            }
        }
    
        return time;
    }
}
