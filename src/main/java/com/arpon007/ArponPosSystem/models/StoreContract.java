package com.arpon007.ArponPosSystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Embeddable
public class StoreContract {
    private String address;
  //  @Column(nullable = false)
    private String Phone;
    @Email(message = "invalid email format")
    private String email;
}
