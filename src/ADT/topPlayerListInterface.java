package ADT;

public interface topPlayerListInterface<T> {

    public boolean addPlayerToList(T newPlayer);
    public boolean isEmpty();
    public String displayRanking();
}
