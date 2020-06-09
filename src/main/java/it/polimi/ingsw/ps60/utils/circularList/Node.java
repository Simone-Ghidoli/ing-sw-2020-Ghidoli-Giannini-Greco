package it.polimi.ingsw.ps60.utils.circularList;

import java.io.Serializable;

/**
 * This is a simple node of a list
 * @param <T> can be all the type
 */
public class Node<T> implements Serializable {

    final T value;
    Node<T> nextNode;

    /**
     * Is a simple node for a list of any type
     *
     * @param value is a value of a the list
     */
    public Node(T value) {
        this.value = value;
        nextNode = null;
    }

    /**
     * This method will get the value of the node in a list
     *
     * @return the value of the node
     */
    public T getValue() {
        return value;
    }
}