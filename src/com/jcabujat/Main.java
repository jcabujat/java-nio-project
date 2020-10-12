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
            ByteBuffer byteBuffer = ByteBuffer.wrap(outputBytes);
            int numBytes = binChannel.write(byteBuffer);
            System.out.println("Number of bytes written = " + numBytes);

        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
}
