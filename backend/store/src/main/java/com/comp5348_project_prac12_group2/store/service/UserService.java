package com.comp5348_project_prac12_group2.store.service;

import com.comp5348_project_prac12_group2.store.dto.UserDTO;
import com.comp5348_project_prac12_group2.store.model.Customer;
import com.comp5348_project_prac12_group2.store.repository.UserRepository;

import com.comp5348_project_prac12_group2.store.util.LoggerUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO login(String username, String password) {

        Customer customer = userRepository.findByUsername(username);

        if (customer != null && PasswordHasher.hashPassword(password).equals(customer.getHashedPassword())){
            customer.setLoggedIn(true);
            userRepository.save(customer);
            return new UserDTO(customer.getId());
        }
        return null;

    }
    @Transactional
    public Customer getSellerExistsByUsername(String username){
        return userRepository.findByUsername(username);
    }




    public static class PasswordHasher {
        public static String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashedBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
