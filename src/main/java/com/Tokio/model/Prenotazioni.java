package com.Tokio.model;

public class Prenotazioni extends Dati {
    private String data_arrivo;
    private String data_partenza;
    private int numero_notti;

    public Prenotazioni(String data_arrivo, String data_partenza, int numero_notti, String idcliente, int numerocamera) {
        super(idcliente, numerocamera);
        this.data_arrivo = data_arrivo;
        this.data_partenza = data_partenza;
        this.numero_notti = numero_notti;
    }









}