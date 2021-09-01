package lebah.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONObject;

public class Rest {

	public static String post(String method, String restUrl, Map<String, Object> params) throws Exception {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        
        URL url = new URL (restUrl);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));		
		con.setDoOutput(true);
		con.getOutputStream().write(postDataBytes);
		int code = con.getResponseCode();
		if ( code == 200 ) {
			StringBuilder b = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String str = "";
			while ((str = br.readLine()) != null) b.append(str);
			con.disconnect();
			return b.toString().trim();
		} else {
			con.disconnect();
			return "Error: response code = " + code;
		}
	}
	
	
	public static String get(String requestUrl) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(requestUrl).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		con.setRequestProperty("Content-Type", "application/json" );
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.flush();
		int code = con.getResponseCode();
		if ( code == 200 ) {
			StringBuilder b = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String str = "";
			while ((str = br.readLine()) != null) b.append(str);
			con.disconnect();
			return b.toString();
		}
		else {
			con.disconnect();
			return "Error: response code = " + code;
		}
	}
	
	
	public static String doRequest(String method, String requestUrl, String jsonIn) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(requestUrl).openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		con.setRequestProperty("Content-Type", "application/json" );
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		byte[] input = jsonIn.getBytes("utf-8");
		os.write(input);
		os.flush();
		int code = con.getResponseCode();
		if ( code == 200 ) {
			StringBuilder b = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String str = "";
			while ((str = br.readLine()) != null) b.append(str);
			con.disconnect();
			return b.toString().trim();
		}
		else {
			con.disconnect();
			return "Error: response code = " + code;
		}
	}
	
	public static String post(String requestUrl, String jsonIn) throws Exception {
		return doRequest("POST", requestUrl, jsonIn);
	}
	
	public static String get(String requestUrl, String jsonIn) throws Exception {
		return doRequest("GET", requestUrl, jsonIn);
	}
	
	
	public static void main(String[] args) throws Exception {
		String icno = "720922015344";
		String url = "http://mylebah.com/mmdisusers/api/GetUserInfo?icno=" + icno;
		String result = Rest.get(url);
		System.out.println(result);
				
	}

}
