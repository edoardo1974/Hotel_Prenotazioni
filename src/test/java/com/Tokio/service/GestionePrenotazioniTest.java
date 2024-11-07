package com.Tokio.service;

import com.Tokio.model.Cliente;
import com.Tokio.model.Prenotazioni;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

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
}