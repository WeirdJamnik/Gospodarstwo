package com.example.gospodarstwo;

public class GetOlej {

    public GetOlej(String id, String marka, String model, String nazwa_oleju, String data, String ilosc, String przebieg, boolean isVisible) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.nazwa_oleju = nazwa_oleju;
        this.data = data;
        this.ilosc = ilosc;
        this.przebieg = przebieg;
        this.isVisible = isVisible;
    }

    public String getId() {
        return id;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getNazwa_oleju() {
        return nazwa_oleju;
    }

    public String getData() {
        return data;
    }

    public String getIlosc() {
        return ilosc;
    }

    public String getPrzebieg() {
        return przebieg;
    }

    public boolean isVisible() {
        return isVisible;
    }

    String id, marka, model, nazwa_oleju, data, ilosc, przebieg;

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    boolean isVisible;
}
