package com.netmind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientPostJson {

	public static void main(String[] args) {
		sendJSONData("http://localhost:8080/RESTfulExample/rest/json/metallica/post", "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}");

	}

	public static boolean sendJSONData(String dir, String json) {
		boolean result = true;
		try {

			URL url = new URL(dir);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = json;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			result = false;
			e.printStackTrace();

		} catch (IOException e) {
			result = false;
			e.printStackTrace();

		}

		return result;
	}
}