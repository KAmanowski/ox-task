package com.kacper.oxtask.service;

import com.kacper.oxtask.domain.Manufacturer;
import com.kacper.oxtask.domain.User;
import com.kacper.oxtask.exception.NotFoundRepositoryException;
import com.kacper.oxtask.repository.ManufacturerRepository;
import com.kacper.oxtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.kacper.oxtask.config.CacheConfig.MANUFACTURER_EXISTS;

@Service
@RequiredArgsConstructor
public class UserService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
  }

  public User insert(String name) {
    UUID token = UUID.randomUUID();
    String encodedToken = bCryptPasswordEncoder.encode(token.toString());

    userRepository.insert(name, encodedToken);

    return User.builder()
        .name(name)
        .token(token.toString())
        .build();
  }

  public Boolean checkCredentials(String name, String token) {
    try {
      String hash = userRepository.fetchHash(name);

      return bCryptPasswordEncoder.matches(token, hash);
    } catch (NotFoundRepositoryException ex) {
      return false;
    }
  }
}
