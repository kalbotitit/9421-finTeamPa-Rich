package finlab;

/**
 * Node class of nodes in the stack and queue
 * @param <E>
 */
public class Node<E> {

    private E element;
    private Node<E> next;

    Node(){}

    Node(E e, Node<E> p){
        element = e;
        next = p;
    }
    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> prev) {
        this.next = prev;
    }

}
