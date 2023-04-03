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
@Table(name = "advisor")
public class Advisor implements Serializable {
	private static final long serialVersionUID = 3755315976869196098L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advisorID")
	private int advisorID;
	
	@Column(name = "firstname")
    private String firstName;
	
	@Column(name = "lastname")
    private String lastName;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "contactNumber")
    private String contactNumber;
	
	@Column(name = "supervisorID")
    private int supervisorID;
	
	public Advisor() {
		this.advisorID = 1801609;
		this.firstName = "Owen";
		this.lastName = "Lewis";
		this.email = "owenlewis01@gmail.com";
		this.contactNumber = "282-0763";
		this.supervisorID = 1991709;
	}

	public Advisor(int advisorID, String firstName, String lastName, String email, String contactNumber,
			int supervisorID) {
		this.advisorID = advisorID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.supervisorID = supervisorID;
	}
	
	public List<Advisor> readAll() throws HibernateException {
		List<Advisor> advisors = new ArrayList<>();
		Session session = null;
		
		try {
            session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            advisors = session.createQuery("from Advisor", Advisor.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        }
		
		return advisors;
	}

	public int getAdvisorID() {
		return advisorID;
	}

	public void setAdvisorID(int advisorID) {
		this.advisorID = advisorID;
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

	public int getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}

	@Override
	public String toString() {
		return "Advisor [advisorID=" + advisorID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", contactNumber=" + contactNumber + ", supervisorID=" + supervisorID + "]";
	}
}
