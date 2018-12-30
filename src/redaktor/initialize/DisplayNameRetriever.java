package redaktor.initialize;

public interface DisplayNameRetriever<T> {
    String getName(T valueObject);
}
