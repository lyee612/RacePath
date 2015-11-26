/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Zhen Yee
 */
public interface topPlayerListInterface<T> {

    public boolean addPlayerToList(T newPlayer);
    public boolean isEmpty();
    public String displayRanking();
}
