package com.example.gospodarstwo;

public class GetZbiornik {
    public String getNumer_zbiornika() {
        return Numer_zbiornika;
    }

    public String getIlosc_paliwa_w_zbiorniku() {
        return ilosc_paliwa_w_zbiorniku;
    }

    public String getPojemnosc_zbiornika() {
        return pojemnosc_zbiornika;
    }

    public GetZbiornik(String numer_zbiornika, String ilosc_paliwa_w_zbiorniku, String pojemnosc_zbiornika) {
        Numer_zbiornika = numer_zbiornika;
        this.ilosc_paliwa_w_zbiorniku = ilosc_paliwa_w_zbiorniku;
        this.pojemnosc_zbiornika = pojemnosc_zbiornika;
    }


    String Numer_zbiornika, ilosc_paliwa_w_zbiorniku, pojemnosc_zbiornika;
}
