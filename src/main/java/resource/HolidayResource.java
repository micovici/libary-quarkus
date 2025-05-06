package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.client.Country;
import model.client.Holiday;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import repository.BookRepository;
import restclient.HolidayClient;
import java.util.List;

@Path("/holiday")
public class HolidayResource {

	@Inject
	@RestClient
	HolidayClient holidayClient;

	@Inject
	BookRepository bookRepository;

	@GET
	@Path("/countries")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCountries() {
		try {
			List<Country> countries = holidayClient.getAvailableCountries();
			return Response.ok(countries).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Greška pri uzimanju država: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/nextHolidays/{countryCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNextHolidays(@PathParam("countryCode") String countryCode) {
		try {
			List<Holiday> holidays = holidayClient.getNextPublicHolidays(countryCode);
			bookRepository.saveHolidays(holidays);
			return Response.ok(holidays).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Greška pri uzimanju praznika: " + e.getMessage()).build();
		}
	}

/*	@GET
	@Path("/testHoliday/{countryCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHolidayEndpoint(@PathParam("countryCode") String countryCode) {
		try {
			List<Holiday> apiResponse = holidayClient.getNextPublicHolidays(countryCode);
			bookRepository.saveHolidays(apiResponse);
			boolean savedSuccessfully = bookRepository.checkHolidaysSaved(countryCode, apiResponse.size());
			String responseMessage = savedSuccessfully
					? "Svi praznici su uspješno sačuvani (" + apiResponse.size() + " zapisa)"
					: "Došlo je do problema pri čuvanju praznika";
			return Response.ok(responseMessage).build();
		} catch (Exception e) {
			return Response.serverError().entity("Greška pri testiranju: " + e.getMessage()).build();
		}
	}
*/
}