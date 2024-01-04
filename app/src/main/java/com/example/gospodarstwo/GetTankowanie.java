package com.example.gospodarstwo;

public class GetTankowanie {
    String data_tank, imie, nazwisko, ilosc_zatankowanego_paliwa, marka, model, przebieg, numer_zbiornika;
    boolean isVisible;


    public GetTankowanie(String imie, String nazwisko, String ilosc_zatankowanego_paliwa,
                         String data_tank, String marka, String model, String przebieg, String numer_zbiornika, boolean isVisible) {
        this.data_tank = data_tank;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.ilosc_zatankowanego_paliwa = ilosc_zatankowanego_paliwa;
        this.marka = marka;
        this.model = model;
        this.przebieg = przebieg;
        this.numer_zbiornika = numer_zbiornika;
        this.isVisible = false;
    }
    public String getData_tank() {
        return data_tank;
    }
    public String getImie() {
        return imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public String getIlosc_zatankowanego_paliwa() {
        return ilosc_zatankowanego_paliwa;

    }
    public String getMarka() {
        return marka;
    }
    public String getModel() {
        return model;
    }
    public String getPrzebieg() {
        return przebieg;
    }
    public String getNumer_zbiornika() {
        return numer_zbiornika;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
