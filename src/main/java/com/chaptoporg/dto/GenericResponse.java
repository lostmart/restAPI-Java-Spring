package com.chaptoporg.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic response object for success messages")
public class GenericResponse {

    @Schema(description = "Response message", example = "Message sent with success")
    private String message;

    public GenericResponse() {
    }

    public GenericResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
