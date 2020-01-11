package org.cajunc2.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferedCopy {

	private static final int DEFAULT_BUFFER_SIZE = 4096;
	private final byte[]     buffer;
	private final int        bufferSize;

	public BufferedCopy() {
		this.buffer = new byte[DEFAULT_BUFFER_SIZE];
		this.bufferSize = DEFAULT_BUFFER_SIZE;
	}

	public BufferedCopy(int bufferSize) {
		this.buffer = new byte[bufferSize];
		this.bufferSize = bufferSize;
	}

	public void copy(InputStream is, OutputStream os) throws IOException {
		while(true) {
			int bytesRead = is.read(this.buffer, 0, this.bufferSize);
			if(bytesRead < 0) {
				break;
			}
			os.write(this.buffer, 0, bytesRead);
		}
	}

}
