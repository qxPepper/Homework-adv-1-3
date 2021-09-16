import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Integer> {
    private final int[] intArray;

    ArraySumTask(int[] intArray) {
        this.intArray = intArray;
    }

    @Override
    protected Integer compute() {
        final int range = intArray.length;
        return switch (range) {
            case 0 -> 0;
            case 1 -> intArray[0];
            case 2 -> intArray[0] + intArray[1];
            default -> forkTasks();
        };
    }

    public int forkTasks() {
        ArraySumTask task1 = new ArraySumTask(Arrays.copyOfRange(intArray, 0, intArray.length / 2));
        ArraySumTask task2 = new ArraySumTask(Arrays.copyOfRange(intArray, intArray.length / 2, intArray.length));

        invokeAll(task1, task2);

        return task1.join() + task2.join();
    }
}
