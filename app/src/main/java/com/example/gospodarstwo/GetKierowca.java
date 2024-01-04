package com.example.gospodarstwo;

public class GetKierowca {

    public GetKierowca(String id,String imie, String nazwisko, String pesel) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.id =id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public String getId() {
        return id;
    }

    String imie, nazwisko, pesel, id;
}
