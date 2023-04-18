package com.myapp.ems.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First Name cannot be empty.")
    @Size(min = 3,max = 50,message = "First Name must be between 3 and 50.")
    @Column(name = "first_name")
    private String firstName;
    @Size(min = 3,max = 50,message = "Last Name must be between 3 and 50.")
    @NotBlank(message = "Last Name cannot be empty.")
    @Column(name = "last_name")
    private String lastName;
    @Email(message = "Email Invalid.")
    @NotBlank(message = "Email cannot be empty.")
    @Column(name = "email")
    private String email;
}
