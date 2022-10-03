import java.util.NoSuchElementException;

/**
 * An implementation of a queue using an expandable array whose
 * capacity is initially 16.
 */
public class ExpandableArrayQueue implements Queue {
    private Object[] elements;
    private int size = 0;
    private int head = 0; // index of the current front item, if one exists
    private int tail = 0; // index of next item to be added
    private int capacity = 16;

    public ExpandableArrayQueue() {
        elements = new Object[capacity];
    }
    
    public int capacity() {
        return capacity;
    }

    public void expandArray() {
        Object[] copy_array = new Object[size*2];
        for (int i = 0; i < size; i++) {
            copy_array[i] = elements[(head + i) % elements.length];
        }
        head = 0;
        tail  = size;
        elements = copy_array;
        capacity = copy_array.length;
    }

    public void reduceArray() {
        if (((size - 1) < capacity / 4 ) && capacity > 16) {
            Object[] copy_array = new Object[capacity/2];
            for (int i = 0; i < size; i++) {
               copy_array[i] = elements[(head + i) % elements.length];
            head = 0;
            tail  = size;
            elements = copy_array;
            capacity = copy_array.length;
            }
        }
    }

    public void enqueue(Object item) {
        if (size == elements.length) {
            expandArray();
        }
        elements[tail] = item;
        tail = (tail + 1) % elements.length;
        size++;
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty queue");
        }
        reduceArray();
        var item = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return item;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into empty queue");
        }
        return elements[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

}