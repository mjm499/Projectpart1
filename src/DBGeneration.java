import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DBGeneration {

	public static void main(String[] args) {
		//2d9ec69f2b5067cd is my key
		String key = "2d9ec69f2b5067cd";
	
		
		String sURL = "http://api.wunderground.com/api/" + key + "/forecast10day/q/19015.json";
		try {
			
		URL url = new URL(sURL);

		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		
		request.connect();
		
		JsonParser jp = new JsonParser();
				
		JsonElement root = jp.parse(new InputStreamReader((InputStream)request.getContent()));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Mark\\Desktop\\dbgen.txt")));
		
		JsonObject rootobj = root.getAsJsonObject();
		
		JsonArray forecastList = rootobj.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().get("forecastday").getAsJsonArray();
		String forecast = rootobj.get("forecast").getAsJsonObject().get("simpleforecast").getAsJsonObject().toString();//.get("forecastday").getAsString();
		int high = 0, low = 0;
		for(int i = 0; i < 10; i++)
		{
		//writer.write(forecastList.get(i).getAsJsonObject().get("high").getAsJsonObject().get("fahrenheit").toString());
		//writer.write(forecastList.get(i).getAsJsonObject().get("low").getAsJsonObject().get("fahrenheit").toString());
		//writer.write(System.getProperty("line.separator"));
		high += Integer.parseInt(forecastList.get(i).getAsJsonObject().get("high").getAsJsonObject().get("fahrenheit").getAsString());
		low += Integer.parseInt(forecastList.get(i).getAsJsonObject().get("low").getAsJsonObject().get("fahrenheit").getAsString());
		writer.write(forecastList.get(i).getAsJsonObject().get("conditions").toString());
		writer.write(System.getProperty("line.separator"));
		}
		writer.write(high/10.0 + " ");
		writer.write(low/10.0 + "");
		writer.close();
		System.out.println(forecast);
		}
		catch(IOException ex) {
			System.err.println("IOException: " + ex.getMessage());
		}
	}

}
