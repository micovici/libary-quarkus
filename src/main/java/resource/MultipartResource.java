package resource;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.ws.rs.Path;

@Path("/multipart")
public class MultipartResource {
	@Schema(type = SchemaType.STRING, format = "binary")
	public static class UploadSchema {
	};
}
