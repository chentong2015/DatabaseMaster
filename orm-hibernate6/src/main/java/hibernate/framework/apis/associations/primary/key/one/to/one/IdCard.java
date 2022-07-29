package hibernate.framework.apis.associations.primary.key.one.to.one;

import java.io.Serializable;

public class IdCard implements Serializable {

    private int id;
    private String code;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
