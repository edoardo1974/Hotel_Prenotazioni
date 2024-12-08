package com.Tokio.model;
import lombok.Getter;
import lombok.Setter;

public class Prenotazioni extends Dati {
    @Getter
    private String data_arrivo;
    @Getter
    private String data_partenza;
    @Getter
    private int numero_notti;

    public Prenotazioni(String data_arrivo, String data_partenza, int numero_notti, String idcliente, int numerocamera) {
        super(idcliente, numerocamera);
        this.data_arrivo = data_arrivo;
        this.data_partenza = data_partenza;
        this.numero_notti = numero_notti;
    }

    @Override
    public String toString() {
        return "Prenotazioni{" +
                "data_arrivo='" + data_arrivo + '\'' +
                ", data_partenza='" + data_partenza + '\'' +
                ", numero_notti=" + numero_notti +", idcliente=" + getIdcliente() + ", numerocamera=" + getNumerocamera() +
                '}'+"\n";
    }
}