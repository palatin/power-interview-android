package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Игорь on 01.04.2017.
 */

public class Question extends InterviewObject implements Parcelable {


    private static final long serialVersionUID = -9130752311079548914L;
    private List<WidgetEntity> widgetEntities;

    @Override
    public String getTitle() {
        return "Question, id -  " + getId();
    }

    public Question() {
    }


    public List<WidgetEntity> getWidgetEntities() {
        return widgetEntities;
    }

    public void setWidgetEntities(List<WidgetEntity> widgetEntities) {
        this.widgetEntities = widgetEntities;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.widgetEntities);
        dest.writeInt(getId());
    }

    protected Question(Parcel in) {
        this.widgetEntities = in.createTypedArrayList(WidgetEntity.CREATOR);
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
