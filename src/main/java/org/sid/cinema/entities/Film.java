package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String realisateur;
    private String description;
    private String photo;
    private Date dateSortie;
    @OneToMany(mappedBy = "film")
    private Collection<Projection> projections;
    @ManyToOne
    private Categorie categorie;
}
