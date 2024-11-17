package com.Tokio.model;
import lombok.Getter;
import lombok.Setter;

public class Camera extends Dati {
    @Getter
    private int prezzo_notte;
    @Getter @Setter
    private String stato_camera;

    public Camera(int prezzo_notte, String stato_camera, String idcliente, String tipodicamera, int numerocamera) {
        super(idcliente, tipodicamera, numerocamera);
        this.prezzo_notte = prezzo_notte;
        this.stato_camera = stato_camera;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "prezzo_notte=" + prezzo_notte +
                ", stato_camera=" + stato_camera + '\'' + ", tipo di camera=" + getTipodicamera() + ", numero camera=" + getNumerocamera() +
                '}';
    }

    public String getStato_camera() {
        return stato_camera;
    }
}


