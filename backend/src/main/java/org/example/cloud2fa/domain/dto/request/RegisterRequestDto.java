package org.example.cloud2fa.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

   @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Username must be 6-20 characters long and contain only letters and numbers")
   private String username;

   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters and contain at least one digit, one lower case, one upper case and one special character")
   private String password;

   @Email(message = "Email invalid")
   private String email;

   @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
   private String phone;
}
