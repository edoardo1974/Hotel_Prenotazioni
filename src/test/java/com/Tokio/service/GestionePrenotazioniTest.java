package com.Tokio.service;

import com.Tokio.model.Cliente;
import com.Tokio.model.Prenotazioni;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GestionePrenotazioniTest {

    @Test
    void test_add_add_room() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        gestionePrenotazioni.aggiungiCamera();

        assertEquals(5, gestionePrenotazioni.getListacamera().size());
    }

    @Test
    public void test_add_client_with_valid_input() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        Scanner scanner = new Scanner("John\nDoe\njohn.doe@example.com\n1234567890\n");
        GestionePrenotazioni.scanner = scanner;

        gestionePrenotazioni.aggiungiCliente();

        assertEquals(1, gestionePrenotazioni.getListaclienti().size());
        Cliente addedClient = gestionePrenotazioni.getListaclienti().get(0);
        assertEquals("John", addedClient.getNome());
        assertEquals("Doe", addedClient.getCognome());
        assertEquals("john.doe@example.com", addedClient.getEmail());
        assertEquals("1234567890", addedClient.getTelefono());
    }

    @Test
    void test_visualizzaCamereDisponibili() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        gestionePrenotazioni.aggiungiCamera();

        gestionePrenotazioni.visualizzaCamereDisponibili();

        assertEquals(5, gestionePrenotazioni.getListacamera().size());
    }

    @Test
    void test_add_reservation_success() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        Scanner scanner = new Scanner("12345678\n2023-10-01\n2023-10-05\n4\n101\n");
        GestionePrenotazioni.scanner = scanner;
        gestionePrenotazioni.aggiungiPrenotazione();

        assertEquals(1, gestionePrenotazioni.getListaprenotazioni().size());
        Prenotazioni prenotazione = gestionePrenotazioni.getListaprenotazioni().getFirst();
        assertEquals("12345678", prenotazione.getIdcliente());
        assertEquals("2023-10-01", prenotazione.getData_arrivo());
        assertEquals("2023-10-05", prenotazione.getData_partenza());
        assertEquals(4, prenotazione.getNumero_notti());
        assertEquals(101, prenotazione.getNumerocamera());
    }


    @Test
    public void test_remove_reservation_with_valid_id() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        Prenotazioni prenotazione = new Prenotazioni("2023-10-01", "2023-10-05", 4, "20231001001", 1);
        gestionePrenotazioni.getListaprenotazioni().add(prenotazione);

        Scanner scanner = new Scanner(new java.io.ByteArrayInputStream("20231001001\n".getBytes()));

        GestionePrenotazioni.scanner = scanner;

        gestionePrenotazioni.cancellaPrenotazione();

        assertTrue(gestionePrenotazioni.getListaprenotazioni().isEmpty());
    }

    @Test
    public void test_remove_reservation_with_valid_id_last() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        Prenotazioni prenotazione = new Prenotazioni("2023-10-01", "2023-10-05", 4, "20231001001", 1);
        Prenotazioni prenotazione1 = new Prenotazioni("2023-10-20", "2023-10-22", 2, "20231001005", 3);
        gestionePrenotazioni.getListaprenotazioni().add(prenotazione);
        gestionePrenotazioni.getListaprenotazioni().add(prenotazione1);

        Scanner scanner = new Scanner(new java.io.ByteArrayInputStream("20231001001\n".getBytes()));

        GestionePrenotazioni.scanner = scanner;

        gestionePrenotazioni.cancellaPrenotazione();

        for(int i = 0; i < gestionePrenotazioni.getListaprenotazioni().size(); i++) {
            System.out.println(gestionePrenotazioni.getListaprenotazioni().get(i));
        }


    }

    @Test
    public void test_adds_dates_between_arrival_and_departure() {
        GestionePrenotazioni gp = new GestionePrenotazioni();
        int roomNumber = 1;
        gp.getRooms().put(roomNumber, new HashSet<>());

        String arrivalDate = "2024-01-01";
        String departureDate = "2024-01-03";

        gp.verificaSegiornigiaoccupati(roomNumber, arrivalDate, departureDate);

        Set<LocalDate> occupiedDays = gp.getRooms().get(roomNumber);
        assertEquals(3, occupiedDays.size());
        assertTrue(occupiedDays.contains(LocalDate.parse("2024-01-01")));
        assertTrue(occupiedDays.contains(LocalDate.parse("2024-01-02")));
        assertTrue(occupiedDays.contains(LocalDate.parse("2024-01-03")));
    }


}