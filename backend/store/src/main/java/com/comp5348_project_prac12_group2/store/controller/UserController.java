package com.comp5348_project_prac12_group2.store.controller;

import com.comp5348_project_prac12_group2.store.dto.StockDTO;
import com.comp5348_project_prac12_group2.store.dto.UserDTO;
import com.comp5348_project_prac12_group2.store.service.UserService;
import com.comp5348_project_prac12_group2.store.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                         @RequestParam String password) {
        // Call the login service with username and password
        UserDTO userDTO = userService.login(username,password);

        if (userDTO != null) {
            // If userDTO is not null, login is successful
            return ResponseEntity.ok(userDTO); // Return the UserDTO with a 200 OK status
        } else {
            // If userDTO is null, login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
