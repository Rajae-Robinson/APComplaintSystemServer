/**
 * Author: Rajae Robinson
 */
package factories;

import javax.persistence.PersistenceException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Advisor;
import model.Complaint;
import model.Login;
import model.Query;
import model.Student;
import model.Supervisor;

public class SessionFactoryBuilder {
	private static SessionFactory sf = null;

	public static SessionFactory getSessionFactory() {
		try { 
			if(sf == null) {
				sf = new Configuration()
						.configure("hibernate.cfg.xml")
						.addAnnotatedClass(Login.class)
						.addAnnotatedClass(Student.class)
						.addAnnotatedClass(Complaint.class)
						.addAnnotatedClass(Query.class)
						.addAnnotatedClass(Advisor.class)
						.addAnnotatedClass(Supervisor.class)
						.buildSessionFactory();
			}
			return sf;
		} catch (PersistenceException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	public static void closeSessionFactory() {
		if(sf != null) {
			sf.close();
		}
	}
}
