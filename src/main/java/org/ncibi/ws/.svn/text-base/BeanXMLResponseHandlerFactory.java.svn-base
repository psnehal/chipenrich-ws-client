package org.ncibi.ws;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class BeanXMLResponseHandlerFactory {
	private BeanXMLResponseHandlerFactory() {
	}

	public static <T> ResponseHandler<Response<T>> newMetabolicResponseHandler() {
		return new ResponseHandler<Response<T>>() {
			public Response<T> handleResponse(HttpResponse response) {
				System.out.println("Inside newmetaboliicResponseHandler"+response.getStatusLine());
				try {
					if (nonDocumentReturnCode(response)) {
						return createHtmlErrorResponse(response);
					}
					return createResponseFromHttpResponse(response);
				} catch (IOException e) {
					return createErrorResponse(e.getMessage());
				}
			}

			private boolean nonDocumentReturnCode(HttpResponse response) {
				return response.getStatusLine().getStatusCode() != 200;
			}

			private Response<T> createResponseFromHttpResponse(
					HttpResponse response) throws IOException {
				System.out.println("Inside createResponseFromHttpResponse");
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("entity is not null");
					return createResponseFromEntity(response, entity);
				}
				return createErrorResponse("Unexpected null response from server.");
			}

			private Response<T> createResponseFromEntity(HttpResponse response,
					HttpEntity entity) throws ParseException, IOException {
				String xml = EntityUtils.toString(entity);
				System.out.println("xml is"+xml);
				BeanXMLResponseDecoder<T> decoder = new BeanXMLResponseDecoder<T>(
						xml);
				Response<T> r = decoder.fromXmlString();
				r.getResponseStatus().setHttpStatus(
						makeHttpStatusFromHttpResponse(response));
				System.out.println("Last "+r.toString());
				return r;
			}

			private Response<T> createErrorResponse(String message) {
				ResponseStatus status = new ResponseStatus(null, false, message);
				Response<T> r = new Response<T>(status, null);
				return r;
			}

			private Response<T> createHtmlErrorResponse(HttpResponse response) {
				System.out.println("I m inside createHtmlErrorResponse");
				ResponseStatus status = new ResponseStatus(null, false,
						ResponseStatusType.HTTP_ERROR,
						extractNonDocumentErrorMessage(response));
				status.setHttpStatus(makeHttpStatusFromHttpResponse(response));
				Response<T> r = new Response<T>(status, null);
				return r;
			}

			private String extractNonDocumentErrorMessage(HttpResponse response) {
				return "Status code = "
						+ response.getStatusLine().getStatusCode() + " for "
						+ response.getStatusLine().getReasonPhrase();
			}

			private ResponseHttpStatus makeHttpStatusFromHttpResponse(
					HttpResponse response) {
				return new ResponseHttpStatus(response.getStatusLine()
						.getStatusCode(), response.getStatusLine()
						.getReasonPhrase());
			}

		};
	}
}
