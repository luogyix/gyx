package com.gyx.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletRequestWrapper;

import jodd.JoddDefault;
import jodd.io.StreamUtil;
  
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private final byte[] body;
	
	public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		body = StreamUtil.readBytes(request.getReader(), JoddDefault.encoding);
	}  
	
	public BufferedReader getReader(){
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	public ServletInputStream getInputStream(){
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			
			public int read() throws IOException {
				return bais.read();
			}
		};
	}
  
}