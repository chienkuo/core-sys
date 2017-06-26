package me.akuo.domain;

import java.io.Serializable;

/**
 * Created by pc on 2017/4/10.
 */
public class User implements Serializable {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(30);
        buf.append("{");
        buf.append(id);
        buf.append(", ");
        buf.append(name);
        buf.append("}");
        return buf.toString();
    }

}
