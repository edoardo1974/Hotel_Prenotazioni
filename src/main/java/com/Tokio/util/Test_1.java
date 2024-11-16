package com.Tokio.util;

import com.Tokio.model.Cliente;
import com.Tokio.model.Prenotazioni;
import com.Tokio.service.GestionePrenotazioni;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class Test_1 extends GestionePrenotazioni {
    private final List<Cliente> listaclienti = new ArrayList<Cliente>();
    private List<com.Tokio.model.Prenotazioni> listaprenotazioni = new ArrayList<com.Tokio.model.Prenotazioni>();
    private List<com.Tokio.model.Camera> listacamera = new ArrayList<com.Tokio.model.Camera>();

    @Test
    public void aggiungiClienteAddsClienteToList() {
        Test_1 test1 = new Test_1();
        test1.aggiungiCliente("Edoardo", "Mozzato", "edo.moz@gmail.com", "0496719267");
        assertEquals(1, test1.listaclienti.size());
    }
    @Test
    public void aggiungiPrenotazioneAddsBookingToList() {
        Test_1 test2 = new Test_1();
        test2.aggiungiPrenotazione("2021-12-01", "2021-12-05", 4, "202112010", 1);
        System.out.println(test2.listaprenotazioni);
    }

    @Test
    public void aggiungiCameraAddsRoomToList() {
        Test_1 test3 = new Test_1();
        test3.aggiungiCamera();
        System.out.println(test3.listacamera);
        for(int i = 0; i < test3.listacamera.size(); i++) {
            System.out.println(test3.listacamera.get(i));
        }
    }

    /*@Test
    void aggiungiClienteGeneratesUniqueId() {
        Test_1 test1 = new Test_1();
        test1.aggiungiCliente("Edoardo", "Mozzato", "edo.moz@gmail.com", "0496719267");
        Cliente cliente = test1.listaclienti.get(0);
        assertNotNull(cliente.getIdcliente());
        assertTrue(cliente.getIdcliente().matches("\\d{8}\\d{1,3}"));
    }

     */

    /*@Test
    void aggiungiClienteHandlesEmptyFields() {
        Test_1 test1 = new Test_1();
        test1.aggiungiCliente("", "", "", "");
        Cliente cliente = test1.listaclienti.get(0);
        assertEquals("", cliente.getNome());
        assertEquals("", cliente.getCognome());
        assertEquals("", cliente.getEmail());
        assertEquals("", cliente.getTelefono());
    }

     */

    /*@Test
    void aggiungiClienteHandlesNullFields() {
        Test_1 test1 = new Test_1();
        test1.aggiungiCliente(null, null, null, null);
        Cliente cliente = test1.listaclienti.get(0);
        assertNull(cliente.getNome());
        assertNull(cliente.getCognome());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

     */
    public void aggiungiPrenotazione(String data_arrivo, String data_partenza, int numero_notti, String idcliente, int numerocamera) {

        Prenotazioni prenotazione = new Prenotazioni(data_arrivo, data_partenza, numero_notti, idcliente, numerocamera);
        System.out.println(prenotazione);
        listaprenotazioni.add(prenotazione);
    }

    @Test
    public void test_add_reservation_first_client() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();
        gestionePrenotazioni.aggiungiPrenotazioneThread();

        // Assuming the listaprenotazioni is accessible and we can check its size
        assertEquals(1, gestionePrenotazioni.getListaprenotazioni().size());
        System.out.println("Test for adding reservation for the first client passed.");
    }

    @Test
    public void test_handle_exception_first_client() {
        GestionePrenotazioni gestionePrenotazioni = new GestionePrenotazioni();

        // Simulate an exception scenario, e.g., invalid input
        // This might require mocking or altering the method to throw an exception
        try {
            gestionePrenotazioni.aggiungiPrenotazioneThread();
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            System.out.println("Exception handled successfully: " + e.getMessage());
        }
    }


    public void aggiungiCliente(String nome, String cognome, String email, String telefono) {
        Random rand = new Random();
        int codice = rand.nextInt(1000);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String idcliente = today.format(formatter) + String.valueOf(codice);

        Cliente cliente = new Cliente(nome, cognome, email, telefono, null, idcliente);
        System.out.println(cliente);
        listaclienti.add(cliente);
    }



    public static void main(String[] args) {
        //Test_1 test1 = new Test_1();

        //test1.aggiungiClienteAddsClienteToList();
       // test1.aggiungiPrenotazioneAddsBookingToList();
        //test1.aggiungiCameraAddsRoomToList();
    }
}
