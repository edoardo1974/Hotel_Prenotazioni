package com.Tokio.service;
import com.Tokio.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;

import javax.xml.validation.Validator;

import static com.ibm.icu.text.PluralRules.Operand.e;

public class GestionePrenotazioni {
    @Getter
    private List<com.Tokio.model.Prenotazioni> listaprenotazioni = new ArrayList<com.Tokio.model.Prenotazioni>();
    @Getter
    private List<Cliente> listaclienti = new ArrayList<Cliente>();
    @Getter
    private List<Camera> listacamera = new ArrayList<Camera>();
    @Setter @Getter
    private int selezione;
    static Scanner scanner = new Scanner(System.in);

    public void aggiungiCamera() {
        listacamera.add(new Camera(100, "occupata", null, "singola", 1));
        listacamera.add(new Camera(150, "libera", null, "doppia", 2));
        listacamera.add(new Camera(200, "libera", null, "suite", 3));
        listacamera.add(new Camera(250, "libera", null, "suite", 4));
        listacamera.add(new Camera(300, "libera", null, "suite", 5));
        //System.out.println(" ");
    }

    public void aggiungiCliente() {
        boolean validinput = false;
        while(!validinput) {
            try {
                System.out.println("AGGIUNGI UN CLIENTE ");
                //System.out.println("Inserisci il tuo nome:");
                String nome = getInput("Inserisci il tuo nome:", this::validaNome);
                //String nome = scanner.next();
                //validaNome(nome);

                //System.out.println("Inserisci il tuo cognome:");
                String cognome = getInput("Inserisci il tuo cognome:", this::validaCognome);
                //validaCognome(cognome);

                //System.out.println("Inserisci la tua email:");
                String email = getInput("Inserisci la tua email:", this::validaEmail);
                //validaEmail(email);

                //System.out.println("Inserisci il tuo numero di telefono:");
                String telefono = getInput("Inserisci il tuo numero di telefono:", this::validaTelefono);
                //validaTelefono(telefono);

                //Random rand = new Random();
                //int codice = rand.nextInt(1000);

                //LocalDate today = LocalDate.now();
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String idcliente = generateIdCliente();

                Cliente cliente = new Cliente(nome, cognome, email, telefono, null, idcliente);
                System.out.println(cliente);
                listaclienti.add(cliente);
                validinput = true;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Si è verificato un errore: " + e.getMessage());
            }
        }
    }

    @FunctionalInterface
    public interface Validator {
        String validate(String input);
    }

    private String getInput(String prompt, Validator validator) {
        System.out.println(prompt);
        String input = scanner.next();
        return validator.validate(input);
    }

    private String generateIdCliente() {
        Random rand = new Random();
        int codice = rand.nextInt(1000);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return today.format(formatter) + codice;
    }

    public String validaNome(String nome) {
        if (!nome.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Errore: Il nome deve contenere solo lettere.");
        }
        return nome;
    }

    public String validaCognome(String cognome) {
        if (!cognome.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Errore: Il cognome deve contenere solo lettere.");
        }
        return cognome;
    }

    public String validaEmail(String email) {
        if (!email.matches("^(.+)@(.+)$")) {
            throw new IllegalArgumentException("Errore: Inserisci un'email valida.");
        }
        return email;
    }

    public String validaTelefono(String telefono) {
        if (!telefono.matches("[0-9]+")) {
            throw new IllegalArgumentException("Errore: Inserisci un numero di telefono valido.");
        }
        return telefono;
    }

    public String validaIdcliente(String idcliente) {
        if (!idcliente.matches("[0-9]{11}")) {
            throw new IllegalArgumentException("Errore: Il codice cliente deve contenere 11 numeri.");
        }
        return idcliente;
    }

    public String validaDataarrivo_partenza(String data_arrivo) {
        if (!data_arrivo.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Errore: Inserisci una data valida.");
        }
        return data_arrivo;
    }

    public String validaNumeronotti(String numero_notti) {
        int numero= Integer.parseInt(numero_notti);
        if (numero < 1) {
            throw new IllegalArgumentException("Errore: Inserisci un numero di notti valido.");
        }
        return numero_notti;
    }

    public String validaNumerocamera(String numerocamera) {
        int numero= Integer.parseInt(numerocamera);
        if (numero < 1 || numero > 5) {
            throw new IllegalArgumentException("Errore: Inserisci un numero di camera valido.");
        }
        return numerocamera;
    }




