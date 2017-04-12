package example.com.powerinterview.model;

import android.os.Parcelable;

/**
 * Created by Игорь on 01.04.2017.
 */

public abstract class InterviewObject implements Parcelable {

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
