import java.util.List;

public interface Completer {
    List<String> complete(String prefix);
    void add(String name, int score);
}
