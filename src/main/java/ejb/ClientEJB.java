package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import jpa.Client;
import utilities.DbManager;

/**
 * Session Bean implementation class ClientBean
 */

@Stateless
public class ClientEJB {

    /**
     * Default constructor. 
     */

	DbManager dbManager;
	String persistenceUnit;
	
    public ClientEJB() {
    	dbManager = DbManager.getInstance();
    	persistenceUnit = "client";
    }
     
    public void updateClient(int id, String name) {
    	Client client = getClient(id);
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	client.setId(id);
    	client.setName(name);
    	
    	try {
    		
    		EntityTransaction entityTransaction = dbManager.getEntityManager().getTransaction();
    		entityTransaction.begin();
    		
    		dbManager.getEntityManager().merge(client);
    		
    		entityTransaction.commit();
    		
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	finally {
    		dbManager.closeEntityManagerConnection();
    	}
				
	}
	
    public void removeClient(int id) {
    
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Client client  = dbManager.getEntityManager().find(Client.class, id);
    	dbManager.getEntityManager().remove(client);
    	dbManager.closeEntityManagerConnection();
    
    }
    
    public List<Client> getAll(){
    	
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Query q = dbManager.getEntityManager().createQuery("select c from Client c");
    	
    	return (List<Client>) q.getResultList();
    
    }
    
    public Client getClient(int id) {
    	
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Client client = dbManager.getEntityManager().find(Client.class, id);
    	
    	if (client == null) {
    		return null;
    	}
    	
    	dbManager.closeEntityManagerConnection();
    	
    	System.out.println("Client Returned");
    	
    	return client;
    	
    }
    
    public void addClient(int id, String name) {
    	
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Client client = new Client();
    	client.setId(id);
    	client.setName(name);
    	
    	try {
    	
	    	dbManager.getEntityManager().getTransaction().begin();
	    	dbManager.getEntityManager().persist(client);
	    	dbManager.getEntityManager().getTransaction().commit();
    	
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    	
    		dbManager.closeEntityManagerConnection();
    		System.out.println("Client added...");
    	
    	}
    }
   
}