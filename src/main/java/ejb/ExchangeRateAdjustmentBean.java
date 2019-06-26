package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jpa.CurrencyAdjustment;

@Stateless
public class ExchangeRateAdjustmentBean {

	
	EntityManager entityManager;
	EntityManagerFactory entityManagerFactory;
	
    private void closeEntityManagerConnection() {
    	
    	this.entityManager.close();
    	this.entityManagerFactory.close();
    
    }
    
    private void openEntityManagerConnection() {
    	try {
    	  entityManagerFactory = Persistence.createEntityManagerFactory("client");
          entityManager = entityManagerFactory.createEntityManager();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	}
	
	public double getAdjustmentRate(String key){
		openEntityManagerConnection();
		CurrencyAdjustment ca = entityManager.find(CurrencyAdjustment.class, key);
		closeEntityManagerConnection();
		
		return ca.getAdjustmentPercentage();
	
	}
	
}
