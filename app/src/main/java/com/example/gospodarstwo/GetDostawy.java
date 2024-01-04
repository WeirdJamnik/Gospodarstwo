package com.example.gospodarstwo;

public class GetDostawy {

    public String getData() {
        return data;
    }

    public String getIlosc_paliwa() {
        return ilosc_paliwa;
    }

    public String getId_zbiornika() {
        return id_zbiornika;
    }

    public GetDostawy(String data, String ilosc_paliwa, String id_zbiornika) {
        this.data = data;
        this.ilosc_paliwa = ilosc_paliwa;
        this.id_zbiornika = id_zbiornika;
    }

    String data, ilosc_paliwa, id_zbiornika;
}
