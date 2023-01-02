import java.util.*;

/**
 * Creates a power set from a given set.
 * Returns a list of multiples from a given set.
 */
public interface Sets {

    static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        if (originalSet.size() > 14) {
            throw new IllegalArgumentException("Set is too large to compute");
        }
        Set<Set<T>> sets = new HashSet<Set<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    static List<Integer> multiples(Set<Integer> set, int base) {
        List<Integer> multiples = new ArrayList<Integer>();
        for (int num : set) {
            if (num % base == 0) {
                multiples.add(num);
            }
        }
        Collections.sort(multiples);
        return multiples;
    }

}
