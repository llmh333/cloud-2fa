package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;
import org.example.cloud2fa.constant.enums.DefaultAlgorithm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSecretKeyRequestDto {

   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String masterPassword;

   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String secretKey;

   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String name;

   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String issuer;

   private String algorithm = DefaultAlgorithm.HmacSHA1.name();

   @Min(value = 6, message = MessageKey.ErrorMessage.Valid.MIN)
   private int digits;

   @Min(value = 30, message = MessageKey.ErrorMessage.Valid.MIN)
   private int period = 30;
}
