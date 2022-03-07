package com.picpay.wallet.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    protected LocalDateTime createdDate;

    @Column
    @UpdateTimestamp
    @EqualsAndHashCode.Exclude
    protected LocalDateTime updatedDate;

    @Transient
    protected Boolean isDeletable;

    @Override
    public String toString() {
        return "{" + "id=" + id + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }

    public BaseEntity(Long id) {
        this.id = id;
    }
}
