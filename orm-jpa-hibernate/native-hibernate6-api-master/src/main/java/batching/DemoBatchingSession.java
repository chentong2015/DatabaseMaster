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

    // 只能使用object对象操作
    // 或者使用PreparedStatement
    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        for (int index = 110; index < 120; index++) {
            // session.persist(new Comment(index, "Post comment"));
            String query = "INSERT INTO Comment (id, review) values (:id, :review)";
            session.createMutationQuery(query)
                    .setParameter(1, index)
                    .setParameter(2, "post comment")
                    .executeUpdate();
        }
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 先查询再执行Update, 或者直接执行Query语句
    public static void testInsertAndUpdate(String[] args) {
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
            session.merge(post);
        }
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
