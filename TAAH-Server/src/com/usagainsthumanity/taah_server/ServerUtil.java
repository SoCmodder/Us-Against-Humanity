package com.usagainsthumanity.taah_server;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.usagainsthumanity.taah_model.UserKey;

public class ServerUtil {
	
	public Long getUserID(Object apiKey){
		EntityManager em = EMFService.get().createEntityManager();
		Query q =em.createQuery("SELECT uk FROM UserKey uk WHERE uk.APIKey = :APIKey");
		q.setParameter("APIKey", (String) apiKey);
		return ((UserKey)(q.getResultList().get(0))).getUserID();
	}
	
	

}
