package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class BufferToText {
	private static final int BSIZE = 1024;

	public static void main(String[] args) throws Exception {
		FileChannel fc = new FileOutputStream("data2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text".getBytes()));
		fc.close();

		fc = new FileInputStream("data2.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		buff.flip();

		// Dosen't work
		System.out.println(buff.asCharBuffer());

		// Decode using this system's default charest
		// д��ʱ�����룬����ʱ����
		buff.rewind();
		String encoding = System.getProperty("file.encoding");
		System.out.println("Decode using " + encoding + ": "
				+ Charset.forName(encoding).decode(buff));

		// Or,we could encode with something that will print
		// д��ʱ���룬����ʱ�Ͳ���Ҫ����
		fc = new FileOutputStream("data2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text".getBytes("UTF-16BE")));
		fc.close();

		// Now try reading again:
		fc = new FileInputStream("data2.txt").getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();

		System.out.println(buff.asCharBuffer());
		// Use a CharBuffer to write through
		fc = new FileOutputStream("data2.txt").getChannel();
		buff = ByteBuffer.allocate(24);// More than needed
		buff.asCharBuffer().put("Some text");
		fc.close();

		// Read and display
		fc = new FileInputStream("data2.txt").getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();

		System.out.println(buff.asCharBuffer());

	}

}