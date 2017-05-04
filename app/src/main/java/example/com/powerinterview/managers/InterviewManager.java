package example.com.powerinterview.managers;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import example.com.powerinterview.model.InterviewModule;

/**
 * Created by Игорь on 03.05.2017.
 */

public class InterviewManager {



    public void storeInterviewModule(InterviewModule module, Context context, byte[] bytes) throws IOException {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/modules");
        file.mkdir();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(file.getAbsolutePath() + "/" + module.getName() + module.getId() +".pif")));
        bos.write(bytes);
        bos.flush();
        bos.close();
    }

}
