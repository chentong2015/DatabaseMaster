package com.hibernate5.annotation.fetching.LazyCollection;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "t_worker")
public class Worker {

    @Id
    private Long id;

    @NaturalId
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
}
