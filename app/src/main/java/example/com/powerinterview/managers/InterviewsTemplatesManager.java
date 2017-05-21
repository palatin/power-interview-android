package example.com.powerinterview.managers;

import android.content.Context;

import com.orm.SugarRecord;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.InterviewTemplate;

/**
 * Created by Игорь on 03.05.2017.
 */

public class InterviewsTemplatesManager {



    public void storeInterviewTemplate(InterviewTemplate template, Context context, byte[] bytes) throws IOException {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/templates");
        if(!file.exists())
            file.mkdir();
        if(file.exists()) {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(file.getAbsolutePath() + "/" + template.getName() + "_" + template.getId() + ".pif")));
            bos.write(bytes);
            bos.flush();
            bos.close();
            SugarRecord.save(template);
        }
    }

    public InterviewTemplate getInterviewTemplateById(long id) {
        InterviewTemplate template = SugarRecord.findById(InterviewTemplate.class, id);
        if(template == null)
            throw new NullPointerException("Template not found");
        return template;
    }

    public File getFileTemplateById(long id, Context context) throws FileNotFoundException {
        InterviewTemplate template = getInterviewTemplateById(id);
        File file = new File(context.getFilesDir().getAbsolutePath() + "/templates/" + template.getName() + "_" + template.getId() + ".pif");
        if(!file.exists()) {
            removeTemplate(id);
            throw new FileNotFoundException("Template file not found");
        }
        return file;
    }

    private void removeTemplate(long id) {
        SugarRecord.delete(getInterviewTemplateById(id));
    }

    public Interview loadInterviewByFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Interview interview = (Interview) ois.readObject();
        if(interview  == null)
            throw new NullPointerException("Can't read template");

        return interview;

    }



}
