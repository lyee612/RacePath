package ADT;

public interface RacePathInterface<T> {
 
    public boolean addStation(T newStation);
    public boolean removeStation(T station);
    public boolean isExist(String stationName);
    public String findStation(int i);
    public void movePosition(int diceValue);
    public String getCurrentStationName();
    public boolean checkWin();
     public String toString();

}
