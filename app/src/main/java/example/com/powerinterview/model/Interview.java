package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Игорь on 18.04.2017.
 */

public class Interview implements Serializable, Parcelable{

    private List<InterviewObject> interviewObjects;

    public List<InterviewObject> getInterviewObjects() {
        return interviewObjects;
    }

    public void setInterviewObjects(List<InterviewObject> interviewObjects) {
        this.interviewObjects = interviewObjects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.interviewObjects);
    }

    public Interview() {
    }

    protected Interview(Parcel in) {
        this.interviewObjects = in.createTypedArrayList(InterviewObject.CREATOR);
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
}
