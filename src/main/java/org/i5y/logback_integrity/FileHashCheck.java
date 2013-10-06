package org.i5y.logback_integrity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class FileHashCheck {

	private static final byte NEWLINE = (byte)'\n';
	
	public static boolean check(InputStream is) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		byte[] nMinus1 = null;
		byte[] nMinus2 = null;
		String line = br.readLine();
		boolean isContentLine = true;
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		while (line != null) {
			if (line.startsWith("#")) {

				if (isContentLine) {
					throw new IllegalStateException(
							"Expected content line but got a hash line");
				}

				md.reset();

				if (nMinus2 != null) {
					md.update(nMinus2);
					md.update(NEWLINE);
				}

				md.update(nMinus1);
				md.update(NEWLINE);

				String hashed = new sun.misc.BASE64Encoder().encode(md.digest());

				if (!hashed.equals(line.substring(1))) {
					return false;
				}
			} else {
				if (!isContentLine) {
					throw new IllegalStateException(
							"Expected hash line but got a content line");
				}
			}
			nMinus2 = nMinus1;
			nMinus1 = line.getBytes();
			line = br.readLine();
			isContentLine = !isContentLine;
		}
		if (!isContentLine) {
			throw new IllegalStateException();
		}

		return true;
	}

}
