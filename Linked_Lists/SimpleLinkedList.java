import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A simple linked list class, implemented completely from scratch, using doubly
 * linked nodes and a cool "header" node to greatly simplify insertion and
 * deletions on the end of the list.
 */

public class SimpleLinkedList {

    private class Node {
        Object item;
        Node next;
        Node previous;

        Node(Object item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node header = new Node(null, null, null);

    private int size = 0;

    public SimpleLinkedList() {
        header.next = header.previous = header;
    }

    public int size() {
        return size;
    }

    public void addFirst(Object item) {
        addBefore(item, header.next);
    }

    public void addLast(Object item) {
        addBefore(item, header);
    }

    public void add(int index, Object item) {
        addBefore(item, (index == size ? header : nodeAt(index)));
    }

    public void remove(int index) {
        remove(nodeAt(index));
    }

    public Object get(int index) {
        return nodeAt(index).item;
    }

    public void set(int index, Object item) {
        nodeAt(index).item = item;
    }

    public int indexOf(Object item) {
        // Ah the old "linear search" pattern: a for-loop with a return in
        // the middle. Note that to iterate the actual nodes, we start at
        // header.next, since header does not hold a list item. The loop
        // terminates when we get back to the header.
        var index = 0;
        for (var node = header.next; node != header; node = node.next) {
            if (node.item.equals(item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void forEach(Consumer<Object> consumer) {
        // Iterate from the first node (the one after the header).
        for (var node = header.next; node != header; node = node.next) {
            consumer.accept(node.item);
        }
    }

    private Node nodeAt(int index) {
        // The only legal indexes are 0, 1, ... size-1.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " for size " + size);
        }
        Node node = header;
        for (var i = 0; i <= index; i++) {
            node = node.next;
        }
        return node;
    }

    private void addBefore(Object item, Node node) {
        var newNode = new Node(item, node, node.previous);
        newNode.previous.next = newNode;
        newNode.next.previous = newNode;
        size++;
    }

    private void remove(Node node) {
        if (node == header) {
            throw new NoSuchElementException();
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
        size--;
    }

    public void take(int n) {
        // Keep only the first n elements of the list, removing the rest
        if (size() < n || n < 0) {
            throw new IllegalArgumentException("Argument for number of elements is too large or small");
        } else {
            for (int i = (size() - 1); i >= n; i--) {
                remove(i);
            }
        }
    }

    public void drop(int n) {
        // Remove the first n elements of this list
        if (size() < n || n < 0) {
            throw new IllegalArgumentException("Argument for number of elements is too large or small");
        } else {
            for (int i = 0; i < n; i++) {
                remove(i);
            }
        }
    }

    public void reverse() {
        Node n = header;
        for (int i = 0; i < size() + 1; i++) {
            Node temporary = n.next;
            n.next = n.previous;
            n.previous = temporary;
            n = n.previous;
        }
    }

    public void append(SimpleLinkedList other) {
        if (this == other) {
            throw new IllegalArgumentException("Cannot append a list to itself");
        } else {
            for (int i = 0; i < other.size(); i++) {
                addLast(other.get(i));
            }
        }
    }

    public void map(Function<Integer, Integer> f) {
        // Replace all items x in this list with f(x)
        for (int i = 0; i < size(); i++) {
            int mappedNumber = ((Number) get(i)).intValue();
            set(i, f.apply(mappedNumber));
        }

    }

    public void filter(Predicate<Object> condition) {
        // Keep only the items in this list for which the predicate p holds
        for (int i = 0; i < size(); i++) {
            int checkNumber = ((Number) get(i)).intValue();
            System.out.println(checkNumber);
            if (!condition.test(checkNumber)) {
                remove(i);
                i -= 1;
            }
        }
    }

    public Object last() {
        if (size() == 0) {
            throw new NoSuchElementException("No such element");
        } else {
            return get(size() - 1);
        }
    }

    public Boolean every(Predicate<Object> condition) {
        // Return whether every item in this list satisfies predicate p
        Boolean everyElement = true;
        for (int i = 0; i < size(); i++) {
            int element = ((Number) get(i)).intValue();
            if (!condition.test(element)) {
                everyElement = false;
            }
        }
        return everyElement;
    }

    public Boolean some(Predicate<Object> condition) {
        // Return whether at least item in this list satisfies predicate p
        Boolean everyElement = false;
        for (int i = 0; i < size(); i++) {
            int element = ((Number) get(i)).intValue();
            if (condition.test(element)) {
                everyElement = true;
            }
        }
        return everyElement;
    }

    public static SimpleLinkedList of(int... arguments) {
        var list = new SimpleLinkedList();
        for (int i : arguments) {
            list.addLast(i);
        }
        return list;
    }
}