package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTotpAccountRequestDto {

   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String masterPassword;

   private String accountName;

   private String issuer;

   private String algorithm;

   @Min(value = 6, message = MessageKey.ErrorMessage.Valid.MIN)
   private Integer digits;

   @Min(value = 30, message = MessageKey.ErrorMessage.Valid.MIN)
   private Integer period;
}
