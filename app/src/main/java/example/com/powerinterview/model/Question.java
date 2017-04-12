package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 01.04.2017.
 */

public class Question extends InterviewObject implements Parcelable {


    private List<Widget> widgets;

    @Override
    public String getTitle() {
        return "Question, id -  " + getId();
    }

    public Question() {
    }


    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.widgets);
        dest.writeInt(getId());
    }

    protected Question(Parcel in) {
        this.widgets = in.createTypedArrayList(Widget.CREATOR);
        setId(in.readInt());
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
