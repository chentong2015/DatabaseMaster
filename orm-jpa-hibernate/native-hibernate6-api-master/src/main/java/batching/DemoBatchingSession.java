package batching;

import batching.entity.Comment;
import batching.entity.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

// TODO. Native Hibernate API上层要执行batched Query
// 1. 必须配置name="hibernate.jdbc.batch_size"参数
// 2. 必须直接使用API操作对象
public class DemoBatchingSession {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        // 1. 测试batched insert
        // session.persist(new Comment(index, "Post comment"));
        // 2. 测试batched update/delete
        List<Comment> comments = session.createQuery("FROM Comment", Comment.class).getResultList();
        for (Comment comment : comments) {
            comment.setReview("new review");
            session.merge(comment);
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
