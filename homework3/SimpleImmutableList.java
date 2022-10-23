import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A simple immutable linked list, implement completely from scratch as a
 * singly-linked structure. There aren't too many operations, as additional will
 * be left for homework.
 * 
 * The implementation uses a sum type because I have major null-phobia. Not
 * going to pay the billion-dollar mistake, not with this one.
 */
public sealed interface SimpleImmutableList permits EmptyList, ListNode {
    int size();

    Object head();

    SimpleImmutableList tail();

    static SimpleImmutableList of(Object... items) {
        SimpleImmutableList list = new EmptyList();
        for (var i = items.length - 1; i >= 0; i--) {
            list = new ListNode(items[i], list);
        }
        return list;
    }

    static SimpleImmutableList from(Object head, SimpleImmutableList tail) {
        return new ListNode(head, tail);
    }

    Object at(int index);

    void forEach(Consumer<Object> consumer);
}

final record EmptyList() implements SimpleImmutableList {
    public int size() {
        return 0;
    }

    public Object head() {
        throw new NoSuchElementException();
    }

    public SimpleImmutableList tail() {
        throw new NoSuchElementException();
    }

    public Object at(int index) {
        throw new NoSuchElementException();
    }

    public void forEach(Consumer<Object> consumer) {
        // Intentionally empty: nothing to iterate
    }
}

final record ListNode(
        Object head, SimpleImmutableList tail) implements SimpleImmutableList {
    public int size() {
        return 1 + tail.size();
    }

    public Object at(int index) {
        return index == 0 ? head : tail.at(index - 1);
    }

    public void forEach(Consumer<Object> consumer) {
        consumer.accept(head);
        tail.forEach(consumer);
    }
}