package com.chaptoporg.dto;

import lombok.Data;

/**
 * A simple DTO used for returning error messages in API responses.
 * Typically used in HTTP 4xx or 5xx responses to communicate the reason for
 * failure.
 */
@Data
public class ErrorResponse {

    /**
     * A human-readable error message.
     */
    private String message;

    /**
     * Constructs an ErrorResponse with the specified message.
     *
     * @param message the error message to return in the response
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
}
