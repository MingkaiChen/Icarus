package game.icarus.listener;

public interface Listenable<T extends Listener> {
    void registerListener(T listener);

    void unregisterListener(T listener);
}
