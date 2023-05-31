package GreenBallApp.extras;

import model.Booking;

public class Reserva extends Booking {
    private String courtName;
    private String userName;
    private String paid;
    Booking booking;
    public Reserva(Booking b){
        super(b.getBookingDate(),
                b.getMadeForDay(),
                b.getFromTime(),
                b.getPaid(),
                b.getCourt(),
                b.getMember());
        courtName = b.getCourt().getName();
        userName = b.getMember().getName();
        booking = b;
        if (b.getPaid()){
            paid = "SI";
        }else{
            paid = "NO";
        }
    }
    public String getName(){
        return courtName;
    }
    public String getUserName(){
        return userName;
    }

    //return la reserva original.
    public Booking getBooking(){
        return booking;
    }


    public String getPaide() {return this.paid;}
}
