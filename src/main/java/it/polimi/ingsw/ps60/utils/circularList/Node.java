package it.polimi.ingsw.ps60.utils.circularList;

public class Node<T> {

    T value;
    Node<T> nextNode;

    /**
     * Is a simple node for a list of any type
     * @param value is a value of a the list
     */
    public Node(T value){
        this.value = value;
        nextNode = null;
    }

    /**
     * This method will get the value of the node in a list
     * @return
     */
    public T getValue() {
        return value;
    }
}
