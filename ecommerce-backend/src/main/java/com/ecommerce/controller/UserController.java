// package com.ecommerce.controller;

// import com.ecommerce.model.User;
// import com.ecommerce.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     @PostMapping("/signup")
//     public ResponseEntity<User> signup(@RequestBody User user) {
//         User savedUser = userService.registerUser(user);
//         return ResponseEntity.ok(savedUser);
//     }
// }
package com.ecommerce.controller;

import com.ecommerce.dto.UserRequestDTO;
import com.ecommerce.dto.UserResponseDTO;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
        // public ResponseEntity<UserResponseDTO> signup(@RequestBody @Valid UserRequestDTO userDTO) {✅ Note: @Valid must be before @RequestBody or right on top of it.
        public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO created = userService.registerUser(userDTO);
        return ResponseEntity.ok(created);
    }    
    
}
