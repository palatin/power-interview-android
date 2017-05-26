package example.com.powerinterview.core;

import android.util.Base64;

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
        stringBuilder.append(message + "<br/>");
    }

    public static byte[] getResults(String encryptKey) throws Exception {
        return Encrypt.encryptAES(stringBuilder.toString(), encryptKey).getBytes();
    }

    public static void clearLog() {
        stringBuilder.setLength(0);
    }

}
