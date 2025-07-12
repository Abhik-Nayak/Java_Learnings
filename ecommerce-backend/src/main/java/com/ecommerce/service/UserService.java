// package com.ecommerce.service;

// import com.ecommerce.model.User;

// public interface UserService {
//     User registerUser(User user);
// }
package com.ecommerce.service;

import com.ecommerce.dto.UserRequestDTO;
import com.ecommerce.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO request);
}

