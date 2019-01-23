package com.pcomm.product.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_PRODUCT")
@Getter
@Setter
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="product_name")
    protected String productName;


    @Column(name="product_category_id")
    protected Long categoryId;

    @Column(name="product_brand_id")
    protected Long brandId;

    @Column(name="create_user_id")
    protected String createUserId;

    @Column(name="update_user_id")
    protected String updateUserId;

    @Column(name="create_instance_id")
    @CreationTimestamp
    protected LocalDateTime createInstanceId;

    @Column(name="update_instance_id")
    @UpdateTimestamp
    protected LocalDateTime updateInstanceId;

    public Product() {
    }
}
