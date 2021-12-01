package finlab;

/**
 * Queue for getting vertices for Breadth first search
 * @param <E>
 */
public class Queue<E> {

    private Node<E> head = new Node<>();
    private Node<E> tail = new Node<>();
    private int size;

    Queue(){
        size = 0;
        head = null;
        tail = null;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

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

    public E dequeue() throws QueueNullException{
        if (isEmpty()) throw new QueueNullException("Queue is null");
        E q = head.getElement();
        head = head.getNext();
        size--;
        return q;
    }

    public E peek(){
        return head.getElement();
    }

    public void clear() {
        for (int node = 0; node < size; node++){
            head = head.getNext();
        }
    }

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
