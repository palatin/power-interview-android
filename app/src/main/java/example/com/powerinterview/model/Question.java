package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Игорь on 01.04.2017.
 */

public class Question extends InterviewObject implements Parcelable {


    @Override
    public String getTitle() {
        return "Question, id -  " + getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
    }

    public Question() {
    }

    protected Question(Parcel in) {
        setId(in.readInt());
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
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
