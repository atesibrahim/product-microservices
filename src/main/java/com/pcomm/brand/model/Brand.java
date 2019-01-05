package com.pcomm.brand.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_BRAND")
@Getter
@Setter
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="brand_name")
    protected String brandName;


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

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                ", createInstanceId=" + createInstanceId +
                ", updateInstanceId=" + updateInstanceId +
                '}';
    }
}
