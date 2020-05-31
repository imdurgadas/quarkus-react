package net.quarkify.posts;

import net.quarkify.data.*;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobPostResource {
    @GET
    @Operation(operationId = "getPosts")
    public List<JobPost> getAll() {
        return JobPost.findAll().list();
    }

    @POST
    @Transactional
    public JobPost submit(JobPost post) {
        post.persistAndFlush();
        return post;
    }

    @GET
    @Path("/{id}/proposals")
    @Operation(operationId = "getJobProposals")
    public List<JobProposal> getAllProposals(@PathParam("id") Long id) {
        return JobProposal.find("job_post_id", id).list();
    }

    @POST
    @Path("/{id}/proposals")
    @Transactional
    public JobProposal submitProposal(@PathParam("id") Long id, JobProposal jobProposal) {
        JobPost jobPost = JobPost.findById(id);
        jobProposal.persistAndFlush();
        jobProposal.jobPost = jobPost;
        return jobProposal;
    }

}
