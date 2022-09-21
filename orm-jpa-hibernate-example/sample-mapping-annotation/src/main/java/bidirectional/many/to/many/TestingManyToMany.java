package bidirectional.many.to.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

public class TestingManyToMany {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        initData(session);
        session.close();
        sessionFactory.close();
    }

    private static void initData(Session session) {
        session.getTransaction().begin();
        Employee employee1 = new Employee("employee name 1");
        Employee employee2 = new Employee("employee name 2");
        Employee employee3 = new Employee("employee name 3");
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(employee1);
        employeeSet.add(employee2);
        employeeSet.add(employee3);

        Project project1 = new Project("project name 1");
        Project project2 = new Project("project name 2");
        Set<Project> projectSet = new HashSet<>();
        projectSet.add(project1);
        projectSet.add(project2);

        employee1.setProjects(projectSet);
        employee2.setProjects(projectSet);
        employee3.setProjects(projectSet);
        project1.setEmployees(employeeSet);
        project2.setEmployees(employeeSet);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);
        session.persist(project1);
        session.persist(project2);
        session.getTransaction().commit();
    }

    private static void getEmployeesByProject(Session session, int projectId) {
        Project project = session.get(Project.class, projectId);
        Set<Employee> employees = project.getEmployees();
        for (Employee employee : employees) {
            System.out.println(employee.getName());
        }
    }

    private static void getProjectsByEmployee(Session session, int employeeId) {
        Employee employee = session.get(Employee.class, employeeId);
        Set<Project> projects = employee.getProjects();
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }
}
