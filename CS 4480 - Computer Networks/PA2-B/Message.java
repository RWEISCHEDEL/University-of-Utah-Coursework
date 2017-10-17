public class Message
{
    private String data;
    
    public Message(String inputData)
    {
        if (inputData == null)
        {
            data = "";
        }
        else if (inputData.length() > NetworkSimulator.MAXDATASIZE)
        {
            data = "";
        }
        else
        {
            data = new String(inputData);
        }
    }
           
    public boolean setData(String inputData)
    {
        if (inputData == null)
        {
            data = "";
            return false;
        }
        else if (inputData.length() > NetworkSimulator.MAXDATASIZE)
        {
            data = "";
            return false;
        }
        else
        {
            data = new String(inputData);
            return true;
        }
    }
    
    public String getData()
    {
        return data;
    }
}
