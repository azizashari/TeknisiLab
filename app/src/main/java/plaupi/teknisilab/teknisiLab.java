package plaupi.teknisilab;

/**
 * Created by Aziz on 16/04/2017.
 */

public class teknisiLab {

    private String nama_teknisi;

    public teknisiLab(){

    }

    teknisiLab(String nama_teknisi){
        this.nama_teknisi = nama_teknisi;
    }

    public String getNamaTeknisi(){
        return nama_teknisi;
    }

    public void setNamaTeknisi(String nama_teknisi){
        this.nama_teknisi = nama_teknisi;

    }
}

