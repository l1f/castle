package castle;

public class Room {
    public String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;

    public Room(String description) 
    {
        this.description = description;
    }

    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
    }

    @Override
    public String toString()
    {
        return description;
    }
    
    public String getExitDesc(){
    		StringBuffer result = new StringBuffer();
    		if( northExit != null )
    			result.append("north ");
    		if( eastExit != null )
    			result.append("east ");
    		if( southExit != null )
    			result.append("south ");
    		if( westExit != null )
    			result.append("west ");
    		return result.toString();
    }
    
    public Room getExit(String direction){
    		Room exit = null;
    		if(direction.equals("north")) {
            exit = northExit;
        }
        if(equals("east")) {
            exit = eastExit;
        }
        if(equals("south")) {
            exit = southExit;
        }
        if(direction.equals("west")) {
            exit = westExit;
        }
    		return exit;
    }
}
