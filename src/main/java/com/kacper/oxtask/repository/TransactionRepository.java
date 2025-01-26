package com.kacper.oxtask.repository;

import com.kacper.oxtask.domain.transaction.Transaction;
import com.kacper.oxtask.domain.transaction.TransactionSearchQuery;
import com.kacper.oxtask.repository.resultsetextractor.TransactionResultSetExtractor;
import com.kacper.oxtask.repository.resultsetextractor.TransactionSearchResultSetExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

  private final static String INSERT_TRANSACTION = ""
      + "INSERT INTO public.transaction (retailer_id, manufacturer_id, transaction_date, product_code, quantity, value) "
      + "VALUES (:retailerId, :manufacturerId, :transactionDate, :productCode, :quantity, :value)";

  private final static String DELETE_ALL_TRANSACTIONS = ""
      + "DELETE FROM public.transaction";

  private final static String FETCH_TRANSACTION = ""
      + "SELECT * FROM public.transaction ";

  private final static String SEARCH_TRANSACTION = ""
      + "SELECT product_code, SUM(:sum) AS sum_total "
      + "FROM public.transaction "
      + "WHERE (:manufacturerId IS NULL OR manufacturer_id = :manufacturerId) "
      + "AND (:retailerId IS NULL OR retailer_id = :retailerId) "
      + "AND (cast(:startDate as timestamp) IS NULL OR transaction_date >= cast(:startDate as timestamp)) "
      + "AND (cast(:endDate as timestamp) IS NULL OR transaction_date <= cast(:endDate as timestamp)) ";

  private final static String PRODUCT_LIST = ""
      + "AND product_code IN (:productList) ";

  private final static String GROUP_BY = ""
      + "GROUP BY product_code, :sum ";

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final TransactionResultSetExtractor transactionResultSetExtractor;
  private final TransactionSearchResultSetExtractor transactionSearchResultSetExtractor;

  public UUID insert(Transaction transaction) {
    MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    sqlParameterSource.addValue("retailerId", transaction.getRetailer());
    sqlParameterSource.addValue("manufacturerId", transaction.getManufacturer());
    sqlParameterSource.addValue("transactionDate", new Timestamp(transaction.getTransactionDate().getMillis()));
    sqlParameterSource.addValue("productCode", transaction.getProductCode());
    sqlParameterSource.addValue("quantity", transaction.getQuantity());
    sqlParameterSource.addValue("value", transaction.getValue());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(INSERT_TRANSACTION, sqlParameterSource, keyHolder);

    return UUID.fromString(String.valueOf(keyHolder.getKeyList().get(0).get("transaction_id")));
  }

  public void deleteAll() {
    namedParameterJdbcTemplate.update(DELETE_ALL_TRANSACTIONS, new MapSqlParameterSource());
  }

  public List<Transaction> getAllTransactions() {
    return namedParameterJdbcTemplate.query(FETCH_TRANSACTION, new MapSqlParameterSource(),
        transactionResultSetExtractor);
  }

  public Map<String, Float> searchTransactions(TransactionSearchQuery transactionSearchQuery) {
    MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
    sqlParameterSource.addValue("manufacturerId", transactionSearchQuery.getManufacturer());
    sqlParameterSource.addValue("retailerId", transactionSearchQuery.getRetailer());
    sqlParameterSource.addValue("startDate", transactionSearchQuery.getStartDate() != null
        ? new Timestamp(transactionSearchQuery.getStartDate().getMillis()) : null);
    sqlParameterSource.addValue("endDate", transactionSearchQuery.getEndDate() != null
        ? new Timestamp(transactionSearchQuery.getEndDate().getMillis()) : null);

    String sql = SEARCH_TRANSACTION;

    if (!CollectionUtils.isEmpty(transactionSearchQuery.getProductList())) {
      sql += PRODUCT_LIST;
      sqlParameterSource.addValue("productList", transactionSearchQuery.getProductList());
    }

    sql += GROUP_BY;
    sql = sql.replaceAll(":sum", transactionSearchQuery.getSum());

    return namedParameterJdbcTemplate.query(sql, sqlParameterSource, transactionSearchResultSetExtractor);
  }
}
