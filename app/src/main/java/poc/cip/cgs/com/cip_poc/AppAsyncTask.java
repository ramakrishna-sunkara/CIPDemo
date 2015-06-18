/**
 * Copyright (c) 2014 CGS Inc.
 *
 * Niaz Ahamed
 */
package poc.cip.cgs.com.cip_poc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for Async methods
 *
 * @author Niaz Ahamed
 */
public class AppAsyncTask extends AsyncTask<String, Void, String> {
    private Context itsContext;
    public ProgressDialog itsProgressDialog;
    private AsyncTaskListener itsAsyncTaskListener;
    private String itsargs, itsProgressMsg, itsApiName;
    private String REQUEST_PROPERTY = "application/x-www-form-urlencoded";
    private String REQUEST_TYPE = "POST";
    private String contentOutput;

    String CONNECTION_ERROR = "Connection error...! Please try again";

    public interface AsyncTaskListener {
        void onPreExecuteConcluded();

        void onPostExecuteConcluded(String theResult);
    }

    public AppAsyncTask(Context theContext, String theApiName,
                        String args, String theProgressMsg) {
        itsargs = args;
        itsContext = theContext;
        itsProgressMsg = theProgressMsg;
        itsProgressDialog = new ProgressDialog(theContext);
        itsApiName = theApiName;
    }

    final public void setListener(AsyncTaskListener theListener) {
        itsAsyncTaskListener = theListener;
    }

    @Override
    final protected void onPreExecute() {
        Activity activity = (Activity) itsContext;
        if (!activity.isFinishing()) {
            if (itsProgressDialog != null) {
//				itsProgressDialog.setCancelable(false);
                itsProgressDialog.setMessage(itsProgressMsg);
                itsProgressDialog
                        .setProgressStyle(ProgressDialog.STYLE_SPINNER);
                itsProgressDialog.setProgress(0);
                itsProgressDialog.show();
            }
            if (itsAsyncTaskListener != null)
                itsAsyncTaskListener.onPreExecuteConcluded();
        }
    }

    @Override
    final protected String doInBackground(String... theParams) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(itsApiName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_TYPE);
            connection.setRequestProperty("Content-Type",
                    REQUEST_PROPERTY);
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(itsargs);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
                contentOutput = response.toString();
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return contentOutput;
    }

    @Override
    final protected void onPostExecute(String theResult) {
        if (itsProgressDialog != null) {
            itsProgressDialog.cancel();
        }
        if (theResult != null) {
            if (itsAsyncTaskListener != null)
                itsAsyncTaskListener.onPostExecuteConcluded(theResult);
        } else {
            Toast.makeText(itsContext.getApplicationContext(),
                    CONNECTION_ERROR, Toast.LENGTH_LONG).show();
        }
    }
}