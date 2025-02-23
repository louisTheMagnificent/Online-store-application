package com.comp5348_project_prac12_group2.store.repository;

import com.comp5348_project_prac12_group2.store.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);


}
