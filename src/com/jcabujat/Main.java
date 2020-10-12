package com.jcabujat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

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

            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel channel = ra.getChannel();
            // checking that outputBytes is tied with byteBuffer
            outputBytes[0] = 'a';
            outputBytes[1] = 'b';
            byteBuffer.flip(); // this will reset the buffer's pointer and read the file from the start of the line
            numBytes = channel.read(byteBuffer);
            System.out.println("Number of bytes read = " + numBytes);
            System.out.println(new String(outputBytes));

            // Another way to extract byte array from the file
            if (byteBuffer.hasArray()) {
                System.out.println("byteBuffer = " + new String(byteBuffer.array()));
            }

            // Absolute read - passing index whenever we try to get the value from buffer so we don't need to flip the pointer
            intBuffer.flip();
            numBytes = channel.read(intBuffer);
            System.out.println(intBuffer.getInt(0)); // passing the index where you want to start the read in the buffer

            intBuffer.flip();
            numBytes = channel.read(intBuffer);
            System.out.println(intBuffer.getInt(0)); // passing the index where you want to start the read in the buffer


//            // Relative read - you need to call flip between reading the buffer and getting the value from it
//            // read the next int then print to console
//            intBuffer.flip();
//            numBytes = channel.read(intBuffer);
//            intBuffer.flip();
//            System.out.println(intBuffer.getInt());
//
//            // read the next int then print to console
//            intBuffer.flip();
//            numBytes = channel.read(intBuffer);
//            intBuffer.flip();
//            System.out.println(intBuffer.getInt());

            channel.close();
            ra.close();


//            RandomAccessFile raf = new RandomAccessFile("data.dat", "rwd");
//            byte[] bytes = new byte[outputBytes.length];
//            raf.read(bytes);
//            System.out.println(new String(bytes));
//
//            int int1 = raf.readInt();
//            int int2 = raf.readInt();
//            System.out.println(int1);
//            System.out.println(int2);

        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
}
