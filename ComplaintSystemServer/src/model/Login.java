/**
 * Author: Rajae Robinson
 */
package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import factories.SessionFactoryBuilder;

@Entity
@Table(name = "login")
public class Login implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "password")
	private String password;
	
	public Login() {
		this.id = 2006677;
		this.password = "password";
	}
	
	public Login(int id, String password) {
		this.id = id;
		this.password = password;
	}

    public static boolean authenticate(ObjectInputStream input, ObjectOutputStream output) throws IOException {
        int id = 0;
        String password = "";
		try {
			id = Integer.parseInt((String) input.readObject());
			password = (String) input.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
	    Session session = null;
	    try {
	        session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
	        session.beginTransaction();
	        Query<Login> query = session.createQuery("from Login where id = :id and password = :password", Login.class);
	        query.setParameter("id", id);
	        query.setParameter("password", password);
	        Login login = query.uniqueResult();
	        session.getTransaction().commit();
	        output.writeObject(login != null);
	        return login != null;
	    } catch (HibernateException e) {
	        if (session != null && session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        throw e;
	    }
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

