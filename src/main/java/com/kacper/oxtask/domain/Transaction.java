package com.kacper.oxtask.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

  @JsonAlias("transaction_id")
  private UUID transactionId;
  @JsonAlias("manufacturer")
  private String manufacturer;
  @JsonAlias("retailer")
  private String retailer;
  @JsonAlias("product_code")
  private String productCode;
  @JsonAlias("transaction_date")
  private DateTime transactionDate;
  @JsonAlias("quantity")
  private float quantity;
  @JsonAlias("value")
  private float value;
}
