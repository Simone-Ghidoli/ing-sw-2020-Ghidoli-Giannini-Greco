package it.polimi.ingsw.ps60.utils.circularList;

public class CircularLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    public CircularLinkedList(){
        head = tail = null;
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
    }

    public Node<T> getHead() {
        return head;
    }

    public void removeNode(T nodeToRemove){
        Node<T> node = head;
        if (node.value == nodeToRemove){
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
}