package it.polimi.ingsw.ps60.utils;

public class CircularListIterator<T> {
    public Node<T> node;

    public CircularListIterator(CircularLinkedList<T> list){
        node = list.getHead();
    }

    public void nextNode(){
        node = node.nextNode;
    }
}
