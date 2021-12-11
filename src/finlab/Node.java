package finlab;

/**
 * Node class of nodes in the stack and queue
 * @param <E>
 */
public class Node<E> {

    private E element;
    private Node<E> next;
    private Node<E> prev;

    Node(){}

    Node(Node<E> p, E e, Node<E> n){
        element = e;
        next = n;
        prev = p;
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

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }
}
