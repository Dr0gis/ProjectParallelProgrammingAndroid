package team.incorpore.dr0gis.parallelprogramingtasks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int STARTER_SIZE = 250;
    private static final int STARTER_COUNT_THREADS = 8;
    private static final int STARTER_COUNT_EXECUTION = 10;

    public static final int FIXED_THREAD_POOL = 0;
    public static final int SCHEDULED_THREAD_POOL = 1;
    public static final int CACHED_THREAD_POOL = 2;

    EditText etMatrixSize;
    AppCompatButton acbApplyMatrixSize;
    EditText etThreadsCount;
    AppCompatButton acbApplyThreadsCount;
    EditText etExecutionCount;
    AppCompatButton acbApplyExecutionCount;

    AppCompatButton acbConsistentExecution;
    AppCompatButton acbParallelFixedThreadPool;
    AppCompatButton acbParallelScheduledThreadPool;
    AppCompatButton acbParallelCachedThreadPool;

    ProgressBar pbConsistent;
    ProgressBar pbParallelFixedThreadPool;
    ProgressBar pbParallelScheduledThreadPool;
    ProgressBar pbParallelCachedThreadPool;

    TextView tvOutputConsistent;
    TextView tvOutputParallelFixedThreadPool;
    TextView tvOutputParallelScheduledThreadPool;
    TextView tvOutputParallelCachedThreadPool;

    AppCompatButton acbCheckMatrix;

    int countThreads;
    int countExecution;
    int sizeMatrix;

    Matrix matrix1;
    Matrix matrix2;

    Matrix resultConsistent;
    Matrix resultParallel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMatrixSize = findViewById(R.id.etMatrixSize);
        acbApplyMatrixSize = findViewById(R.id.acbApplyMatrixSize);
        etThreadsCount = findViewById(R.id.etThreadsCount);
        acbApplyThreadsCount = findViewById(R.id.acbApplyThreadsCount);
        etExecutionCount = findViewById(R.id.etExecutionCount);
        acbApplyExecutionCount = findViewById(R.id.acbApplyExecutionCount);

        acbConsistentExecution = findViewById(R.id.acbConsistentExecution);
        acbParallelFixedThreadPool = findViewById(R.id.acbParallelFixedThreadPool);
        acbParallelScheduledThreadPool = findViewById(R.id.acbParallelScheduledThreadPool);
        acbParallelCachedThreadPool = findViewById(R.id.acbParallelCachedThreadPool);

        pbConsistent = findViewById(R.id.pbConsistent);
        pbParallelFixedThreadPool = findViewById(R.id.pbParallelFixedThreadPool);
        pbParallelScheduledThreadPool = findViewById(R.id.pbParallelScheduledThreadPool);
        pbParallelCachedThreadPool = findViewById(R.id.pbParallelCachedThreadPool);

        tvOutputConsistent = findViewById(R.id.tvOutputConsistent);
        tvOutputParallelFixedThreadPool = findViewById(R.id.tvOutputParallelFixedThreadPool);
        tvOutputParallelScheduledThreadPool = findViewById(R.id.tvOutputParallelScheduledThreadPool);
        tvOutputParallelCachedThreadPool = findViewById(R.id.tvOutputParallelCachedThreadPool);

        acbCheckMatrix = findViewById(R.id.acbCheckMatrix);

        setCountThreads(STARTER_COUNT_THREADS);
        setMatrixes(STARTER_SIZE);
        setCountExecution(STARTER_COUNT_EXECUTION);

        etMatrixSize.setText(sizeMatrix + "");
        acbApplyMatrixSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMatrixes(Integer.parseInt(etMatrixSize.getText().toString()));
            }
        });

        etThreadsCount.setText(countThreads + "");
        acbApplyThreadsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCountThreads(Integer.parseInt(etThreadsCount.getText().toString()));
            }
        });

        etExecutionCount.setText(countExecution + "");
        acbApplyExecutionCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCountExecution(Integer.parseInt(etExecutionCount.getText().toString()));
            }
        });

        acbConsistentExecution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutputConsistent.setText("wait");
                new MultiplyMatrixСonsistent(new DoTaskListener() {
                    @Override
                    public void onSuccess(final String time) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvOutputConsistent.setText(time);
                            }
                        });
                    }
                    @Override
                    public void onError() {

                    }

                }).execute();
            }
        });

        acbParallelFixedThreadPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutputParallelFixedThreadPool.setText("wait");
                new MultiplyMatrixParallel(FIXED_THREAD_POOL, countThreads, pbParallelFixedThreadPool, new DoTaskListener() {
                    @Override
                    public void onSuccess(final String time) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvOutputParallelFixedThreadPool.setText(time);
                            }
                        });
                    }
                    @Override
                    public void onError() {

                    }
                }).execute();
            }
        });

        acbParallelScheduledThreadPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutputParallelScheduledThreadPool.setText("wait");
                new MultiplyMatrixParallel(SCHEDULED_THREAD_POOL, countThreads, pbParallelScheduledThreadPool, new DoTaskListener() {
                    @Override
                    public void onSuccess(final String time) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvOutputParallelScheduledThreadPool.setText(time);
                            }
                        });
                    }
                    @Override
                    public void onError() {

                    }
                }).execute();
            }
        });

        acbParallelCachedThreadPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutputParallelCachedThreadPool.setText("wait");
                new MultiplyMatrixParallel(CACHED_THREAD_POOL, countThreads, pbParallelCachedThreadPool, new DoTaskListener() {
                    @Override
                    public void onSuccess(final String time) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvOutputParallelCachedThreadPool.setText(time);
                            }
                        });
                    }
                    @Override
                    public void onError() {

                    }
                }).execute();
            }
        });


        acbCheckMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, resultParallel.isEquals(resultConsistent) + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setMatrixes(int size) {
        matrix1 = new Matrix(size, size);
        matrix2 = new Matrix(size, size);

        matrix1.fillRandom();
        matrix2.fillRandom();

        resultConsistent = new Matrix(size, size);
        resultParallel = new Matrix(size, size);

        sizeMatrix = size;
    }

    private void setCountThreads(int count) {
        countThreads = count;
    }

    private void setCountExecution(int count) {
        countExecution = count;
    }

    interface DoTaskListener {
        void onSuccess(String time);
        void onError();
    }

    private double getTrunclatedAverage(List<Double> numbers) {
        double sum = 0;
        double count = 0;

        Collections.sort(numbers);

        if (numbers.size() < 5) {
            return numbers.get(numbers.size() / 2);
        }

        for (int i = numbers.size() / (countExecution / 2); i < numbers.size() - numbers.size() / (countExecution / 2); ++i)
        {
            sum += numbers.get(i);
            ++count;
        }
        return count != 0 ? sum / count : 0;
    }

    class MultiplyMatrixСonsistent extends AsyncTask<Void, Integer, Void> {
        DoTaskListener listener;

        MultiplyMatrixСonsistent(DoTaskListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Double> times = new ArrayList<>();

            for (int i = 0; i < countExecution; ++i) {
                long start = System.nanoTime();
                resultConsistent = matrix1.multiplyСonsistent(matrix2);
                long end = System.nanoTime();

                times.add((double)(end - start) / 1000000000.d);
                publishProgress(i);
            }

            listener.onSuccess(getTrunclatedAverage(times) + "s");

            return null;
        }

        protected void onPreExecute() {
            pbConsistent.setMax(countExecution);
            pbConsistent.setProgress(0);
        }

        protected void onProgressUpdate(Integer... progress) {
            pbConsistent.setProgress(progress[0] + 1);
        }
    }

    class MultiplyMatrixParallel extends AsyncTask<Void, Integer, Void> {
        final int executorServiceCode;
        final int countThread;
        ProgressBar progressBar;
        DoTaskListener listener;

        MultiplyMatrixParallel(int executorServiceCode, int countThread, ProgressBar progressBar, DoTaskListener listener) {
            this.executorServiceCode = executorServiceCode;
            this.countThread = countThread;
            this.progressBar = progressBar;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Double> times = new ArrayList<>();

            for (int i = 0; i < countExecution; ++i) {
                long start = System.nanoTime();
                resultParallel = matrix1.multiplyParallel(matrix2, executorServiceCode, countThread);
                long end = System.nanoTime();

                times.add((double)(end - start) / 1000000000.d);
                publishProgress(i);
            }

            listener.onSuccess(getTrunclatedAverage(times) + "s");

            return null;
        }

        protected void onPreExecute() {
            progressBar.setMax(countExecution);
            progressBar.setProgress(0);
        }

        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0] + 1);
        }
    }
}
