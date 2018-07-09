package com.example.adria.myappmvp.remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adria.myappmvp.AppConfig;
import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.task.TaskActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity
{
    EditText mLoginEditText;
    EditText mPasswordEditText;
    Button mLoginButton;
    Button mRegisterButton;

    TextView mSkipTextView;

    SessionManager mSessionManager;
    private ProgressDialog mDialog;


    private static final String USGS_REQUEST_URL = "http://192.168.206.1/api/login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mLoginEditText = findViewById(R.id.login);
        mPasswordEditText = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.goToRegisterButton);
        mSkipTextView = findViewById(R.id.skipTextView);
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);

        mSessionManager = new SessionManager(this);

//        if(mSessionManager.isLoggedIn())
//        {
//            Intent intent = new Intent(this, TaskActivity.class);
//            startActivity(intent);
//            finish();
//        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(mLoginEditText.getText().toString(),mPasswordEditText.getText().toString());
            }
        });
        final Context context = this;
        mSkipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        Log.e(TAG, "checkLogin: 1" );
        try
        {
            Log.e(TAG, "checkLogin: 2" );
            try
            {
                Log.e(TAG, makeHttpRequest(createUrl(USGS_REQUEST_URL)));
            } catch(NetworkOnMainThreadException e)
            {
                Log.e(TAG, "checkLogin: 3" + e.getMessage() + " " );
            }

            Log.e(TAG, "checkLogin: 4" );
        }
        catch (IOException e)
        {
            Log.e(TAG, "EXCEPTION" );
        }

        //showDialog();

    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(TAG, "error" );
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
            Log.e(TAG, "makeHttpRequest: " + jsonResponse.indexOf(0) );
        } catch (IOException e) {

        }  finally {
           if(urlConnection != null)
               urlConnection.disconnect();
           if(inputStream != null)
               inputStream.close();
        }

        return jsonResponse;

    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private void showDialog() {
        if (!mDialog.isShowing())
            mDialog.show();
    }

    private void hideDialog() {
        if (mDialog.isShowing())
            mDialog.dismiss();
    }
}
