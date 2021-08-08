package com.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView1);
        if (savedInstanceState!=null)
        {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }

    }
    /**
     * Saves the contents of the TextView to restore on configuration change.
     * @param outState The bundle in which the state of the activity is saved
     * when it is spontaneously destroyed.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }


    public void startTask(View view) {
        mTextView.setText(R.string.napping);
        // Start the AsyncTask.
        new SimpleAsyncTask(mTextView).execute();

    }
    /* Use Void for the params, because this AsyncTask does not require any inputs.
     * Use Void for the progress type, because the progress is not published.
     * Use a String as the result type, because you will update the TextView with a string
     * when the AsyncTask has completed execution.
   */
    public class SimpleAsyncTask  extends AsyncTask<Void,Void, String> {
        //Class Member to store the refrence passed to Constructor of class
        //We made it weak refrence because it can  be collected by garbage collector when not needed any more
        private WeakReference<TextView> mTextView;
        //Constructor to get the text
        SimpleAsyncTask(TextView tv) {
            mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {

            // Generate a random number between 0 and 10
            Random r = new Random();
            int n = r.nextInt(11);

            // Make the task take long enough that we have
            // time to rotate the phone while it is running
            int s = n * 200;

            // Sleep for the random amount of time
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Return a String result
            return "Awake at last after sleeping for " + s + " milliseconds!";
        }
        /* When the doInBackground() method completes,
         * the return value is automatically passed to the onPostExecute() callback.
         * */
        //Let's implement the onPostExecute method to set the text of passed textView in Constructor


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mTextView.get().setText(result);
        }
    }

}