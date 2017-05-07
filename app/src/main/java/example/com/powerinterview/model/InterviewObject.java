package example.com.powerinterview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Игорь on 01.04.2017.
 */

public abstract class InterviewObject implements Parcelable, Serializable {

    private static final long serialVersionUID = -8961472213104074291L;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getTitle();


    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        try {
            if(((InterviewObject)obj).getId() == getId())
                equals = true;
        }
        finally {
            return equals;
        }
    }



}
