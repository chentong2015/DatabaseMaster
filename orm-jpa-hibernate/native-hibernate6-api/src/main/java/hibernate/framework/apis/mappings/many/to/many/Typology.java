package hibernate.framework.apis.mappings.many.to.many;

import java.util.Set;

public class Typology {

    private int id;
    private String name;
    private Set<Strategy> strategies;

    public Typology() {
    }

    public Typology(int id, String name, Set<Strategy> strategies) {
        this.id = id;
        this.name = name;
        this.strategies = strategies;
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

    public Set<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(Set<Strategy> strategies) {
        this.strategies = strategies;
    }
}
