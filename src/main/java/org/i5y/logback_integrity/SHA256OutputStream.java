package org.i5y.logback_integrity;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

class SHA256OutputStream extends OutputStream {

	private static final byte HASH_CHAR = '#';
	private static final byte NEWLINE = '\n';

	private final MessageDigest md;
	private final OutputStream underlying;

	public SHA256OutputStream(OutputStream underlying) {
		this.underlying = underlying;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(int b) throws IOException {
		underlying.write(b);
		md.update((byte) b);
		if (b == '\n') {
			byte[] hashBytes = DatatypeConverter.printBase64Binary(md.digest())
					.getBytes(SHA256Encoder.UTF8);

			md.reset();
			underlying.write(HASH_CHAR);
			md.update(HASH_CHAR);
			underlying.write(hashBytes);
			md.update(hashBytes);
			underlying.write(NEWLINE);
			md.update(NEWLINE);
		}
	}
}