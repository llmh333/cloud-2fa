package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;

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

   @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = MessageKey.ErrorMessage.Valid.USERNAME)
   private String username;

   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = MessageKey.ErrorMessage.Valid.PASSWORD)
   private String password;

   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = MessageKey.ErrorMessage.Valid.MASTER_PASSWORD)
   private String masterPassword;

   @Email(message = MessageKey.ErrorMessage.Valid.EMAIL)
   private String email;

   @Pattern(regexp = "^[0-9]{10}$", message = MessageKey.ErrorMessage.Valid.PHONE)
   private String phone;
}
