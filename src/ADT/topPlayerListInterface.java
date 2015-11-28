package ADT;

public interface topPlayerListInterface<T> {

    public boolean addPlayerToList(T newPlayer);
    public boolean comparePlayersDuration(T newP, T existP);
    public boolean isEmpty();
    public void eliminatePlayer();
    public String displayRanking();
}
