package com.chaptoporg.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A simple DTO used for returning success messages in API responses.
 * Typically used when an operation succeeds but doesn't need to return any data beyond a confirmation message.
 */
@Schema(description = "Generic response object for success messages")
public class GenericResponse {

    /**
     * A human-readable success message.
     */
    @Schema(description = "Response message", example = "Message sent with success")
    private String message;

    /**
     * Default constructor.
     * Required for deserialization.
     */
    public GenericResponse() {
    }

    /**
     * Constructs a GenericResponse with the specified message.
     *
     * @param message the message to return in the response
     */
    public GenericResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the message content.
     *
     * @return the success message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content.
     *
     * @param message the success message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

