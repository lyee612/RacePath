/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Zhen Yee
 */
public interface topPlayerListInterface<T extends Comparable<T>> {
    
    public void rollDice();
    public int moveForward(int step);
    public int moveBackward(int step);
    public boolean addPlayerToList(T newDuration, T newPlayer);
}
