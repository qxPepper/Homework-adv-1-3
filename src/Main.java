import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final int CYCLE_COUNT = 4;
        int[] borderMin;
        int[] borderMax;
        int[] sizeArray;
        int[] intArray;
        long startTime;
        long finishTime;

        borderMin = new int[]{1, 1, 1, 1};
        borderMax = new int[]{1_000, 1_000_000, 1_000, 1_000_000};
        sizeArray = new int[]{10, 1_000, 1_00_000, 10_000_000};

        for (int i = 0; i < CYCLE_COUNT; i++) {
            intArray = integerArray(sizeArray[i], borderMin[i], borderMax[i]);

            System.out.println("Диапазон случайных чисел от " + borderMin[i] + " до " + borderMax[i] +
                    ", размер массива " + sizeArray[i] + " элементов.");
            System.out.println();

            startTime = System.currentTimeMillis();
            System.out.println("Однопоточный подсчет суммы элементов массива.");
            System.out.println("Сумма элементов массива = " + sumOfValues(intArray));
            System.out.println("Среднее значение = " + calcMiddle(intArray));
            finishTime = System.currentTimeMillis();
            System.out.println("Время = " + ((double) (finishTime - startTime) / 1000) + " секунд.");

            System.out.println();

            System.out.println("Многопоточный подсчет суммы элементов массива.");

            startTime = System.currentTimeMillis();
            ArraySumTask arraySumTask = new ArraySumTask(intArray);
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            System.out.println("Сумма элементов массива = " + forkJoinPool.invoke(arraySumTask).toString());
            System.out.println("Среднее значение = " + (forkJoinPool.invoke(arraySumTask) / intArray.length));
            finishTime = System.currentTimeMillis();
            System.out.println("Время = " + ((double) (finishTime - startTime) / 1000) + " секунд.");

            System.out.println("---\n");
        }

        System.out.println("ВЫВОД:");
        System.out.println("Очень ждал, что многопоточка быстрее, но нет! " +
                "Даже на больших числах она ещё больше замедляется.");
        System.out.println("Либо с простыми вычислениями, да ещё с примитивами, такой эффект, " +
                "либо я сильно косячу! Хотя я уверен в своей правоте!");
        System.out.println("Если я не прав; Юрий, пожалуйста, разъясните!");
    }

    public static int[] integerArray(int sizeArray, int borderMin, int borderMax) {
        int[] integerArray = new int[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            integerArray[i] = (int) (Math.random() * (borderMax - borderMin)) + borderMin;
        }
        return integerArray;
    }

    public static int sumOfValues(int[] integerArray) {
        int summa = 0;
        for (int element : integerArray) {
            summa += element;
        }
        return summa;
    }

    public static int calcMiddle(int[] integerArray) {
        return integerArray.length > 0 ?
                sumOfValues(integerArray) / integerArray.length : Integer.MAX_VALUE;
    }
}
