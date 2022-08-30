package batching;

import batching.entity.Comment;
import batching.entity.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DemoBatchingSession {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        int indexComment = 30;
        for (int i = 1; i < 5; i++) {
            session.persist(new Comment(indexComment++, "Post comment"));
        }
        // List<Comment> comments = session.createQuery("FROM Comment", Comment.class).getResultList();
        // for (Comment comment : comments) {
        //     session.remove(comment);
        // }

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public static void main1(String[] args) {
        Session session = sessionFactory.openSession();
        session.setJdbcBatchSize(3);

        session.getTransaction().begin();
        int indexComment = 1;
        int batchSize = 3;
        for (int i = 1; i < 5; i++) {
            // 避免OutOfMemoryException异常
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
            Post post = new Post(i, String.format("Post no. %d", i));
            post.addComment(new Comment(indexComment++, "Post comment"));
            post.addComment(new Comment(indexComment++, "Post comment"));
            post.addComment(new Comment(indexComment++, "Post comment"));
            session.persist(post);
        }
        session.getTransaction().commit();

        String hqlQuery = "select distinct p from Post p join fetch p.comments c";
        List<Post> posts = session.createQuery(hqlQuery, Post.class).list();

        session.getTransaction().begin();
        for (Post post : posts) {
            post.setTitle("new " + post.getTitle());
            for (Comment comment : post.getComments()) {
                comment.setReview("new review " + comment.getReview());
            }
            // Update persist object data
            session.merge(post);
        }
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 排序执行
    // Query:{[insert into Post (title, version, id) values (?, ?, ?)][Post no. 0,0,1]} {[insert into Post (title, version, id) values (?, ?, ?)][Post no. 1,0,2]} {[insert into Post (title, version, id) values (?, ?, ?)][Post no. 2,0,3]}
    // Query:{[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][1,Post comment 0:0,0,51]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][1,Post comment 0:1,0,52]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][2,Post comment 1:0,0,53]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][2,Post comment 1:1,0,54]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][3,Post comment 2:0,0,55]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][3,Post comment 2:1,0,56]}
    //  
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][1,Blog Post comment 0:0,1,51,0]}
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][1,Blog Post comment 0:1,1,52,0]}
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][2,Blog Post comment 1:0,1,53,0]}
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][2,Blog Post comment 1:1,1,54,0]}
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][3,Blog Post comment 2:0,1,55,0]}
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][3,Blog Post comment 2:1,1,56,0]}
    //  
    // Query:{[update Post set title=?, version=? where id=? and version=?][Blog Post no. 0,1,1,0]}
    // Query:{[update Post set title=?, version=? where id=? and version=?][Blog Post no. 1,1,2,0]}
    // Query:{[update Post set title=?, version=? where id=? and version=?][Blog Post no. 2,1,3,0]}

    // 将insert和update指定数量内的语句打包执行
    // Query:{[insert into Post (title, version, id) values (?, ?, ?)][Post no. 0,0,1]} {[insert into Post (title, version, id) values (?, ?, ?)][Post no. 1,0,2]} {[insert into Post (title, version, id) values (?, ?, ?)][Post no. 2,0,3]}
    // Query:{[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][1,Post comment 0:0,0,51]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][1,Post comment 0:1,0,52]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][2,Post comment 1:0,0,53]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][2,Post comment 1:1,0,54]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][3,Post comment 2:0,0,55]} {[insert into Comment (post_id, review, version, id) values (?, ?, ?, ?)][3,Post comment 2:1,0,56]}
    //
    // Query:{[update Comment set post_id=?, review=?, version=? where id=? and version=?][1,Blog Post comment 0:0,1,51,0]} {[update Comment set post_id=?, review=?, version=? where id=? and version=?][1,Blog Post comment 0:1,1,52,0]} {[update Comment set post_id=?, review=?, version=? where id=? and version=?][2,Blog Post comment 1:0,1,53,0]} {[update Comment set post_id=?, review=?, version=? where id=? and version=?][2,Blog Post comment 1:1,1,54,0]} {[update Comment set post_id=?, review=?, version=? where id=? and version=?][3,Blog Post comment 2:0,1,55,0]} {[update Comment set post_id=?, review=?, version=? where id=? and version=?][3,Blog Post comment 2:1,1,56,0]}
    // Query:{[update Post set title=?, version=? where id=? and version=?][Blog Post no. 0,1,1,0]} {[update Post set title=?, version=? where id=? and version=?][Blog Post no. 1,1,2,0]} {[update Post set title=?, version=? where id=? and version=?][Blog Post no. 2,1,3,0]}
}
