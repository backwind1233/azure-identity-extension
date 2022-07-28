package com.example.demo.identity;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);


}
