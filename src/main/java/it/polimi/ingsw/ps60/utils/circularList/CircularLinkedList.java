package it.polimi.ingsw.ps60.utils.circularList;

public class CircularLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Is a list with head and tail connected
     */
    public CircularLinkedList(){
        head = tail = null;
        size = 0;
    }

    /**
     * This method will add a node to the list
     * @param value is the value of the node
     */
    public void addNode(T value){
        Node<T> newNode = new Node<>(value);

        if(head == null){
            head = newNode;
        }
        else {
            tail.nextNode = newNode;
        }
        tail = newNode;
        newNode.nextNode = head;
        size++;
    }

    /**
     * This method will provide the head of the list
     * @return the head of the circular list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * This method will delete a node of the list
     * @param nodeToRemove is the node that has to be removed
     */
    public void removeNode(T nodeToRemove){
        Node<T> node = head;
        if (node.value == nodeToRemove){
            size--;
            if (head == tail){
                head = tail = null;
                return;
            }
            head = node.nextNode;
            tail.nextNode = head;
            return;
        }

        while (node.nextNode != head){
            if (node.nextNode.value == nodeToRemove){
                size--;
                if (node.nextNode == tail){
                    node.nextNode = head;
                    tail = node;
                }
                else {
                    node.nextNode = node.nextNode.nextNode;
                }
                return;
            }
            node = node.nextNode;
        }
    }

    /**
     * This method will provide the size of the list
     * @return the size of the list
     */
    public int getSize() {
        return size;
    }
}