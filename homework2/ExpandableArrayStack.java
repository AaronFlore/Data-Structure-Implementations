import java.util.NoSuchElementException;

/**
 * An implementation of a stack using an expandable array whose
 * capacity is initially 16.
 */
public class ExpandableArrayStack implements Stack {
    private Object[] elements;
    private int size = 0;
    private int capacity = 16;

    public ExpandableArrayStack() {
        elements = new Object[capacity];
    }

    public void expandArray() {
        Object[] copy_array = new Object[size*2];
        for(int i=0; i<size; i++){
            copy_array[i] = elements[i];
        }
        elements = copy_array;
        capacity = copy_array.length;
    }

    public void reduceArray() {
        if (((size - 1) < capacity / 4 ) && capacity > 16) {
            Object[] copy_array = new Object[capacity/2];
            System.arraycopy(elements, 0, copy_array, 0, copy_array.length);
            elements = copy_array;
            capacity = copy_array.length;
        }
    }

    public int capacity() {
        return capacity;
    }

    public void push(Object item) {
        if (isFull()) {
            expandArray();
        }
        elements[size++] = item;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop from empty stack");
        }
        else {
        reduceArray();
        var result = elements[size - 1];
        elements[--size] = null;
        return result;
        }
    }

    public Object peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into empty stack");
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

    // This is a bonus method that is not part of the interface
    public boolean isFull() {
        return size == elements.length;
    }

}