/**
 * 
 */
package jp.kobe_u.cs24.service.invocation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author tokunaga
 *
 */
public class HttpClient {
	private static final String httpMethodString = "GET";

	public static void execGet() {
		URL url = null;
		try {
			url = new URL(
					"http://133.30.159.3:8080/axis2/services/MisakiTweetService/tweet?paramString=tokunagaさんいらっしゃい");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(httpMethodString);
			try {
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStreamReader inputReader = new InputStreamReader(
							connection.getInputStream(), StandardCharsets.UTF_8);
					BufferedReader bufReader = new BufferedReader(inputReader);
					String line;
					while((line = bufReader.readLine()) != null){
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection != null){
				connection.disconnect();
			}
			
		}
		

	}

}
