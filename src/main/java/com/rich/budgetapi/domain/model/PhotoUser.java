package com.rich.budgetapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhotoUser {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private Long id;

    private String fileName;

    private String contentType;

    private Long size;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
