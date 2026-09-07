package com.example.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p ORDER BY p.createDate DESC")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;

    @Column(name = "productName", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String productName;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX) NULL")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "images", columnDefinition = "NVARCHAR(500) NULL")
    private String images;

    @Column(name = "status")
    private int status; // 1: Đang bán, 0: Khóa

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate = new Date();

    // Liên kết ManyToOne với Category
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
}