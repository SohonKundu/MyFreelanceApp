package com.MyFreelanceApp.MyApp.Util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {	
	
	
	public int createOtp()
	{	
		int len=4;
		String numbers = "0123456789"; 
        Random rndm_method = new Random();  
       StringBuffer otp= new StringBuffer(4); 
  
        for (int i = 0; i < len; i++)
        	otp= otp.append(numbers.charAt(rndm_method.nextInt(numbers.length())));
         
		return Integer.parseInt(otp.toString()); 
	}
	
	
	public void SendOtp(long number,int otp) throws URISyntaxException, ClientProtocolException, IOException
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletableFuture.runAsync(()->
		{
			try {
				String url = "https://2factor.in/API/V1/ad6717ad-d1b5-11ea-9fa5-0200cd936042/SMS/"+
						String.valueOf(number)+"/"+String.valueOf(otp);			
		URIBuilder builder = new URIBuilder(url);
		URI uri = builder.build();
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();		
		httpclient.execute(httpGet);		
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		},executor);
		
	}

}
