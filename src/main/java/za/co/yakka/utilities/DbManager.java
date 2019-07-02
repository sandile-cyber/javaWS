package za.co.yakka.utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbManager {
	
	static DbManager obj = new DbManager();
	
	EntityManager entityManager;
	EntityManagerFactory entityManagerFactory;
	
	private DbManager() {
		
	}
	
	public static DbManager getInstance() {
		return obj;
	}
	
    public void openEntityManagerConnection(String persistenceUnit) {
    	
    	try {
    		
    	  this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
          this.entityManager = entityManagerFactory.createEntityManager();
          
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		}	
    }
    
    public void closeEntityManagerConnection() {
    	try {
	    	this.entityManager.close();
	    	this.entityManagerFactory.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public EntityManager getEntityManager() {
    	return this.entityManager;
    }

}
