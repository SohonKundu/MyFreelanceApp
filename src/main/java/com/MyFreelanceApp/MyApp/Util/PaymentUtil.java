package com.MyFreelanceApp.MyApp.Util;

import java.io.IOException;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.MyFreelanceApp.MyApp.ApiResponse.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PaymentUtil {
	
	private String CreateOrder(long BasketId,double Amount) throws ClientProtocolException, IOException, JSONException
	{
		HttpClient httpClient = HttpClients.createDefault();		
		String encoding = Base64.getEncoder().encodeToString(
				("rzp_test_pgmDCnUN2PTsMK:959esnIS3mg3UpeQfAlMaDG1").getBytes());
		HttpPost httpPost = new HttpPost("https://api.razorpay.com/v1/orders");
		httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
		httpPost.setHeader("Content-Type", "application/json");
		JSONObject jobj = new JSONObject();
		jobj.put("amount", Amount);
		jobj.put("currency", "INR");
		jobj.put("receipt", String.valueOf(BasketId));
		StringEntity params = new StringEntity(jobj.toString());
		httpPost.setEntity(params);
		HttpResponse response = httpClient.execute(httpPost);		
		HttpEntity responseEntity = response.getEntity();
		return EntityUtils.toString(responseEntity);
	}
	
	public Order GetOrderResponse(long BasketId,double Amount) throws ClientProtocolException, JSONException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.readValue(CreateOrder(BasketId,Amount), Order.class);
		return order;
	}
	

}
