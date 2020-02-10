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
public class Place implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double longitude, latitude, altitude;
    @ManyToOne
    private Salle salle;
    @OneToMany(mappedBy = "place")
    private Collection <Ticket> tickets;
}
