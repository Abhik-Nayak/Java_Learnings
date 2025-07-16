package com.abhi.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role; // BUYER or SELLER
}
