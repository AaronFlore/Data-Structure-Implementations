import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
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

    SimpleImmutableList drop(int n);

    SimpleImmutableList take(int n);
    
    SimpleImmutableList reversed();

    SimpleImmutableList append(SimpleImmutableList other);

    SimpleImmutableList map(Function <Integer, Integer> f );

    SimpleImmutableList filter(Predicate<Object> condition);

    Boolean every(Predicate<Object> condition);

    Boolean some(Predicate<Object> condition);

    int last();
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

    public SimpleImmutableList drop(int n) {
        if (size() < n || n < 0) {
            throw new IllegalArgumentException("Argument for number of elements is too large or small");
        }
        return new EmptyList();
    }
    
    public SimpleImmutableList take(int n) {
        if (size() < n || n < 0) {
            throw new IllegalArgumentException("Argument for number of elements is too large or small");
        }
        return new EmptyList();
    }

    public SimpleImmutableList reversed() {
        return new EmptyList();
    }

    public SimpleImmutableList append(SimpleImmutableList other) {
        if (other.size() == 0) {
            return this;
        }
        else if (this.size() == 0) {
            return other;
        }
        else {
        return new EmptyList();
        }
    }

    public SimpleImmutableList map(Function <Integer, Integer> f ) {
        return new EmptyList();
    }

    public SimpleImmutableList filter(Predicate<Object> condition) {
        return new EmptyList();
    }

    public Boolean every(Predicate<Object> condition) {
        return true;
    }

    public Boolean some(Predicate<Object> condition) {
        return false;
    }

    public int last() {
        throw new NoSuchElementException();
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


    public SimpleImmutableList take(int n) {
        // Keep only the first n elements of the list, removing the rest
        if (size() < n || n < 0) {
            throw new IllegalArgumentException("Argument for number of elements is too large or small");
        }
        SimpleImmutableList list = new EmptyList();
        if (n !=0) {
            for (var i = (n-1); i >= 0; i--) {
                list = new ListNode(at(i), list);
            }
        }
        return list;
    }

    public SimpleImmutableList drop(int n) {
        if (size() < n || n < 0) {
            throw new IllegalArgumentException("Argument for number of elements is too large or small");
        }
        SimpleImmutableList list = new EmptyList();
        for (var i = (size() -1); i >= n; i--) {
            list = new ListNode(at(i), list);
        }
        return list;
    }

    public SimpleImmutableList reversed() {
        SimpleImmutableList list = new EmptyList();
        if (size() != 0) {
        for (var i = 0; i < size(); i++) {
            list = new ListNode(at(i), list);
            }
        }
        return list;
    }

    public SimpleImmutableList append(SimpleImmutableList other) {
        SimpleImmutableList list = new EmptyList();
        if (size() != 0) {
            for (var i = (size()-1); i >= 0; i--) {
                list = new ListNode(at(i), list);
                }
            }
        if (other.size() !=0) {
            for (var i = (other.size()-1); i >= 0; i--) {
                list = new ListNode(other.at(i), list);
                }
            }
        return list;
    }

    public SimpleImmutableList map(Function <Integer, Integer> f ) {
        // Replace all items x in this list with f(x)
        SimpleImmutableList list = new EmptyList();
        if (size() != 0) {
            for (var i = (size()-1); i >= 0; i--) {
                int mappedNumber = ((Number)at(i)).intValue();
                list = new ListNode(f.apply(mappedNumber), list);
                }
            }
        return list;
    }

    public SimpleImmutableList filter(Predicate<Object> condition) {
        SimpleImmutableList list = new EmptyList();
        if (size() != 0) {
            for (var i = (size()-1); i >= 0; i--) {
                int checkNumber = ((Number)at(i)).intValue();
                if (condition.test(checkNumber)) {
                    list = new ListNode(checkNumber, list);
                }
            }
        }
        return list;
    }

    public Boolean every(Predicate<Object> condition) {
        Boolean every = true;
        if (size() != 0) {
            for (var i = (size()-1); i >= 0; i--) {
                int checkNumber = ((Number)at(i)).intValue();
                if (!condition.test(checkNumber)) {
                    every = false;
                }
            }
        }
        return every;
    }

    public Boolean some(Predicate<Object> condition) {
        Boolean every = false;
        if (size() != 0) {
            for (var i = (size()-1); i >= 0; i--) {
                int checkNumber = ((Number)at(i)).intValue();
                if (condition.test(checkNumber)) {
                    every = true;
                }
            }
        }
        return every;
    }

    public int last() {
        int lastNumber = ((Number)at(size()-1)).intValue();
        return(lastNumber);
    }
}

