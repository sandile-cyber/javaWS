package ejb;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.Client;

/**
 * Session Bean implementation class ClientBean
 */

@Stateless
public class ClientEJB {

    /**
     * Default constructor. 
     */

	EntityManager entityManager;
	EntityManagerFactory entityManagerFactory;
	
    public ClientEJB() {
    	
    }
    
    private void closeEntityManagerConnection() {
    	this.entityManager.close();
    	this.entityManagerFactory.close();
    }
    
    private void openEntityManagerConnection() {
    	  entityManagerFactory = Persistence.createEntityManagerFactory("client");
          entityManager = entityManagerFactory.createEntityManager();
    }
     
    public void updateClient(int id, String name) {
    	Client client = getClient(id);
    	openEntityManagerConnection();
    	client.setId(id);
    	client.setName(name);
    	
    	try {
    		EntityTransaction entityTransaction = entityManager.getTransaction();
    		entityTransaction.begin();
    		
    		entityManager.merge(client);
    		
    		entityTransaction.commit();
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	finally {
    		closeEntityManagerConnection();
    	}
				
	}
	
    public void removeClient(int id) {
    
    	openEntityManagerConnection();
    	Client client  = entityManager.find(Client.class, id);
    	entityManager.remove(client);
    	closeEntityManagerConnection();
    
    }
    
    public List<Client> getAll(){
    	
    	openEntityManagerConnection();
    	Query q = entityManager.createQuery("select c from Client c");
    	
    	return (List<Client>) q.getResultList();
    
    }
    
    public Client getClient(int id) {
    	openEntityManagerConnection();
    	Client client = entityManager.find(Client.class, id);
    	
    	if (client == null) {
    		return null;
    	}
    	
    	closeEntityManagerConnection();
    	
    	System.out.println("Client Returned");
    	
    	return client;
    	
    }
    
    public void addClient(int id, String name) {
    	
    	openEntityManagerConnection();
    	Client client = new Client();
    	client.setId(id);
    	client.setName(name);
    	
    	try {
    	
    	entityManager.getTransaction().begin();
    	entityManager.persist(client);
    	entityManager.getTransaction().commit();
    	
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		closeEntityManagerConnection();
    		System.out.println("Client added...");
    	}
    	
    }
   
}