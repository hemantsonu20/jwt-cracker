package com.github.spring.jwt;

import java.io.IOException;
import java.io.OutputStream;

public class MyClosable implements AutoCloseable {

	
	private final OutputStream stream;
	
	public MyClosable(OutputStream stream) {
		this.stream = stream;
	}
	
	public void write(String s) throws IOException {
		stream.write(s.getBytes());
	}
	
	@Override
	public void close() throws Exception {
		stream.close();
	}
}
