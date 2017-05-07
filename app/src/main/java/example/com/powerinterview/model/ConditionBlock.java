package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 16.04.2017.
 */

public class ConditionBlock extends InterviewObject implements Parcelable {


    private static final long serialVersionUID = -2221309268233065596L;

    @Override
    public String getTitle() {
        return "Condition block, id -  " + getId();
    }


    private List<Condition> conditions;


    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.conditions);
        dest.writeInt(getId());
    }

    public ConditionBlock() {
    }

    protected ConditionBlock(Parcel in) {
        this.conditions = in.createTypedArrayList(Condition.CREATOR);
        setId(in.readInt());
    }

    public static final Creator<ConditionBlock> CREATOR = new Creator<ConditionBlock>() {
        @Override
        public ConditionBlock createFromParcel(Parcel source) {
            return new ConditionBlock(source);
        }

        @Override
        public ConditionBlock[] newArray(int size) {
            return new ConditionBlock[size];
        }
    };
}
