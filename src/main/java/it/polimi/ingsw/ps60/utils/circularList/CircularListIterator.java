package it.polimi.ingsw.ps60.utils.circularList;

import java.io.Serializable;

/**
 * This is a standard iterator of a circularList
 * @param <T> this is the type of the list where to iterate
 */
public class CircularListIterator<T> implements Serializable {
    private Node<T> node;
    private final CircularLinkedList<T> list;

    /**
     * The iterator allow iterate in a list
     *
     * @param list is the list in which iterate
     */
    public CircularListIterator(CircularLinkedList<T> list) {
        node = list.getHead();
        this.list = list;
    }

    /**
     * This method will get the next node of the list
     */
    public void nextNode() {
        node = node.nextNode;
    }

    /**
     * This method will return the list where the iterator is pointing
     *
     * @return the list pointed by the iterator
     */
    public CircularLinkedList<T> getList() {
        return list;
    }

    /**
     * This method will return the value of the node where the iterator is pointing
     *
     * @return the value of the node pointed by the iterator
     */
    public T get() {
        return node.getValue();
    }
}