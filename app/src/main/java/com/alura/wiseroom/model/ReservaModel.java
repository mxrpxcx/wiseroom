package com.alura.wiseroom.model;

import java.io.Serializable;

public class ReservaModel implements Serializable {
    private int id;
    private SalaModel salaReservada;
    private ColaboradorModel colaboradorQueReservou;
    private DataModel dataReservada;

   
}
