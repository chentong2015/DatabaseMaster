package hibernate.framework.apis.mappings.many.to.many;

import java.util.Set;

public class Strategy {

    private int id;
    private String name;
    private Set<Typology> typologies;

    public Strategy() {
    }

    public Strategy(int id, String name, Set<Typology> typologies) {
        this.id = id;
        this.name = name;
        this.typologies = typologies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Typology> getTypologies() {
        return typologies;
    }

    public void setTypologies(Set<Typology> typologies) {
        this.typologies = typologies;
    }
}
