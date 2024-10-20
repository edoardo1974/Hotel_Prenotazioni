package com.Tokio.model;

public class Camera extends Dati {

    private int prezzo_notte;
    private String stato_camera;

    public Camera(int prezzo_notte, String stato_camera, String idcliente, String tipodicamera, int numerocamera) {
        super(idcliente, tipodicamera, numerocamera);
        this.prezzo_notte = prezzo_notte;
        this.stato_camera = stato_camera;
    }
}
