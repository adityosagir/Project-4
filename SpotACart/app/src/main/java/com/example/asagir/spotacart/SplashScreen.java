//package com.example.asagir.spotacart;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.firebase.client.utilities.Base64;
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by adi on 3/24/16.
// */
//public class SplashScreen extends AppCompatActivity {
//
//    private GMapsResult result;
//
//    private static int TIMEOUT = 2500;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//
//
//
//    }
//
//    // AsyncTask
//    public class GetGmapsData extends AsyncTask<Void, Void, Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Url url = new URL("");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                Base64.InputStream inStream = connection.getInputStream();
//                data = getInputData(inStream);
//            }
//            return null;
//        }
//    }
//}
