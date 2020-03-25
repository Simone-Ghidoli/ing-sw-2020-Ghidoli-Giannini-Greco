package it.polimi.ingsw.ps60.utils.circularList;

public class CircularListIterator<T> {
    public Node<T> node;

    public CircularListIterator(CircularLinkedList<T> list){
        node = list.getHead();
    }

    public void nextNode(){
        node = node.nextNode;
    }

    public Node<T> getNode() {
        return node;
    }
}
