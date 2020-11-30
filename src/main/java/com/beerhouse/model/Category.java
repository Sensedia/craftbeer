package com.beerhouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ToString
@Entity
@Getter @Setter
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "category_seq")
    private Long id;

    @NotNull(message = "name: required field")
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Category_Beer",
            joinColumns={@JoinColumn(name = "beer_id")},
            inverseJoinColumns={@JoinColumn(name = "category_id")})
    private List<Beer> beers;

    public Category(String name){
        this.name = name;
    }
}
