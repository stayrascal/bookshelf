package com.tw.bookshelf.controller;

import java.util.Vector;

public class Algorithm {
    public static void main(String[] args) {
        Vector vector = getVector();

        System.out.print("排序前: ");
        print(vector);

        System.out.print("\n排序后: ");
        print(quickSort(vector, 0, 7));
    }

    public static Vector<Integer> getVector() {
        Vector<Integer> vector = new Vector<>(8);
        vector.add(2);
        vector.add(4);
        vector.add(9);
        vector.add(3);
        vector.add(6);
        vector.add(7);
        vector.add(1);
        vector.add(5);
        return vector;
    }

    public static void print(Vector<Integer> vector) {
        vector.forEach(number -> {
            System.out.print(" " + number);
        });
    }

    public static Vector<Integer> quickSort(Vector<Integer> vector, final int left, final int right) {
        if (left < right) {
            int key = vector.get(left);
            int low = left;
            int high = right;
            while (low < high) {
                while (low < high && key < vector.get(high)) {
                    high--;
                }
                vector.set(low, vector.get(high));
                while (low < high && key > vector.get(low)) {
                    low++;
                }
                vector.set(high, vector.get(low));
                vector.set(low, key);
                quickSort(vector, left, low - 1);
                quickSort(vector, low + 1, right);
            }
        }
        return vector;
    }

    public static Vector<Integer> heapSort(Vector<Integer> vector) {
        int length = vector.size();
        buildHeap(vector, length);

        return vector;
    }

    private static void buildHeap(Vector<Integer> vector, int length) {
        int begin = length / 2 - 1;
        for (int i = begin; i >=0; i--){
            adjustHeap(vector, length, i);
        }

    }

    private static void adjustHeap(Vector<Integer> vector, int length, int index) {
        int left = 2 * index;
        int right = 2 * index + 1;
        int largest = index;
        while (left < length || right < length){
            if (left < length && vector.get(largest) < vector.get(left)){
                largest = left;
            }
            if (right < length && vector.get(largest) < vector.get(right)){
                largest = right;
            }

            if (index != largest){
                int temp = vector.get(largest);
                vector.set(largest, vector.get(index));
                vector.set(index, temp);
                index = largest;
                left = 2 * index;
                right = 2 * index + 1;
            }else {
                break;
            }
        }
    }
}
