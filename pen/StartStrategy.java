public interface StartStrategy {
    void start(Pen pen);

    void close(Pen pen);

    String mechanismName();
}
