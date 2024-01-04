package com.example.gospodarstwo;

public class GetCzesc {

    public String getId() {
        return id;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getNazwa_czesci() {
        return nazwa_czesci;
    }

    public String getData_wym() {
        return data_wym;
    }

    public String getUwagi() {
        return uwagi;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public GetCzesc(String id, String marka, String model, String nazwa_czesci, String data_wym, String uwagi, boolean isVisible) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.nazwa_czesci = nazwa_czesci;
        this.data_wym = data_wym;
        this.uwagi = uwagi;
        this.isVisible = isVisible;
    }

    String id, marka, model, nazwa_czesci, data_wym, uwagi;
    boolean isVisible;


    public void setVisible(boolean visible) {
        isVisible = visible;
    }



}
