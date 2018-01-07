package app;

import logic.Decomposition;
import logic.FD;
import logic.Relations;
import logic.Table;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/relations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppServices {
    @Path("/cover")
    @POST
    public Set<FD> canonicalCover(Set<FD> fds) {
        return Relations.canonicalCover(fds);
    }

    @Path("/key")
    @GET
    public void candidateKeys() {

    }

    @Path("/3nf")
    @GET
    public Decomposition thirdNF(Set<FD> funcDep) {
        return Relations.thirdNF(funcDep);
    }

    @Path("/3.5nf")
    @GET
    public Decomposition bcdNF(Set<FD> funcDep) {
        return Relations.bcNF(funcDep);
    }

    @Path("/4nf")
    @GET
    public void fourthNF() {

    }

    @Path("/decomp")
    @GET
    public void decomposition() {

    }
}
