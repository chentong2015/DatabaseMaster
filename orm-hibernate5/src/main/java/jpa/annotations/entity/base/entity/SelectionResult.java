package jpa.annotations.entity.base.entity;

// 包含查询结果的对象实例
public class SelectionResult {

    private Person person;
    private Sample sample;

    public SelectionResult(Person person, Sample sample) {
        this.person = person;
        this.sample = sample;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    @Override
    public String toString() {
        return "SelectionResult{" +
                "person=" + person.getFirstname() + ":" + person.getLastname() +
                ", sample=" + sample.getFirstname() + ":" + sample.getLastname() +
                '}';
    }
}
