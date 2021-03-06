package example.com.powerinterview.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Source;

import example.com.powerinterview.ui.CustomToast;

/**
 * Created by Игорь on 18.04.2017.
 */

public class Interview implements Serializable, Parcelable{


    private static final long serialVersionUID = -286977446330471723L;
    private String name;
    private String description;


    private List<InterviewObject> interviewObjects;

    private HashMap<String, Variable> variables;

    public List<InterviewObject> getInterviewObjects() {
        return interviewObjects;
    }

    public void setInterviewObjects(List<InterviewObject> interviewObjects) {
        this.interviewObjects = interviewObjects;
    }


    public Interview() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(this.interviewObjects);
    }

    protected Interview(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        in.readList(interviewObjects, InterviewObject.class.getClassLoader());
    }

    public static final Creator<Interview> CREATOR = new Creator<Interview>() {
        @Override
        public Interview createFromParcel(Parcel source) {
            return new Interview(source);
        }

        @Override
        public Interview[] newArray(int size) {
            return new Interview[size];
        }
    };

    public InputStream getInputStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);


        oos.writeObject(this);

        oos.flush();
        oos.close();

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
        checkConditionsOnVariablesExists();
    }

    //check if some condition use variable that is no longer exist
    private void checkConditionsOnVariablesExists() {

        for (InterviewObject object: interviewObjects) {

            if(object instanceof ConditionBlock) {
                List<Condition> conditions = ((ConditionBlock) object).getConditions();
                for (Condition condition: conditions) {
                    if(!variables.containsKey(condition.getLeftSide())) {
                        conditions.remove(condition);
                    }
                }
            }

        }


    }
}
