/**
 * Author: Rajae Robinson
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import factories.SessionFactoryBuilder;

@Entity
@Table(name = "supervisor")
public class Supervisor implements Serializable {
	private static final long serialVersionUID = 8177551965822851163L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supervisorID")
	private int supervisorID;
	
	@Column(name = "firstname")
    private String firstName;
	
	@Column(name = "lastname")
    private String lastName;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "contactNumber")
    private String contactNumber;

	public Supervisor() {
		this.supervisorID = 1991709;
		this.firstName = "Julia";
		this.lastName = "Walt";
		this.email = "juliawalt01@gmail.com";
		this.contactNumber = "282-0763";
	}
	
	public Supervisor(int supervisorID, String firstName, String lastName, String email, String contactNumber) {
		this.supervisorID = supervisorID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
	}
	
	public List<Supervisor> readAll() throws HibernateException {
		List<Supervisor> supervisors = new ArrayList<>();
		Session session = null;
		
		try {
            session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            supervisors = session.createQuery("from Supervisor", Supervisor.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        }
		
		return supervisors;
	}

	public int getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "Supervisor [supervisorID=" + supervisorID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", contactNumber=" + contactNumber + "]";
	}
}
