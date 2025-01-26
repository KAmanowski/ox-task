package com.kacper.oxtask.service;

import com.kacper.oxtask.domain.Manufacturer;
import com.kacper.oxtask.domain.Retailer;
import com.kacper.oxtask.exception.NotFoundRepositoryException;
import com.kacper.oxtask.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.kacper.oxtask.config.CacheConfig.MANUFACTURER_EXISTS;
import static com.kacper.oxtask.config.CacheConfig.RETAILER_EXISTS;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

  private final ManufacturerRepository manufacturerRepository;

  @CacheEvict(MANUFACTURER_EXISTS)
  public Manufacturer insert(String name) {
    manufacturerRepository.insert(name);

    return Manufacturer.builder()
        .name(name)
        .build();
  }

  public Boolean checkIfManufacturerExists(String name) {
    return manufacturerRepository.checkIfManufacturerExists(name);
  }
}
