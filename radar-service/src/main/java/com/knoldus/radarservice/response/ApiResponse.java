package com.knoldus.radarservice.response;

/**
 * Basic contract of an API response. All HTTP responses honour this contract.
 * <p>
 * Implement this interface directly if the payload has generic types (e.g. {@code List<SomeDto>}} instead of using
 * {@link Response} (e.g. {@code Response<List<SomeDto>>}). Deserialisation of such generic types does not work out
 * of the box since the types are erased at runtime. This in turn means that it's very difficult to document such
 * generic responses using {@link com.knoldus.radarservice.annotations.FieldDescriptorDoc}.
 * <p>
 * Example of generic payload implementation:
 *
 * <pre>
 * public class CloseAccountActionsResponse extends BasicResponse {
 *
 *    &#64;FieldDescriptorDoc(value = "Available actions.", recursiveType = AccountActionsDto.class)
 *    private final List&lt;AccountActionsDto&gt; data;
 *
 *    public CloseAccountActionsResponse(List&lt;AccountActionsDto&gt; data) {
 *
 *       this.data = data;
 *    }
 *
 *    public List&lt;AccountActionsDto&gt; getData() {
 *
 *       return data;
 *    }
 * }
 * </pre>
 *
 * @author Romit Saxena
 */
public interface ApiResponse {

    /**
     * @return A brief developer-friendly description of the status. Not meant to be shown to users.
     */
    String getStatusText();

    /**
     * @return The code that is returned as a response. Note that there is no one-to-one mapping between HTTP status
     * codes and this code. Not meant to be shown to users.
     */
    String getStatusCode();
}

