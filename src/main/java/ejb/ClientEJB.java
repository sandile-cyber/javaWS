package ejb;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.Client;

/**
 * Session Bean implementation class ClientBean
 */

@Stateless
@ManagedBean	
public class ClientEJB {

    /**
     * Default constructor. 
     */

	
	EntityManager entityManager;
	EntityManagerFactory entityManagerFactory;
	
	
    public ClientEJB() {
       entityManagerFactory = Persistence.createEntityManagerFactory("client");
       entityManager = entityManagerFactory.createEntityManager();
    	// TODO Auto-generated constructor stub
    }
     
    public void updateClient(int id, String name) {
    	
    	Client client = new Client();
    	client.setId(id);
    	client.setName(name);
		entityManager.merge(client);
		
	}
	
    public void removeClient(int id) {
    	
    	Client client  = entityManager.find(Client.class, id);
    	entityManager.remove(client);
    
    }
    
    public List<Client> getAll(){
    
    	Query q = entityManager.createQuery("select c from client c");
    	
    	return (List<Client>) q.getResultList();
    
    }
    
    public Client getClient(int id) {
    	
    	Client client = entityManager.find(Client.class, id);
    	
    	return client;
    	
    }
    
    public void addClient(int id, String name) {
    	
    	Client client = new Client();
    	client.setId(id);
    	client.setName(name);
    	
    	entityManager.getTransaction().begin();
    	entityManager.persist(client);
    	entityManager.getTransaction().commit();
    	
    }
    
    
    


}