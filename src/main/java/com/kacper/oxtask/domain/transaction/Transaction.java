package com.kacper.oxtask.domain.transaction;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kacper.oxtask.util.TransactionDateTimeDeserializer;
import com.kacper.oxtask.util.TransactionDateTimeSerializer;
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
  @JsonAlias("retailer_id")
  private String retailer;
  @JsonAlias("product_code")
  private String productCode;
  @JsonAlias("quantity")
  private float quantity;
  @JsonAlias("value")
  private float value;

  @JsonAlias("transaction_date")
  @JsonSerialize(using=TransactionDateTimeSerializer.class)
  @JsonDeserialize(using= TransactionDateTimeDeserializer.class)
  private DateTime transactionDate;
}
