package com.example.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(100)")
    private String fullname;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "role")
    private int role; // 1: Admin, 0: User

    @Column(name = "status")
    private int status; // 0: Chờ kích hoạt OTP, 1: Đã kích hoạt

    @Column(name = "otp", length = 10)
    private String otp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "otp_expiry")
    private Date otpExpiry;

    @Column(name = "avatar", columnDefinition = "NVARCHAR(255)")
    private String avatar;
}