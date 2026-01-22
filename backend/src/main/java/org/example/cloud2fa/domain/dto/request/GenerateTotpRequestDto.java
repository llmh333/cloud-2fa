package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTotpRequestDto {

   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String masterPassword;
}
