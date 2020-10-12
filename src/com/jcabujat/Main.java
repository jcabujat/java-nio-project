package com.jcabujat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

    public static void main(String[] args) {
	    try (FileOutputStream binFile = new FileOutputStream("data.dat");
             FileChannel binChannel = binFile.getChannel()){
	        byte[] outputBytes = "Hello World!".getBytes();
            ByteBuffer byteBuffer = ByteBuffer.wrap(outputBytes); // this method repoints the pointer to the start of the buffer
            int numBytes = binChannel.write(byteBuffer);
            System.out.println("Number of bytes written = " + numBytes);

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
            intBuffer.putInt(245);
            intBuffer.flip(); // need to reset the pointer at the start before writing.
            // Note that you need to reset pointer everytime you switch from reading and writing in the buffer.
            // Writing to the buffer switches to reading the buffer then writing to channel.
            numBytes = binChannel.write(intBuffer); // this points the pointer to the end of the buffer
            System.out.println("Number of bytes written = " + numBytes);

            intBuffer.flip(); // need to reset the pointer again before reusing the buffer and putting an int again
            intBuffer.putInt(-98765);
            intBuffer.flip(); // always reset before writing except when calling a method that automatically resets the pointer
            numBytes = binChannel.write(intBuffer);
            System.out.println("Number of bytes written = " + numBytes);

        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
}
