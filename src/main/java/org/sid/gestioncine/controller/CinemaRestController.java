package org.sid.gestioncine.controller;

import lombok.Getter;
import lombok.Setter;
import org.sid.gestioncine.dao.FilmRepository;
import org.sid.gestioncine.dao.TicketRepository;
import org.sid.gestioncine.entities.Film;
import org.sid.gestioncine.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable (name = "id") Long id) throws  Exception{
        System.out.println("id = " + id);
        Film film = filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        System.out.println("photoName = " + photoName);
        File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket> listTickets = new ArrayList<>();
        ticketForm.getTickets().forEach(idTickets->{
            Ticket ticket = ticketRepository.findById(idTickets).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReserve(true);
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }
}

@Getter
@Setter
class TicketForm{
    private String nomClient;
    private List<Long> tickets = new ArrayList<>();
}
