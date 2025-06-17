package com.chaptoporg.controller;

import com.chaptoporg.dto.GenericResponse;
import com.chaptoporg.model.Message;
import com.chaptoporg.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles messaging operations between users (e.g., tenant and
 * owner).
 */
@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Endpoints for sending messages between users")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Sends a message from a user (e.g., tenant to owner).
     *
     * @param message the message object sent in the request body
     * @return confirmation response
     */
    @Operation(summary = "Send a message", description = "Allows a user to send a message. The message content is saved in the database.", responses = {
            @ApiResponse(responseCode = "201", description = "Message sent successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GenericResponse> sendMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse("Message sent with success"));
    }
}
