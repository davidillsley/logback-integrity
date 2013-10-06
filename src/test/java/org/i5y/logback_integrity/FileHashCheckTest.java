package org.i5y.logback_integrity;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class FileHashCheckTest {

	@Test
	public void testValidFile() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest2\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\n".getBytes());
		
		assertTrue(FileHashCheck.check(is));
	}

	@Test
	public void testInvalidText() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest3\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\n".getBytes());
		
		assertFalse(FileHashCheck.check(is));
	}
	
	@Test
	public void testInvalidHash() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd0mk4p4k=\nTest2\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\n".getBytes());
		
		assertFalse(FileHashCheck.check(is));
	}
	
	
	@Test
	public void testLongerValidFile() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest2\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\nTest3\n#0Fzb2ZTdOnXVlgU7ZnBkdoNpKRkSKYGwqai5nM78V1M=\n".getBytes());
		
		assertTrue(FileHashCheck.check(is));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMissingText() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\nTest3\n#0Fzb2ZTdOnXVlgU7ZnBkdoNpKRkSKYGwqai5nM78V1M=\n".getBytes());
		
		FileHashCheck.check(is);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMissingHash() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest2\nTest3\n#0Fzb2ZTdOnXVlgU7ZnBkdoNpKRkSKYGwqai5nM78V1M=\n".getBytes());
		
		FileHashCheck.check(is);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMissingTrailingHash() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest2\n#sASeDSER4/YTAQYmBihbqO1tJyyTr4qM5fjEZYT8keY=\nTest3\n".getBytes());
		
		FileHashCheck.check(is);
	}
	
	@Test
	public void testMissingEntry() throws Exception{
		InputStream is = new ByteArrayInputStream("Test\n#ydBMlWX8ZlyAaB+x2CmTgCaHH2bhT1AeCFMd9mk4p4k=\nTest3\n#0Fzb2ZTdOnXVlgU7ZnBkdoNpKRkSKYGwqai5nM78V1M=\n".getBytes());
		
		assertFalse(FileHashCheck.check(is));
	}
}
