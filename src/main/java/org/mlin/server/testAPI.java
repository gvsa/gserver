package org.mlin.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.mlin.id.UID;

@Path("/testapi")
@Produces("text/html")
public class testAPI {

	@GET
	@Path("/default")
	public Response resMsgDefault() {
		return Response.status(200).entity("empty query=empty response").build();
	}
	
	@GET
	@Path("/writedata/{pid}/{cid}/{name}")
	@Produces("text/plain")
	public String putTestData(
			@PathParam("pid") String pid
			,@PathParam("cid") String cid
			,@PathParam("name") String name
			){
		String res="";
		try{res=UID.createUser(new String[]{"pid",pid,"cid",cid,"name",name});}
		catch(Exception e){
			return "message="+e.getLocalizedMessage();
		}
		return res;
	}
}
