package batching.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_batching_comment")
public class Comment {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "review")
    private String review;

    public Comment() {
    }

    public Comment(int id, String review) {
        this.id = id;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", review='" + review + '\'' +
                '}';
    }
}
