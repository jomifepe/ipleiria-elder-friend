package com.example.tsngapp.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskLoginPost extends AsyncTask<String, Integer, String> {

    private JSONObject dataToPost;
    private final String LOG_TAG="AsyncTaskLoginPost";

    public AsyncTaskLoginPost(JSONObject dataToPost){
        this.dataToPost = dataToPost;
    }

    @Override
    protected String doInBackground(String... urlStrings) {

        String result = null;
        HttpURLConnection conn = null;
        InputStream stream = null;
        BufferedReader reader = null;

        try{

            //Configura a conexão
            URL url = new URL(urlStrings[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //Enviar corpo do post em JSON
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(dataToPost.toString());
            writer.flush();

            //Inicia a conexão
            conn.connect();

            //Ve a resposta
            int responseCode = conn.getResponseCode();

            if(responseCode >199 && responseCode<300){

                //vai buscar dados da resposata do server
                stream = conn.getInputStream();

                //Traduz para string
                if(stream != null){
                    reader = new BufferedReader(new InputStreamReader(stream));

                    this.convertToString(result, reader);
                }
            }
            else{
                Log.d(LOG_TAG,"Error not permited");
            }

        }
        catch(Exception ex){
            Log.d(LOG_TAG,"The doInBckground for login failed with "+ex.getMessage());
        }
        finally {



            try {
                if(reader!=null){
                    reader.close();
                }

                if(stream!=null){
                    stream.close();
                }

                if(conn!=null){
                    conn.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void convertToString(String result, BufferedReader reader) {
        String tempString = "";

        try{
            while (true){
                tempString = reader.readLine();
                if(tempString == null){
                    break;
                }
                result +=tempString;
            }
        }
        catch (Exception ex){
            Log.d(LOG_TAG,"Error in convertToString " + ex.getMessage());
        }

    }


}
