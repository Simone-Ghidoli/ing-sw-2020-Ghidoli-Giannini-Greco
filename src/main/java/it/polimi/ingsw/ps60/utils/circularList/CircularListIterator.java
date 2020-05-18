package it.polimi.ingsw.ps60.utils.circularList;

public class CircularListIterator<T> {
    private Node<T> node;
    private final CircularLinkedList<T> list;

    /**
     * The iterator allow iterate in a list
     * @param list
     */
    public CircularListIterator(CircularLinkedList<T> list){
        node = list.getHead();
        this.list=list;
    }

    /**
     * This method will get the next node of the list
     */
    public void nextNode(){
        node = node.nextNode;
    }

    /**
     * This method will return the node where the iterator is pointing
     * @return the node pointed by the iterator
     */
    public Node<T> getNode() {
        return node;
    }

    /**
     * This method will return the list where the iterator is pointing
     * @return the list pointed by the iterator
     */
    public CircularLinkedList<T> getList() {
        return list;
    }

    /**
     * This method will return the value of the node where the iterator is pointing
     * @return the value of the node pointed by the iterator
     */
    public T get(){
        return node.getValue();
    }
}
