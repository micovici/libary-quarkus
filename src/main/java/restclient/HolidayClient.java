package restclient;

import jakarta.ws.rs.*;

import model.client.Country;
import model.client.Holiday;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/api/v3")
@RegisterRestClient(configKey = "holiday-api")
public interface HolidayClient {

	@GET
	@Path("/AvailableCountries")
	List<Country> getAvailableCountries();

	@GET
	@Path("/NextPublicHolidays/{countryCode}")
	List<Holiday> getNextPublicHolidays(@PathParam("countryCode") String countryCode);
}