package example.com.powerinterview.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import example.com.powerinterview.utils.Encrypt;

/**
 * Created by Игорь on 11.05.2017.
 */

public class InterviewLogger {

    private static StringBuilder stringBuilder = new StringBuilder();


    public static void writeToInterviewLog(String message) {
        stringBuilder.append(message + " \n");
    }

    public static byte[] getResults(String encryptKey) throws Exception {
        return Encrypt.decryptByAES(stringBuilder.toString(), encryptKey).getBytes();
    }

}
