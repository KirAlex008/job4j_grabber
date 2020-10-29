package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;

public class MaxMin{

    public <T> T maxOrMax(List<T> value, Comparator<T> comparator) {
        T temp = value.get(0);
        for (var el : value) {
            int rsl = comparator.compare(el, temp);
            if (rsl > 0) {
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
        var maximum = maxMin.maxOrMax(list, intCompMax);
        System.out.println(maximum);
        Comparator intCompMin = new Comparator() {
            @Override
            public int compare(Object o2, Object o1) {
                int first = (int) o1;
                int second = (int) o2;
                return Integer.compare(first,second);
            }
        };
        var minimum = maxMin.maxOrMax(list, intCompMin);
        System.out.println(minimum);
    }
}


