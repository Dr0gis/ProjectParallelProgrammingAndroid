package team.incorpore.dr0gis.parallelprogramingtasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by dr0gi on 29.11.2017.
 */

public class Matrix {
    private int row;
    private int column;
    private int[][] array;

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        this.array = new int[row][column];
    }

    public Matrix multiplyСonsistent(Matrix matrix) {
        if (this.column != matrix.row)
        {
            throw new IllegalArgumentException("Columns first matrix no equals rows second matrix");
        }

        Matrix resultMatrix = new Matrix(this.row, matrix.column);

        for (int i = 0; i < resultMatrix.row; ++i) {
            for (int j = 0; j < resultMatrix.column; ++j) {
                resultMatrix.array[i][j] = 0;
                for (int l = 0; l < resultMatrix.row; ++l) {
                    resultMatrix.array[i][j] += this.array[i][l] * matrix.array[l][j];
                }
            }
        }

        return resultMatrix;
    }

    public Matrix multiplyParallel(final Matrix matrix, int executorServiceCode, int coutThread) {
        if (this.column != matrix.row)
        {
            throw new IllegalArgumentException("Columns first matrix no equals rows second matrix");
        }

        ExecutorService executorService = null;

        switch (executorServiceCode) {
            case MainActivity.FIXED_THREAD_POOL:
                executorService = Executors.newFixedThreadPool(coutThread);
                break;

            case MainActivity.SCHEDULED_THREAD_POOL:
                executorService = Executors.newScheduledThreadPool(coutThread);
                break;

            case MainActivity.CACHED_THREAD_POOL:
                executorService = Executors.newCachedThreadPool();
                break;
        }

        List<Callable<Boolean>> tasks = new ArrayList<>();

        final Matrix resultMatrix = new Matrix(Matrix.this.row, matrix.column);

        for (int i = 0; i < resultMatrix.row; ++i) {
            for (int j = 0; j < resultMatrix.column; ++j) {
                final int finalI = i;
                final int finalJ = j;
                tasks.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        resultMatrix.array[finalI][finalJ] = 0;
                        for (int l = 0; l < resultMatrix.row; ++l) {
                            resultMatrix.array[finalI][finalJ] += Matrix.this.array[finalI][l] * matrix.array[l][finalJ];
                        }
                        return true;
                    }
                });
            }
        }

        try {
            List<Future<Boolean>> futures = executorService.invokeAll(tasks);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();

        return resultMatrix;
    }



    public void fillRandom() {
        Random random = new Random();
        for (int i = 0; i < this.row; ++i) {
            for (int j = 0; j < this.column; ++j) {
                array[i][j] = random.nextInt(10);
            }
        }
    }

    public boolean isEquals(Matrix matrix) {
        boolean result = false;
        for (int i = 0; i < matrix.row; ++i) {
            for (int j = 0; j < matrix.column; ++j) {
                result = matrix.array[i][j] == this.array[i][j];
            }
        }
        return result;
    }
}
