package com.rich.budgetapi.domain.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.rich.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransaction type;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    @PrePersist
    private void randomCode() {
        setCode(UUID.randomUUID().toString());
    }

    public boolean isNew() {
        return getId() == null;
    }
}
