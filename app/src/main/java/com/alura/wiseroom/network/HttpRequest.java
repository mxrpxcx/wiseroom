package com.alura.wiseroom.network;

import android.content.Context;

import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.Event;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;



public class HttpRequest {

	Context context;
	Map<String, String> headers;
	String url;
	int reqMethod;
	String eventName;

	public HttpRequest(Context c, Map<String, String> h, String u, String reqMethod, String eventName) {
		this.context = c;
		this.headers = h;
		this.url = u;
		this.eventName = eventName;
		switch (reqMethod) {
			case "GET":
				this.reqMethod = Request.Method.GET;
				break;
			case "POST":
				this.reqMethod = Request.Method.POST;
				break;
			case "PUT":
				this.reqMethod = Request.Method.PUT;
				break;
			case "DELETE":
				this.reqMethod = Request.Method.DELETE;
				break;
		}
	}

	public void doRequest() {
		try {
			// Formulate the request and handle the response.
			StringRequest stringRequest = new StringRequest(reqMethod, url,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							EventBus.getDefault().post(new Event(eventName + Constants.eventSuccessLabel, response));
						}
					},
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							try {
								if (error.networkResponse != null) {
									String errorResult = new String(error.networkResponse.data, "UTF-8");
									EventBus.getDefault().post(new Event(eventName + Constants.eventErrorLabel, errorResult));
								} else {
									EventBus.getDefault().post(new Event(eventName + Constants.eventErrorLabel, "Sem resposta do servidor"+error.toString()));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					return headers;
				}
			};

			stringRequest.setRetryPolicy(new DefaultRetryPolicy(
					10000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}