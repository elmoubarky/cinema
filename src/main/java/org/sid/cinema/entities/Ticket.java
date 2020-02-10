package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomClient;
    private double prix;
    private int codePayement;
    private boolean reserve;
    @ManyToOne
    private Place place;
    @ManyToOne
    private Projection projection;
}
