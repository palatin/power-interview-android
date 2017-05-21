package example.com.powerinterview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import example.com.powerinterview.exceptions.ConvertException;

/**
 * Created by Игорь on 30.01.2017.
 */

public class Converter {

    public static JSONObject bytesToJSON(byte[] input) throws ConvertException {
        try {
            String inputString = new String(input, "UTF-8");
            Log.v("Message to JSON", inputString);
            return new JSONObject(inputString);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ConvertException(ex.getMessage());
        }
    }

    public static JSONArray bytesToJSONArray(byte[] input) throws ConvertException {
        try {
            String inputString = new String(input, "UTF-8");
            Log.v("Message to JSON", inputString);
            return new JSONArray(inputString);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ConvertException(ex.getMessage());
        }
    }

    public static Bitmap bitmapFromPath(String path) throws Exception
    {
        return BitmapFactory.decodeFile(path);
    }
}
