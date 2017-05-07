package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Spinner;

import java.io.Serializable;

/**
 * Created by Игорь on 16.04.2017.
 */

public class Condition implements Parcelable, Serializable {


    private static final long serialVersionUID = 356059348954176726L;
    private String condition;
    private String goTo;



    public String getCondition() {
        return condition;
    }

    public void setCondition(String condtition) {
        this.condition = condtition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.condition);
        dest.writeString(this.goTo);
    }

    public Condition() {
    }

    protected Condition(Parcel in) {
        this.condition = in.readString();
        this.goTo = in.readString();
    }

    public static final Creator<Condition> CREATOR = new Creator<Condition>() {
        @Override
        public Condition createFromParcel(Parcel source) {
            return new Condition(source);
        }

        @Override
        public Condition[] newArray(int size) {
            return new Condition[size];
        }
    };

    public String getGoTo() {
        return goTo;
    }

    public void setGoTo(String goTo) {
        this.goTo = goTo;
    }
}
