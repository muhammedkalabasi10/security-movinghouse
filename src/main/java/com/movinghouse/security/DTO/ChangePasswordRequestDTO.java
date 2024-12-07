package com.movinghouse.security.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
