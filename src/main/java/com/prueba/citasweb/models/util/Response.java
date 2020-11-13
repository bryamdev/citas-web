package com.prueba.citasweb.models.util;

import java.util.List;

public class Response {
	
	private boolean ok;
	private String message;
	private String error;
	private Object result;
	private List<?> results;
	
	public Response(boolean ok, String message, Object result) {
		this.ok = ok;
		this.message = message;
		this.result = result;
	}
	
	public Response() {
		
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean isOk) {
		this.ok = isOk;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}
	
		
	

}
