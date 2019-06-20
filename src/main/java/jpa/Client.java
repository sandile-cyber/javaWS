package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the clients database table.
 * 
 */
@Entity
@Table(name="clients")
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;

	@Column(name="name")
	private String name;

	public int getId() {
		return this.id;
	}
	
	public Client() {
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}