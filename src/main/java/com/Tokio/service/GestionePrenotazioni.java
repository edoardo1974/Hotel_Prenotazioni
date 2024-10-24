package com.Tokio.service;
import com.Tokio.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

public class GestionePrenotazioni {
    private List<com.Tokio.model.Prenotazioni> listaprenotazioni = new ArrayList<com.Tokio.model.Prenotazioni>();
    private List<Cliente> listaclienti = new ArrayList<Cliente>();
    @Setter
    @Getter
    private int selezione;
    static Scanner scanner = new Scanner(System.in);

    /*public GestionePrenotazioni(List<Prenotazioni> listaprenotazioni) {
        this.listaprenotazioni = listaprenotazioni;
    }

     */

    public void aggiungiCamera() {
    }

    public void aggiungiCliente() {
        System.out.println("Inserisci il tuo nome:");
        String nome = scanner.next();
        System.out.println("Inserisci il tuo cognome:");
        String cognome = scanner.next();
        System.out.println("Inserisci la tua email:");
        String email = scanner.next();
        System.out.println("Inserisci il tuo numero di telefono:");
        String telefono = scanner.next();

        Random rand = new Random();
        int codice=rand.nextInt(1000);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String idcliente = today.format(formatter) + String.valueOf(codice);

        Cliente cliente = new Cliente(nome, cognome, email, telefono, null, idcliente);
        System.out.println(cliente);
        listaclienti.add(cliente);
    }

    public void visualizzaPrenotazioni() {
    }

    public void cancellaPrenotazione() {
    }

    public void aggiungiPrenotazione() {
        System.out.println("Inserisci la data di arrivo:");
        String data_arrivo = scanner.next();
        System.out.println("Inserisci la data di partenza:");
        String data_partenza = scanner.next();
        System.out.println("Inserisci il numero di notti:");
        int numero_notti = scanner.nextInt();
        System.out.println("Inserisci il tuo id cliente:");
        String idcliente = scanner.next();
        System.out.println("Inserisci il numero della camera:");
        int numerocamera = scanner.nextInt();

        Prenotazioni prenotazione = new Prenotazioni(data_arrivo, data_partenza, numero_notti, idcliente, numerocamera);
        System.out.println(prenotazione);
        listaprenotazioni.add(prenotazione);
    }

    public void visualizzaCamereDisponibili() {
    }




    public static void main(String[] args) {

        System.out.println("BENVENUTO NEL SISTEMA DI GESTIONE PRENOTAZIONI HOTEL TOKIO\n" +
                "Premi 1 per registrarti come Cliente\n" +
                "Premi 2 per prenotare una Camera\n" +
                "Premi 3 per visualizzare le Camere Disponibili\n" +
                "Premi 4 per visualizzare le tue Prenotazioni\n" +
                "Premi 5 per cancellare una Prenotazione\n" +
                "Premi 6 per uscire dal sistema\n");

        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();

        gestionePrenotazioni.setSelezione(scanner.nextInt());

        switch(gestionePrenotazioni.getSelezione()) {
            case 1:
                gestionePrenotazioni.aggiungiCliente();
                break;
            case 2:
                gestionePrenotazioni.aggiungiPrenotazione();
                break;
            case 3:
                gestionePrenotazioni.visualizzaCamereDisponibili();
                break;
            case 4:
                gestionePrenotazioni.visualizzaPrenotazioni();
                break;
            case 5:
                gestionePrenotazioni.cancellaPrenotazione();
                break;
            case 6:
                System.out.println("Arrivederci!");
                break;


        }
    }


}