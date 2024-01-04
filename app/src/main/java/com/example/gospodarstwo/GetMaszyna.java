package com.example.gospodarstwo;

public class GetMaszyna {

    public String getId() {
        return id;
    }

    public String id, poj_zbior, moc, poj_sil, masa, przebieg, model, marka, numer_rejestracji, data_prod, data_przegl;
    boolean isVisible;

    public String getModel() {
        return model;
    }

    public GetMaszyna(String id,String model, String marka, String numer_rejestracji, String przebieg, String poj_zbior, String moc, String poj_sil, String masa, String data_prod, String data_przegl, boolean isVisible) {
        this.id = id;
        this.model = model;
        this.marka = marka;
        this.numer_rejestracji = numer_rejestracji;
        this.przebieg = przebieg;
        this.poj_zbior = poj_zbior;
        this.moc = moc;
        this.poj_sil = poj_sil;
        this.masa = masa;
        this.data_prod = data_prod;
        this.data_przegl = data_przegl;
        this.isVisible = false;
    }

    public String getMarka() {
        return marka;
    }

    public String getPrzebieg() {
        return przebieg;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getData_prod() {
        return data_prod;
    }

    public String getData_przegl() {
        return data_przegl;
    }

    public String getPoj_zbior() {
        return poj_zbior;
    }

    public String getMoc() {
        return moc;
    }

    public String getPoj_sil() {
        return poj_sil;
    }

    public String getMasa() {
        return masa;
    }

    public String getNumer_rejestracji() {
        return numer_rejestracji;
    }
}


