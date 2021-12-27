package io.musala.thedrone.rest;

import io.musala.thedrone.model.Drone;
import io.musala.thedrone.model.Medication;
import io.musala.thedrone.service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/drones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class Drones {
    @Inject
    private DroneService droneService;

    @GET
    @Operation(
            summary = "Get available drones",
            responses = {
                    @ApiResponse(
                            description = "List containing all available drones for loading",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Drone.class
                                            )
                                    )
                            )
                    )

            })
    public Response getAvailableDrones() {
        return Response.ok().entity(droneService.getAvailableDrones()).build();
    }

    @POST
    @Operation(
            summary = "Register Drone",
            responses = {
                    @ApiResponse(
                            description = "Returns the url of the created drone",
                            responseCode = "201"
                    ),


            }
    )
    public Response registerDrone(@Valid Drone drone, @Context UriInfo uriInfo) {
        var savedDrone = droneService.registerDrone(drone);
        URI location = uriInfo.getBaseUriBuilder()
                .path(Drones.class)
                .path(String.valueOf(savedDrone.getId()))
                .build();
        return Response.created(location).build();
    }

    @POST
    @Path("{droneId}/load-medications")
    @Operation(
            summary = "Load Drone Medication",
            responses = {
                    @ApiResponse(
                            description = "Returns the status of 200 for successful loading",
                            responseCode = "201"
                    ),


            }
    )
    public Response loadDroneMedication(@PathParam("droneId") Long droneId, @Valid List<Medication> medications) throws Exception {
        droneService.loadDroneMedication(droneId, medications);
        return Response.ok().build();
    }

    @GET
    @Path("{droneId}/medications")
    @Operation(
            summary = "Get drone medications",
            responses = {
                    @ApiResponse(
                            description = "List containing all medications for the specific drone",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Medication.class
                                            )
                                    )
                            )
                    )

            })
    public Response getDroneMedications(@PathParam("droneId") Long droneId) {
        return Response.ok().entity(droneService.getDroneMedications(droneId)).build();
    }


    @GET
    @Path("{droneId}/battery-level")
    @Operation(
            summary = "Get Drone battery level",
            responses = {
                    @ApiResponse(
                            description = "Returns the battery level of the drone ",
                            responseCode = "200"
                    ),


            }
    )
    public Response getBatteryLevel(@PathParam("droneId") Long droneId) {
        return Response.ok().entity(droneService.getBatteryLevel(droneId)).build();
    }

}