package finlab;

/**
 * Linked list implementation of Stack
 * MyStack for getting the Depth first search
 * @param <E>
 */

public class MyStack<E>{
    private Node<E> head = new Node<>();
    private Node<E> tail = new Node<>();
    private int size = 0;

    /**
     * Add an element to the stack
     * @param element would be added to the stack
     */
    public void push(E element) {
        Node<E> newNode = new Node<>();
        newNode.setElement(element);
        newNode.setNext(head);
        if (head == null){
            head = newNode;
            tail = newNode;
        } else head = newNode;
        size++;
    }

    /**
     * Return and removes the top element of the stack
     * @return top element
     */
    public E pop() throws StackUnderflowException{
        if (isEmpty())
            throw new StackUnderflowException("Stack Underflow!");
        E p = head.getElement();
        head = head.getNext();
        size--;
        return p;
    }

    /**
     * Return the top element of the stack
     * @return top element
     */
    public E top() {
        return head.getElement();
    }

    /**
     * Return the size of the stack
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Return a boolean true value if the stack is empty
     * @return boolean value
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all the element in the stack
     */
    public void clear() {
        for (int node = 0; node < size; node++){
            head = head.getNext();
        }
    }

    /**
     * Convert stack into a string
     * @return string of stack
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null){
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }


}
