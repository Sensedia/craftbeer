package com.beerhouse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TB_BEERS")
public class Beer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "Nome", nullable = false)
    private String name;

    @Column(name = "Ingredientes", nullable = false)
    private String ingredients;

    @Column(name = "PorcAlcool", nullable = false)
    private String alcoholContent;

    @Column(name = "Preco", nullable = false)
    private Long price;

    @Column(name = "Categoria", nullable = false)
    private String category;
}
