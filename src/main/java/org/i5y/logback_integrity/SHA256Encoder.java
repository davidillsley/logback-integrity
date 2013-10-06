package org.i5y.logback_integrity;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

public class SHA256Encoder extends PatternLayoutEncoder {

	static final Charset UTF8 = Charset.forName("UTF-8");

	@Override
	public void init(OutputStream os) throws IOException {
		super.init(new SHA256OutputStream(os));
	}

	public Charset getCharset() {
		return UTF8;
	}

	public void setCharset(Charset charset) {
		if (!UTF8.equals(charset)) {
			throw new IllegalArgumentException();
		}
	}
}
