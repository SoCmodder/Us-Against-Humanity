package com.usagainsthumanity.taah_server;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.CardWhite;

public class WhiteCardJSONResource extends ServerResource {
	
	@Get("json")
	public String handleGet() {
		try{
			//TODO All of the things
			String cardID = getQuery().getValues("cardID");
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select c from CardWhite c where e.cardID = :cardID");
			q.setParameter("cardID", cardID);
			List<CardWhite> cards = q.getResultList();
			if (cards.size() == 1){
				JSONObject json = new JSONObject();
				json.put("Text", cards.get(0).getDescription());
				JsonRepresentation jsonRep = new JsonRepresentation(json);
				return jsonRep.getText();
			}else if (cards.size() > 1){
				throw new ResourceException(406);
			} else{
				throw new ResourceException(404);
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
