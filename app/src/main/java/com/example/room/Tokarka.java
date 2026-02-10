package com.example.room;

public class Tokarka {
    private int id;
    private String marka;
    private String model;
    private int srednicaToczenia;
    private int mocSilnika;

    public Tokarka(String marka, String model, int srednicaToczenia, int mocSilnika) {
        this.id = 0;
        this.marka = marka;
        this.model = model;
        this.srednicaToczenia = srednicaToczenia;
        this.mocSilnika = mocSilnika;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSrednicaToczenia() {
        return srednicaToczenia;
    }

    public void setSrednicaToczenia(int srednicaToczenia) {
        this.srednicaToczenia = srednicaToczenia;
    }

    public int getMocSilnika() {
        return mocSilnika;
    }

    public void setMocSilnika(int mocSilnika) {
        this.mocSilnika = mocSilnika;
    }

    @Override
    public String toString() {
        return "Tokarka{" +
                "id=" + id +
                ", marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", srednicaToczenia=" + srednicaToczenia +
                ", mocSilnika=" + mocSilnika +
                '}';
    }
}
