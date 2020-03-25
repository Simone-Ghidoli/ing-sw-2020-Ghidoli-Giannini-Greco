package it.polimi.ingsw.ps60.utils.circularList;

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
