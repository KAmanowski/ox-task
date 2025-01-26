package com.kacper.oxtask.service;

import com.kacper.oxtask.domain.transaction.Transaction;
import com.kacper.oxtask.domain.transaction.TransactionSearchQuery;
import com.kacper.oxtask.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;

  public Transaction insert(Transaction transaction) {
    UUID transactionId = transactionRepository.insert(transaction);

    transaction.setTransactionId(transactionId);
    return transaction;
  }

  public void deleteAll() {
    transactionRepository.deleteAll();
  }

  public List<Transaction> fetchAllTransactions() {
    return transactionRepository.getAllTransactions();
  }

  public Map<String, Float> searchTransactions(TransactionSearchQuery transactionSearchQuery) {
    return transactionRepository.searchTransactions(transactionSearchQuery);
  }
}
