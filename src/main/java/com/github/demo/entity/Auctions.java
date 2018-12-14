package com.github.demo.entity;

import lombok.Data;

import javax.persistence.Id;


@Data
public class Auctions {
    @Id
    private Long id;
    private String name;
}
