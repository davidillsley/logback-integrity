package org.i5y.logback_integrity;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class SHA256OutputStreamTest {

	@Test
	public void testWriting() throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		SHA256OutputStream sos = new SHA256OutputStream(baos);
		
		sos.write("Test\n".getBytes());
		
		sos.flush();
		
		String result = new String(baos.toByteArray());
		
		assertEquals("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\n", result);
	}
	@Test
	public void testWritingMultiples() throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		SHA256OutputStream sos = new SHA256OutputStream(baos);
		
		sos.write("Test\nTest2\n".getBytes());
		
		sos.flush();
		
		String result = new String(baos.toByteArray());
		
		assertEquals("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest2\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\n", result);
	}
	
	@Test
	public void testWritingMultiples2() throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		SHA256OutputStream sos = new SHA256OutputStream(baos);
		
		sos.write("Test\nTest2\nTest3\n".getBytes());
		
		sos.flush();
		
		String result = new String(baos.toByteArray());
		
		assertEquals("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest2\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\nTest3\n#0Fzb2ZTdOnXVlgU7ZnBkdoNpKRkSKYGwqai5nM78V1M=\n", result);
	}
}
