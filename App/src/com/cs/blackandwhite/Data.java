package com.cs.blackandwhite;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Data {
	public static String userId;
	public static String auth_token = null;
    public static String serverUrl = "http://24.182.146.42:3000/api/v1/";

	public static Map<String, Object> postRequest(String path, Map<String,Object> params) throws JSONException, ClientProtocolException, IOException
	{

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(path);
		JSONObject holder = new JSONObject();
		Object[] keys = params.keySet().toArray();
		for(Object key:keys){
			holder.put((String)key, params.get(key));
		}
		StringEntity se = new StringEntity(holder.toString());
		httpost.setEntity(se);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		if(auth_token != null){
			httpost.setHeader("Authorization", getAuthHeader());
		}
		HttpResponse response = httpclient.execute(httpost);
		return parseJSON(convertStreamToString(response.getEntity().getContent()));		
	}

	public static Map<String, Object> putRequest(String path, Map<String,Object> params)
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPut httput = new HttpPut(path);
		JSONObject holder = new JSONObject();
		Object[] keys = params.keySet().toArray();
		for(Object key:keys){
			holder.put((String)key, params.get((String)key));
		}
		StringEntity se = new StringEntity(holder.toString());
		httput.setEntity(se);
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		if(auth_token != null){
			httput.setHeader("Authorization", getAuthHeader());
		}
		HttpResponse response = httpclient.execute(httput);
		return parseJSON(convertStreamToString(response.getEntity().getContent()));		
	}

	public static Map<String, Object> deleteRequest(String path, Map<String,Object> params)
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpDelete httput = new HttpDelete(path);
		JSONObject holder = new JSONObject();
		Object[] keys = params.keySet().toArray();
		for(Object key:keys){
			holder.put((String)key, params.get((String)key));
		}
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		if(auth_token != null){
			httput.setHeader("Authorization", getAuthHeader());
		}
		HttpResponse response = httpclient.execute(httput);
		return parseJSON(convertStreamToString(response.getEntity().getContent()));		
	}

	public static Map<String, Object> getRequest(String path) throws ClientProtocolException, IOException{

		DefaultHttpClient httpclient = new DefaultHttpClient();		
		HttpGet request = new HttpGet(path);
		request.setHeader("Authorization", getAuthHeader());
		Log.d("String", "Request: " + request.getAllHeaders()[0].toString());
		HttpResponse response = null;		
		response = httpclient.execute(request);
		httpclient.getConnectionManager().shutdown(); 
		return parseJSON(convertStreamToString(response.getEntity().getContent()));
	}

	public static Map<String, Object> parseJSON(String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		Log.d("Message", "Message: "+ result);
		try {
			JSONObject jsonObject = new JSONObject(result);
			Iterator<?> keys = jsonObject.keys();
			while(keys.hasNext()){
				String key = (String) keys.next();
				map.put(key, jsonObject.get(key));
			}
		} catch (JSONException e) {
			try {
				JSONArray jArray = new JSONArray(result);
				map.put("data", jArray);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return map;
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static boolean isEmptyOrNull(String string) {
		if(string == null){
			return true;
		}
		if(string.length() == 0){
			return true;
		}
		return false;
	}

	private static String getAuthHeader() {
		return "Token token=\"" + auth_token + "\"";
	}


}
