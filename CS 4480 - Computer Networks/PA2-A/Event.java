public class Event
{
    private double time;
    private int type;
    private int entity;
    private Packet packet;
    
    public Event(double t, int ty, int ent)
    {
        time = t;
        type = ty;
        entity = ent;
        packet = null;
    }
    
    public Event(double t, int ty, int ent, Packet p)
    {
        time = t;
        type = ty;
        entity = ent;
        packet = new Packet(p);
    }
            
    public boolean setTime(double t)
    {
        time = t;
        return true;
    }
    
    public boolean setType(int n)
    {
        if ((n != NetworkSimulator.TIMERINTERRUPT) &&
            (n != NetworkSimulator.FROMLAYER5) &&
            (n != NetworkSimulator.FROMLAYER3))
        {
            type = -1;
            return false;
        }
        
        type = n;
        return true;
    }
    
    public boolean setEntity(int n)
    {
        if ((n != NetworkSimulator.A) &&
            (n != NetworkSimulator.B))
        {
            entity = -1;
            return false;
        }
        
        entity = n;
        return true;
    }
    
    public boolean setPacket(Packet p)
    {
        if (p == null)
        {
            packet = null;
        }        
        else
        {
            packet = new Packet(p.getSeqnum(), p.getAcknum(),
                                p.getChecksum(), p.getPayload());
        }
        
        return true;
    }
    
    public double getTime()
    {
        return time;
    }
    
    public int getType()
    {
        return type;
    }
    
    public int getEntity()
    {
        return entity;
    }
    
    public Packet getPacket()
    {
        return packet;
    }
    
    public String toString()
    {
        return("time: " + time + "  type: " + type + "  entity: " + entity +
               "packet: " + packet);
    }
        
}
