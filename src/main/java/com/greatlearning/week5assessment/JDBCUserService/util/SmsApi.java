package com.greatlearning.week5assessment.JDBCUserService.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class SmsApi {

	public static void sendSms(String message, String number) {
		String apiKey = "Yf32nq8Eaotzkjm6gI0HWFiU9LDbcplM1ReudBXGsJv7AwxQCZPpnhOZGHR1eJyrM2XjQxNvEYt9aKFf";
		try {
			message = URLEncoder.encode(message, "UTF-8");
			System.out.println("Encoded message is = " + message);
			String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey + "&variables_values="
					+ message + "&route=otp&numbers=" + number;

			// Sending GET request using Java

			URL url = new URL(myUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("cache-control", "no-cache");

			int code = con.getResponseCode();
			System.out.println("Response code = " + code);
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				response.append(line);
			}
			// You can see the response from the below code, only for developers
			// System.out.println(response);

			System.out.println("Message sent successfully to number: " + number);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
