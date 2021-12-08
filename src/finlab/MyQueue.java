package finlab;

/**
 * Queue for getting vertices for Breadth first search
 * @param <E>
 */
public class MyQueue<E> {

    private Node<E> head = new Node<>();
    private Node<E> tail = new Node<>();
    private int size;


    /**
     * Returns the size of queue
     * @return the size of queue
     */
    public int size(){
        return size;
    }

    /**
     * Return boolean value if the queue is empty
     * @return boolean value if the queue is empty
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Add new node in queue
     * @param e element of new node
     */
    public void enqueue(E e){
        Node<E> newNode = new Node<>();
        newNode.setElement(e);
        newNode.setNext(null);
        if (head == null){
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Returns and removes the rear node of queue
     * @return and remove rear node of queue
     * @throws QueueNullException when queue is equal to null
     */
    public E dequeue() throws QueueNullException{
        if (isEmpty()) throw new QueueNullException("Queue is null");
        E q = head.getElement();
        head = head.getNext();
        size--;
        return q;
    }

    /**
     * Returns only the element of rear node of queue
     * @return only the element of rear node of queue
     */
    public E peek(){
        return head.getElement();
    }

    /**
     * Removes all nodes in queue
     */
    public void clear() {
        for (int node = 0; node < size; node++){
            head = head.getNext();
        }
    }

    /**
     * Convert queue into string
     * @return string of queue
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