    public void visualizzaCamereDisponibili() {
        System.out.println("VISUALIZZA CAMERE DISPONIBILI ");
        for(int i = 0; i < listacamera.size(); i++) {
            if(listacamera.get(i).getStato_camera().equals("libera")) {
                System.out.println(listacamera.get(i));
            }
        }
        System.out.println(" ");
    }

    public void cancellaPrenotazione() {
        System.out.println("CANCELLA PRENOTAZIONE /n Inserisci il tuo id cliente:");
        String idcliente = scanner.next();

        for(int i=0;i<listaprenotazioni.size();i++) {
            if(listaprenotazioni.get(i).getIdcliente().equals(idcliente)) {
                listaprenotazioni.remove(i);
            }
        }
        System.out.println("PRENOTAZIONE CANCELLATA");
    }


    public void aggiungiPrenotazione() {
        boolean validinput = false;
        while(!validinput) {
            try {
                System.out.println("AGGIUNGI UNA PRENOTAZIONE ");
                System.out.println("Inserisci il tuo id cliente:");
                String idcliente = getInput("Inserisci il tuo id cliente:", this::validaIdcliente);
                //String idcliente = scanner.next();
                //validaIdcliente(idcliente);

                System.out.println("Inserisci la data di arrivo yy-mm-dd:");
                String data_arrivo = getInput("Inserisci la data di arrivo yy-mm-dd:", this::validaDataarrivo_partenza);
                //String data_arrivo = scanner.next();
                //validaDataarrivo_partenza(data_arrivo);

                //System.out.println("Inserisci la data di partenza yy-mm-dd:");
                String data_partenza = getInput("Inserisci la data di partenza yy-mm-dd:", this::validaDataarrivo_partenza);
                //String data_partenza = scanner.next();
                //validaDataarrivo_partenza(data_partenza);
                
                //System.out.println("Inserisci il numero di notti:");
                int numero_notti = Integer.parseInt(getInput("Inserisci il numero di notti:", this::validaNumeronotti));
                //validaNumeronotti(numero_notti);

                //System.out.println("Inserisci il numero della camera:");
                int numerocamera = Integer.parseInt(getInput("Inserisci il numero della camera:", this::validaNumerocamera));
                //validaNumerocamera(numerocamera);

                Prenotazioni prenotazione = new Prenotazioni(data_arrivo, data_partenza, numero_notti, idcliente, numerocamera);
                System.out.println(prenotazione);
                listaprenotazioni.add(prenotazione);
                validinput = true;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Si è verificato un errore: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Si è verificato un errore: " + e.getMessage());
            }


        }
    }

    public void aggiungiPrenotazioneThread() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            try {
                synchronized (listaprenotazioni) {
                    aggiungiPrenotazione();
                    System.out.println("Prenotazione per il primo cliente aggiunta con successo.");
                }
            } catch (Exception e) {
                System.out.println("Errore nella prenotazione del primo cliente: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                synchronized (listaprenotazioni) {
                    aggiungiPrenotazione();
                    System.out.println("Prenotazione per il secondo cliente aggiunta con successo.");
                }
            } catch (Exception e) {
                System.out.println("Errore nella prenotazione del secondo cliente: " + e.getMessage());
            }
        };

        // Esegui i task
        executor.execute(task1);
        executor.execute(task2);

        // Chiudi l'executor (non accetta più nuovi task)
        executor.shutdown();

    }




    public void visualizzaPrenotazioni() {
        System.out.println("VISUALIZZA UNA PRENOTAZIONE ");
        System.out.println("Inserisci il tuo codice utente");
        String codicecliente = scanner.next();
        for(int i = 0; i < listaprenotazioni.size(); i++) {
            if(listaprenotazioni.get(i).getIdcliente().equals(codicecliente)) {
                System.out.println(listaprenotazioni.get(i));
            }
        }

    }




    public static void main(String[] args) {

        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        gestionePrenotazioni.aggiungiCamera();

        boolean exitcicle = false;
        while(!exitcicle) {

            System.out.println("BENVENUTO NEL SISTEMA DI GESTIONE PRENOTAZIONI HOTEL TOKIO\n" +
                    "Premi 1 per registrarti come Cliente\n" +
                    "Premi 2 per prenotare una Camera\n" +
                    "Premi 3 per visualizzare le Camere Disponibili\n" +
                    "Premi 4 per visualizzare le tue Prenotazioni\n" +
                    "Premi 5 per cancellare una Prenotazione\n" +
                    "Premi 6 per uscire dal sistema\n");

            gestionePrenotazioni.setSelezione(scanner.nextInt());

            switch (gestionePrenotazioni.getSelezione()) {
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
                    exitcicle = true;
                    break;

            }
        }
    }
}