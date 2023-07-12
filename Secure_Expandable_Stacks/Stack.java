import java.util.Objects;

interface Validate {
    static void ok(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    static void notNull(Object value) {
        Objects.requireNonNull(value, "Must not be null");
    }

    static void stringLength(String value, int minLength, int maxLength) {
        ok(value.length() >= minLength, "Length must be at least " + minLength);
        ok(value.length() <= maxLength, "Length must be at most " + maxLength);
    }

    static void integerRange(int value, int minimum, int maximum) {
        ok(value >= minimum, "Must be at least " + minimum);
        ok(value <= maximum, "Must be at most " + maximum);
    }
}

enum Resizing {
    DOUBLE,
    HALF
}

public class Stack {

    private String[] elements;
    private int top;

    public Stack(int capacity) {
        Validate.integerRange(capacity, 5, 100000);
        this.elements = new String[capacity];
        this.top = -1;
    }

    public void push(String item) {
        Validate.notNull(item);
        Validate.stringLength(item, 1, 200);
        if (isFull()) {
            resize(Resizing.DOUBLE);
        }
        top++;
        elements[top] = item;
    }

    public String pop() {
        if (isEmpty()) {
            throw new StackEmptyException("Stack is empty");
        } else if (top < elements.length / 4) {
            resize(Resizing.HALF);
        }
        var oldTopValue = elements[top];
        elements[top] = null;
        top--;
        return oldTopValue;
    }

    public String peek() {
        return elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == elements.length - 1;
    }

    private void resize(Resizing resizing) {
        int newSize = (resizing == Resizing.DOUBLE) ? elements.length * 2 : elements.length / 2;
        var newElements = new String[newSize];
        System.arraycopy(elements, 0, newElements, 0, top + 1);
        this.elements = newElements;
    }

    public static void main(String[] args) {
        var stack = new Stack(5);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");
        stack.push("E");
        stack.push("F");
        stack.push("G");
        stack.push("H");
        stack.push("I");
        stack.push("J");
        stack.push("K");
        System.out.println("capacity: " + stack.elements.length);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println("capacity: " + stack.elements.length);
    }

}

class StackEmptyException extends RuntimeException {
    public StackEmptyException(String message) {
        super(message);
    }
}