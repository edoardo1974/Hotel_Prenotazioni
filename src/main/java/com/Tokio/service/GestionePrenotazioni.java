package com.Tokio.service;
import com.Tokio.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;
import lombok.Setter;

import static com.ibm.icu.text.PluralRules.Operand.e;

public class GestionePrenotazioni {
    @Getter
    private List<com.Tokio.model.Prenotazioni> listaprenotazioni = new ArrayList<com.Tokio.model.Prenotazioni>();
    @Getter
    private List<Cliente> listaclienti = new ArrayList<Cliente>();
    @Getter
    private List<Camera> listacamera = new ArrayList<Camera>();
    @Setter @Getter
    private Map<Integer, Set<LocalDate>> rooms = new HashMap<>();
    @Setter @Getter
    private int selezione;
    static Scanner scanner = new Scanner(System.in);

    public void aggiungiCamera() {
        listacamera.add(new Camera(100, "occupata", null, "singola", 1));
        listacamera.add(new Camera(150, "libera", null, "doppia", 2));
        listacamera.add(new Camera(200, "libera", null, "suite", 3));
        listacamera.add(new Camera(250, "libera", null, "suite", 4));
        listacamera.add(new Camera(300, "libera", null, "suite", 5));

        inizializzaCamereHashmap();

    }

    public void inizializzaCamereHashmap() {
        for (int i = 1; i <= 5; i++) {
            rooms.put(i, new HashSet<>());
        }
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
        System.out.println("CANCELLA PRENOTAZIONE /n");
        String idcliente = getInput("Inserisci il tuo id cliente:", this::validaIdcliente);

        if(listaprenotazioni.isEmpty()) {
            System.out.println("Non ci sono prenotazioni\n");
        }
        else{
            verificaseCancellarePrenotazione(idcliente);
        }

    }


    public void aggiungiPrenotazione() {
        boolean validinput = false;
        while(!validinput) {
            try {
                System.out.println("AGGIUNGI UNA PRENOTAZIONE ");
                String idcliente = getInput("Inserisci il tuo id cliente:", this::validaIdcliente);
                String data_arrivo = getInput("Inserisci la data di arrivo yy-mm-dd:", this::validaDataarrivo_partenza);
                String data_partenza = getInput("Inserisci la data di partenza yy-mm-dd:", this::validaDataarrivo_partenza);
                int numero_notti = Integer.parseInt(getInput("Inserisci il numero di notti:", this::validaNumeronotti));
                int numerocamera = Integer.parseInt(getInput("Inserisci il numero della camera:", this::validaNumerocamera));

                Prenotazioni prenotazione = new Prenotazioni(data_arrivo, data_partenza, numero_notti, idcliente, numerocamera);
                listaprenotazioni.add(prenotazione);

                verificaSegiornigiaoccupati(numerocamera,data_arrivo,data_partenza);

                //verificaNumeroCamera(numerocamera);

                listacamera.get(numerocamera-1).setStato_camera("occupata");
                System.out.println(prenotazione);

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

//verificare se cambiare procedura per la verifica delle camere occupate
   /* public void verificaNumeroCamera(int numerocamera) {
        if(listacamera.get(numerocamera-1).getStato_camera().equals("occupata")) {
            throw new IllegalArgumentException("Errore: La camera è già occupata.");
        }
    }
    
    */

    /*public void verificaGiornoCameraOccupata(int numerocamera,String data_arrivo, String data_partenza) {
        if(listaprenotazioni.get(numerocamera-1).getData_arrivo().equals(data_arrivo)) {
            throw new IllegalArgumentException("Errore: La camera è già occupata.");
        }
    }

     */



    public void verificaSegiornigiaoccupati (int numerocamera,String data_arrivo, String data_partenza){

        LocalDate startDate = LocalDate.parse(data_arrivo);
        LocalDate endDate = LocalDate.parse(data_partenza);
        List<LocalDate> giorniTotalioccupati = new ArrayList<>();
//ottiene i giorni compresi nella prenotazione e li aggiunge alla lista
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            giorniTotalioccupati.add(currentDate);
            currentDate = currentDate.plusDays(1); // Incrementa di 1 giorno
        }
        isAvailable(rooms, numerocamera, giorniTotalioccupati);//verifica se la stanza è disponibile
        rooms.get(numerocamera).addAll(giorniTotalioccupati);//aggiunge i giorni occupati alla stanza
        }



    public static boolean isAvailable(Map<Integer, Set<LocalDate>> rooms, int roomNumber, List<LocalDate> days) {
        Set<LocalDate> bookedDays = rooms.get(roomNumber); // Giorni già prenotati per la stanza
        for (LocalDate day : days) { // Controlla ogni giorno richiesto
            if (bookedDays.contains(day)) { // Se il giorno è già prenotato
                return false; // Non disponibile
            }
        }
        return true; // Tutti i giorni sono disponibili
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
        //System.out.println("Inserisci il tuo codice utente");
        String codicecliente = getInput("Inserisci il tuo id cliente:", this::validaIdcliente);
        if(listaprenotazioni.isEmpty()) {
            System.out.println("Non ci sono prenotazioni\n");
        }
        else{
            verificaseEsisteidCliente(codicecliente);
        }
    }

    public void verificaseEsisteidCliente(String idcliente) {
        int count = 0;
        for(int i = 0; i < listaprenotazioni.size(); i++) {
            if(listaprenotazioni.get(i).getIdcliente().equals(idcliente)) {
                System.out.println(listaprenotazioni.get(i)+"\n");
            }
            else count+=1;
        }
        if(listaprenotazioni.size()!=0) {
            if(count == listaprenotazioni.size()) {
                System.out.println("Non ci sono prenotazioni per questo cliente\n");
            }
        }
    }

    public void verificaseCancellarePrenotazione(String idcliente) {
        int count = 0;
        for(int i = 0; i < listaprenotazioni.size(); i++) {
            if(listaprenotazioni.get(i).getIdcliente().equals(idcliente)) {
                listaprenotazioni.remove(i);
                modificaStatocamera(i);
                System.out.println("PRENOTAZIONE CANCELLATA");
            }
            else {count+=1;}
        }
        if(listaprenotazioni.size()!=0) {
            if(count == listaprenotazioni.size()) {
                System.out.println("Non ci sono prenotazioni per questo cliente\n");
            }
        }

    }


    public void modificaStatocamera(int i){
        for (int j = 0; j < listacamera.size(); j++) {
            if (listacamera.get(j).getNumerocamera() == listaprenotazioni.get(i).getNumerocamera()) {
                listacamera.get(j).setStato_camera("libera");
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