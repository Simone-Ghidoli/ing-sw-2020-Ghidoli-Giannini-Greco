package it.polimi.ingsw.ps60.utils;

public class Node<T> {

    T value;
    Node<T> nextNode;

    public Node(T value){
        this.value = value;
        nextNode = null;
    }

    public T getValue() {
        return value;
    }
}
