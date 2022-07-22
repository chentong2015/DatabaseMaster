package jpa.annotations.entity.inheritance;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseClassEntity extends SuperClassEntity {

    @Column(name = "amount", nullable = false)
    private double amount;

    public BaseClassEntity() {
    }

    // 子类继承于父类的信息，在构造时调用父类构造器
    public BaseClassEntity(String name, double amount) {
        super(name);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
