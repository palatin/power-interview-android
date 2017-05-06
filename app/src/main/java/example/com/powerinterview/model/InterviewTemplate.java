package example.com.powerinterview.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

/**
 * Created by Игорь on 29.04.2017.
 */


@Table
public class InterviewTemplate {


    private transient Long id;
    private String name;
    private String author;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
