package com.example.gospodarstwo;

public class GetZamowienie {

    public String getImie_zam() {
        return imie_zam;
    }

    public String getNazwisko_zam() {
        return nazwisko_zam;
    }

    public String getData_zam() {
        return data_zam;
    }

    public String getModel_zam() {
        return model_zam;
    }

    public String getMarka_zam() {
        return marka_zam;
    }

    public String getNazw_prod_zam() {
        return nazw_prod_zam;
    }

    public String getIlosc_zam() {
        return ilosc_zam;
    }

    public String getCena_zam() {
        return cena_zam;
    }

    public String getData_odbioru_zam() {
        return data_odbioru_zam;
    }

    public String getId() {
        return id;
    }

    String imie_zam, nazwisko_zam, data_zam, model_zam, marka_zam, nazw_prod_zam,ilosc_zam, cena_zam, data_odbioru_zam, id;

    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    boolean isVisible;

    public GetZamowienie(String id, String imie, String nazwisko, String data_zamowienia, String marka, String model, String nazwa_prod, String ilosc, String cena, String data_odbioru, boolean isVisible) {
        this.id = id;
        this.imie_zam = imie;
        this.nazwisko_zam = nazwisko;
        this.data_zam = data_zamowienia;
        this.model_zam = model;
        this.marka_zam = marka;
        this.nazw_prod_zam = nazwa_prod;
        this.ilosc_zam = ilosc;
        this.cena_zam = cena;
        this.data_odbioru_zam = data_odbioru;
        this.isVisible = false;
    }
}
