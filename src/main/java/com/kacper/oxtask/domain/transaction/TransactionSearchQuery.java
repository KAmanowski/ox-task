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

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionSearchQuery {

  @JsonAlias("manufacturer")
  private String manufacturer;
  @JsonAlias("retailer_id")
  private String retailer;
  @JsonAlias("product_list")
  private List<String> productList;

  // Could be enum though that may become a pain to maintain
  @JsonAlias("sum")
  private String sum;

  @JsonAlias("start_date")
  @JsonSerialize(using= TransactionDateTimeSerializer.class)
  @JsonDeserialize(using= TransactionDateTimeDeserializer.class)
  private DateTime startDate;
  @JsonAlias("start_date")
  @JsonSerialize(using= TransactionDateTimeSerializer.class)
  @JsonDeserialize(using= TransactionDateTimeDeserializer.class)
  private DateTime endDate;
}
