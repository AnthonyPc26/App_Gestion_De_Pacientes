package com.sise.app_gestion_de_pacientes.shared;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.StrictMode;

public class HttpUtil {
    private HttpUtil() {

    };
    public static String GET(String baseUrl, String path) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(baseUrl+path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public static String POST(String baseUrl, String path, String body) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(baseUrl+path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);

            try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
                out.writeBytes(body);
                out.flush();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
