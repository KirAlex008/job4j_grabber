package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MaxMin{

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return search( value, comparator, el -> el > 0);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return search( value, comparator, el -> el < 0);
    }

    public static <T> T search(List<T> value, Comparator<T> comparator, Predicate<Integer> condition) {
        T temp = value.get(0);
        for (var el : value) {
            int rsl = comparator.compare(el, temp);
            if (condition.test(rsl)) {
                temp = el;
            }
        }
        return temp;
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(8, 3, 5, 50);
        MaxMin maxMin = new MaxMin();
        Comparator intCompMax = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            int first = (int) o1;
            int second = (int) o2;
            return Integer.compare(first, second);
        }
    };
        var maximum = maxMin.max(list, intCompMax);
        System.out.println(maximum);

        var minimum = maxMin.min(list, intCompMax);
        System.out.println(minimum);
    }
}


