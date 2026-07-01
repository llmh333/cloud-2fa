package org.example.cloud2fa.domain.dto.request;

import org.example.cloud2fa.constant.MessageKey;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to delete a TOTP account")
public class DeleteTotpAccountRequestDto {

   @Schema(description = "Master password for authentication before deletion", example = "Master@456", requiredMode = Schema.RequiredMode.REQUIRED)
   @NotBlank(message = MessageKey.ErrorMessage.Valid.NOT_BLANK)
   private String masterPassword;
}
