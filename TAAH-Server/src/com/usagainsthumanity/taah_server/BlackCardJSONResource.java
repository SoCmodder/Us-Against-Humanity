package com.usagainsthumanity.taah_server;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.usagainsthumanity.taah_model.CardBlack;

public class BlackCardJSONResource extends ServerResource {
	
	@Get("json")
	public String handleGet() {
		try{
			//TODO All of the things
			String cardId = getQuery().getValues("cardID");
			EntityManager em = EMFService.get().createEntityManager();
			Query q = em.createQuery("select c from CardBlack c where c.cardID = :cardID");
			q.setParameter("cardID", cardId);
			CardBlack theCard = (CardBlack)q.getSingleResult();
			JSONObject json = new JSONObject();
			json.put("Text", theCard.getCardText());
			JsonRepresentation jsonRep = new JsonRepresentation(json);
			return jsonRep.getText();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

}
