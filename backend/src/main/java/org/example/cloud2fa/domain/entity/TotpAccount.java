package org.example.cloud2fa.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "totp_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TotpAccount {

   @Id
   private String id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
   private User user;

   @Column(nullable = false)
   private String accountName;

   @Column(nullable = false, columnDefinition = "TEXT")
   private String secretBlob;

   @Column(nullable = false)
   private String issuer;

   @Column(nullable = false)
   private String algorithm;

   @Column(nullable = false)
   private int digits;

   @Column(nullable = false)
   private int period;

}
