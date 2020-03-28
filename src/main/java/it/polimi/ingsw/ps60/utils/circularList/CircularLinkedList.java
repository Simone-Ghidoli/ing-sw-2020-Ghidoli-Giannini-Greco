package it.polimi.ingsw.ps60.utils.circularList;

public class CircularLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CircularLinkedList(){
        head = tail = null;
        size = 0;
    }

    public void addNode(T value){
        Node<T> newNode = new Node<T>(value);

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

    public void addNodeIfNotExists(T value){
        Node<T> newNode = new Node<T>(value);

        if (head == null) {
            addNode(value);
            return;
        }

        if (head.getValue().equals(newNode))
            return;

        Node<T> node = head.nextNode;
        while (node != head){
            if (node.getValue().equals(newNode))
                return;
            node = node.nextNode;
        }
        addNode(value);
    }

    public Node<T> getHead() {
        return head;
    }

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

    public int getSize() {
        return size;
    }
}