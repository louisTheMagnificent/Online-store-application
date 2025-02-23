package com.comp5348_project_prac12_group2.store.util;

import com.comp5348_project_prac12_group2.store.model.Customer;
import com.comp5348_project_prac12_group2.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationHelper {

    @Autowired
    private UserRepository userRepository;

    /**
     * Check if the user is logged in.
     *
     * @param userId the ID of the user to check
     * @return true if the user exists and is logged in, false otherwise
     */
    public boolean isUserLoggedIn(Long userId) {
        Optional<Customer> customerOptional = userRepository.findById(userId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return customer.isLoggedIn();
        } else {
            return false; // Return false if user not found
        }

    }
}
