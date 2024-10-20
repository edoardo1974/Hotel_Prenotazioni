package com.Tokio.model;
import lombok.Getter;
import lombok.Setter;

public abstract class Dati {
    @Getter
    private String idcliente;
    private String tipodicamera;
    private int numerocamera;


    public Dati(String idcliente, String tipodicamera, int numerocamera) {
        this.idcliente = idcliente;
        this.tipodicamera = tipodicamera;
        this.numerocamera = numerocamera;
    }

    public Dati(String idcliente) {
        this.idcliente = idcliente;

    }

    public Dati(String idcliente, int numerocamera) {
        this.idcliente = idcliente;
        this.numerocamera = numerocamera;
    }


}