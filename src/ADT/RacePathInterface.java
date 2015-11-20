/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Zhen Yee
 */
public interface RacePathInterface<T> {
    public T[] createRacePath(int pathSize);
    public boolean addStation(T newStation);
    public boolean addStation(int newPosition, T newStation);
    public T removeStation(int givenPosition);
    public void changeStationSquence();

}
