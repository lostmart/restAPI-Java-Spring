package com.chaptoporg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chaptoporg.model.Message;
import com.chaptoporg.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    // Constructor injection (preferred over @Autowired)
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        ApiResponse response = new ApiResponse("Message sent with success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Inner class for structured JSON response
    private static class ApiResponse {
        private final String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
