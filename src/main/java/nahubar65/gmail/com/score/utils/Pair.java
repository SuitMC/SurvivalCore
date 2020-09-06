package nahubar65.gmail.com.score.utils;

public class Pair<K, V> {

    private K key;

    private V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public Pair(){

    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}