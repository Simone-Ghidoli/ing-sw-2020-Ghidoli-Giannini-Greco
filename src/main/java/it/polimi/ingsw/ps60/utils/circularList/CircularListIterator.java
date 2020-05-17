package it.polimi.ingsw.ps60.utils.circularList;

public class CircularListIterator<T> {
    private Node<T> node;
    private final CircularLinkedList<T> list;

    public CircularListIterator(CircularLinkedList<T> list){
        node = list.getHead();
        this.list=list;
    }

    public void nextNode(){
        node = node.nextNode;
    }

    public Node<T> getNode() {
        return node;
    }

    public CircularLinkedList<T> getList() {
        return list;
    }
}
