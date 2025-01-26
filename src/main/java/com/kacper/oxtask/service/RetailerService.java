package com.kacper.oxtask.service;

import com.kacper.oxtask.domain.Manufacturer;
import com.kacper.oxtask.domain.Retailer;
import com.kacper.oxtask.exception.NotFoundRepositoryException;
import com.kacper.oxtask.repository.ManufacturerRepository;
import com.kacper.oxtask.repository.RetailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kacper.oxtask.config.CacheConfig.RETAILER_EXISTS;

@Service
@RequiredArgsConstructor
public class RetailerService {

  private final RetailerRepository retailerRepository;

  @CacheEvict(RETAILER_EXISTS)
  public Retailer insert(String name) {
    retailerRepository.insert(name);

    return Retailer.builder()
        .name(name)
        .build();
  }

  public Boolean checkIfRetailerExists(String name) {
    return retailerRepository.checkIfRetailerExists(name);
  }
}
