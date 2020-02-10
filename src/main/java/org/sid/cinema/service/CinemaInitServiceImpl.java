package org.sid.cinema.service;

import org.sid.cinema.dao.*;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CinemaInitServiceImpl implements ICinemaInitService{

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CategorieRepository categorieRepository;



    @Override
    public void initVilles() {
        System.out.println("--- debut initialisation des villes----");
        Stream.of("Abidjan", "Bassam", "San Pedro", "Daloa", "Sikensi", "Tiassale").forEach(nameVille->{
            Ville ville = new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
        System.out.println("--- fin initialisation des villes----");

    }

    @Override
    public void initCinemas() {
        System.out.println("--- debut initialisation des cinema----");
        villeRepository.findAll().forEach(v->{
            Stream.of("MAJESTIC", "SAGUIDIBA", "FAYETTE", "CAP SUD")
                    .forEach(nameCinema->{
                        Cinema cinema  = new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setNombreSalles(3+(int)(Math.random()*7));
                        cinema.setVille(v);
                        cinemaRepository.save(cinema);
                    });
        });
        System.out.println("--- fin initialisation des cinema----");

    }

    @Override
    public void initSalles() {
        System.out.println("--- debut initialisation des salles----");
        cinemaRepository.findAll().forEach(cinema->{
            for(int i = 0; i<cinema.getNombreSalles(); i++){
                Salle salle =  new Salle();
                salle.setName("salle "+i+1);
                salle.setCinema(cinema);
                salle.setNombrePlace(20+(int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });
        System.out.println("--- fin initialisation des salles----");

    }

    @Override
    public void initPlaces() {

    }

    @Override
    public void initSeances() {

    }

    @Override
    public void initCategories() {

    }

    @Override
    public void initFilms() {

    }

    @Override
    public void initProjections() {

    }

    @Override
    public void initTickets() {

    }
}
