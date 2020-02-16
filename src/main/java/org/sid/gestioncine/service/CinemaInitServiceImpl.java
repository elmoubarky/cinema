package org.sid.gestioncine.service;

import org.sid.gestioncine.dao.*;
import org.sid.gestioncine.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
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
        Stream.of("Abidjan", "Bassam", "Sikensi").forEach(nameVille->{
            Ville ville = new Ville();
            ville.setName(nameVille);
            System.out.println("ville "+ville);
            villeRepository.save(ville);
        });
        System.out.println("--- fin initialisation des villes----");

    }

    @Override
    @Transactional
    public void initCinemas() {
        System.out.println("--- debut initialisation des cinema----");
        villeRepository.findAll().forEach(v->{
            Stream.of("MAJESTIC", "SAGUIDIBA", "FAYETTE")
                    .forEach(nameCinema->{
                        Cinema cinema  = new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setNombreSalles(3+(int)(Math.random()*7));
                        cinema.setVille(v);
                        cinemaRepository.save(cinema);
                        System.out.println("cinema "+cinema);
                    });
        });
        System.out.println("--- fin initialisation des cinema----");

    }

    @Override
    @Transactional
    public void initSalles() {
        System.out.println("--- debut initialisation des salles----");
        List<Cinema> cine = cinemaRepository.findAll();
        System.out.println("liste cinema "+cine);
        cinemaRepository.findAll().forEach(cinema->{
            System.out.println("cinema "+cinema);
            System.out.println("cinema getNombreSalles "+cinema.getNombreSalles());
            for(int i=0; i<cinema.getNombreSalles(); i++){
                Salle salle =  new Salle();
                salle.setName("salle "+i+1);
                salle.setCinema(cinema);
                salle.setNombrePlace(20+(int)(Math.random()*20));
                System.out.println("salle "+salle);
                salleRepository.save(salle);
            }
        });
        System.out.println("--- fin initialisation des salles----");

    }

    @Override
    @Transactional
    public void initPlaces() {
        System.out.println("--- debut initialisation des places----");
        salleRepository.findAll().forEach(salle->{
            for(int i=0; i<salle.getNombrePlace(); i++){
                Place place =  new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                System.out.println("place "+place);
                placeRepository.save(place);
            }
        });
        System.out.println("--- fin initialisation des places----");
    }

    @Override
    @Transactional
    public void initSeances() {
        System.out.println("--- debut initialisation des seances----");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00").forEach(s->{
            try {
                Seance seance = new Seance();
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
                System.out.println("seance "+seance);
            }catch (ParseException e){
                e.printStackTrace();
            }
        });
        System.out.println("--- fin initialisation des seances----");
    }

    @Override
    @Transactional
    public void initCategories() {
        System.out.println("--- debut initialisation des categories----");
        Stream.of("Histoires", "Actions", "Frictions").forEach(cat->{
            Categorie categorie = new Categorie();
            categorie.setName(cat);
            System.out.println("categorie "+categorie);
            categorieRepository.save(categorie);
        });
        System.out.println("--- fin initialisation des categories----");
    }

    @Override
    @Transactional
    public void initFilms() {
        System.out.println("--- debut initialisation des films----");
        double [] donnees = new double[] {1,1.5,2,2.5,3};
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("Dragon Ball Z", "Yu Gi Oh", "Need For Speed", "Mortal Combat")
                .forEach(titreFilm->{
                    Film film = new Film();
                    film.setTitre(titreFilm);
                    film.setPhoto(titreFilm.replace(" ","")+".jpg");
                    film.setRealisateur("realisateur "+titreFilm);
                    film.setDuree(donnees[new Random().nextInt(donnees.length)]);
                    film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                    System.out.println("film "+film);
                    filmRepository.save(film);
                });
        System.out.println("--- fin initialisation des films----");
    }

    @Override
    @Transactional
    public void initProjections() {
        System.out.println("--- debut initialisation des projections----");
        double [] prices = new double[] {30, 40, 50, 60, 70, 90, 100};
        List<Film>films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                   // filmRepository.findAll().forEach(film -> {
                    Film film = films.get(index);
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            System.out.println("projection "+projection);
                            projectionRepository.save(projection);
                        });
                    });
              //  });
            });
        });
        System.out.println("--- fin initialisation des projections----");
    }

    @Override
    @Transactional
    public void initTickets() {
        System.out.println("--- debut initialisation des tickets----");
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setReserve(false);
                ticket.setProjection(projection);
                System.out.println("ticket "+ticket);
                ticketRepository.save(ticket);

            });
        });
        System.out.println("--- fin initialisation des tickets----");
    }
}
