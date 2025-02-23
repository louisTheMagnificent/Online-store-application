package com.comp5348_project_prac12_group2.store.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @Column()
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private boolean isSeller;


    @Column(nullable = false)
    private boolean isLoggedIn;

    public Customer(String email, String username, String hashedPassword, boolean isSeller,boolean isLoggedIn) {
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isSeller = isSeller;
        this.isLoggedIn = isLoggedIn;
    }
}

