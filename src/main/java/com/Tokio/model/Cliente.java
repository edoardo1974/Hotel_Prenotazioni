
package com.Tokio.model;
import lombok.Getter;
import lombok.Setter;


public class Cliente extends Dati {
    @Getter
    @Setter
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private Prenotazioni prenotazione;


    public Cliente(String nome, String cognome, String email, String telefono, Prenotazioni prenotazione, String idcliente) {
        super(idcliente);
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.prenotazione = prenotazione;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", prenotazione=" + prenotazione +
                ", idcliente=" + getIdcliente() +
                '}';
    }
}
