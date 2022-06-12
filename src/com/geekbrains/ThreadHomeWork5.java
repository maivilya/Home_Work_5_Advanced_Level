package com.geekbrains;

public class ThreadHomeWork5 {

    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;
    private static float[] arr = new float[SIZE];

    private static float[] calculator(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        return arr;
    }

    public static void main(String[] args) {
        m1();
        m2();
    }

    private static void m1() {

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long leadTime = System.currentTimeMillis();
        calculator(arr);
        System.out.println("Время первого потока: " + (System.currentTimeMillis() - leadTime) + " мс");
    }

    private static void m2() {

        float[] array1 = new float[SIZE];
        float[] array2 = new float[HALF];
        float[] array3 = new float[HALF];

        long leadTime = System.currentTimeMillis();
        System.arraycopy(array1, 0, array2, 0, HALF);
        System.arraycopy(array1, HALF, array3, 0, HALF);

        new Thread() {
            @Override
            public void run() {
                float[] a1 = calculator(array2);
                System.arraycopy(a1, 0, array2, 0, a1.length);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                float[] a2 = calculator(array3);
                System.arraycopy(a2, 0, array3, 0, a2.length);
            }
        }.start();


        System.arraycopy(array2, 0, array1, 0, HALF);
        System.arraycopy(array3, 0, array1, HALF, HALF);
        System.out.println("Время второго потока:  " + (System.currentTimeMillis() - leadTime) + " мс");
    }

}
