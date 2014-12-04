package org.adorsys.adpharma.client.jpa.deliveryitem;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.adpharma.client.jpa.article.ArticleDetailsSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.delivery.PeriodicalDeliveryDataSearchInput;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class DeliveryItemService {
	private String media = MediaType.APPLICATION_JSON;
	private static final String FIND_BY = "findBy";
	private static final String FIND_BY_LIKE_PATH = "findByLike";

	@Inject
	private ClientCookieFilter clientCookieFilter;
	@Inject
	private ServerAddress serverAddress;
	Client client = ClientBuilder.newClient();

	private WebTarget target() {
		return client.target(serverAddress + "/rest/deliveryitems").register(
				clientCookieFilter);
	}

	public DeliveryItem create(DeliveryItem entity) {
		Entity<DeliveryItem> eCopy = Entity.entity(entity, media);
		return target().request(media).post(eCopy, DeliveryItem.class);
	}

	// @DELETE
	// @Path("/{id:[0-9][0-9]*}")
	public DeliveryItem deleteById(Long id) {// @PathParam("id")
		// TODO encode id
		return target().path("" + id).request(media).delete(DeliveryItem.class);
	}

	// @PUT
	// @Path("/{id:[0-9][0-9]*}")
	// @Consumes("application/xml")
	public DeliveryItem update(DeliveryItem entity) {
		Entity<DeliveryItem> ent = Entity.entity(entity, media);
		return target().path("" + entity.getId()).request(media)
				.put(ent, DeliveryItem.class);
	}

	// @GET
	// @Path("/{id:[0-9][0-9]*}")
	// @Produces("application/xml")
	public DeliveryItem findById(Long id) {// @PathParam("id")
		return target().path("" + id).request(media).get(DeliveryItem.class);
	}

	// @GET
	// @Produces("application/xml")
	public DeliveryItemSearchResult listAll() {
		return target().request(media).get(DeliveryItemSearchResult.class);
	}

	// @GET
	// @Produces("application/xml")
	public DeliveryItemSearchResult listAll(int start, int max) {
		return target().queryParam("start", start).queryParam("max", max)
				.request(media).get(DeliveryItemSearchResult.class);
	}

	// @GET
	// @Path("/count")
	public Long count() {
		return target().path("count").request().get(Long.class);
	}

	// @POST
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DeliveryItemSearchResult findBy(DeliveryItemSearchInput searchInput) {
		Entity<DeliveryItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY).request(media)
				.post(searchInputEntity, DeliveryItemSearchResult.class);
	}

	// @POST
	// @Path("/countBy")
	// @Consumes("application/xml")
	public Long countBy(DeliveryItemSearchInput searchInput) {
		Entity<DeliveryItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countBy").request()
				.post(searchInputEntity, Long.class);
	}

	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DeliveryItemSearchResult findByLike(
			DeliveryItemSearchInput searchInput) {
		Entity<DeliveryItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path(FIND_BY_LIKE_PATH).request(media)
				.post(searchInputEntity, DeliveryItemSearchResult.class);
	}

	// @POST
	// @Path("/countByLike"
	// @Consumes("application/xml")
	public Long countByLike(DeliveryItemSearchInput searchInput) {
		Entity<DeliveryItemSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("countByLike").request()
				.post(searchInputEntity, Long.class);
	}
	
	// @POST
		// @Path("/findArticlesDetails"
		// @Consumes("application/xml")
	public ArticleDetailsSearchResult findByArticle(ArticleSearchInput searchInput) {
	Entity<ArticleSearchInput> entity = Entity.entity(searchInput, media);
	return target().path("findArticlesDetails").request().post(entity, ArticleDetailsSearchResult.class);
	}



	// @POST
	// @Path("/findByLike"
	// @Produces("application/xml")
	// @Consumes("application/xml")
	public DeliveryItemSearchResult periodicalDeliveryRepport(
			PeriodicalDeliveryDataSearchInput searchInput) {
		Entity<PeriodicalDeliveryDataSearchInput> searchInputEntity = Entity.entity(
				searchInput, media);
		return target().path("periodicalDeliveryRepport").request(media)
				.post(searchInputEntity, DeliveryItemSearchResult.class);
	}
}
