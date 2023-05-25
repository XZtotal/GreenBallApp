package GreenBallApp.extras;

import model.Booking;

public class Reserva extends Booking {
    private String courtName;
    public Reserva(Booking b){
        super(b.getBookingDate(),
                b.getMadeForDay(),
                b.getFromTime(),
                b.getPaid(),
                b.getCourt(),
                b.getMember());
        courtName = b.getCourt().getName();
    }
    public String getName(){
        return courtName;
    }
}
