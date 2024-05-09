import java.util.ArrayList;
import java.util.List;

public class Booked {
    int tickets,pnr;
    List<String> seatNo = new ArrayList<>();
    char src,dest;

    public Booked() {
    }

    Booked(int pnr, int tickets, List<String> seatNo, char src, char dest){
        this.pnr = pnr;
        this.tickets = tickets;
        this.seatNo = seatNo;
        this.src = src;
        this.dest = dest;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getPnr() {
        return pnr;
    }

    public void setPnr(int pnr) {
        this.pnr = pnr;
    }

    public List<String> getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(List<String> seatNo) {
        this.seatNo = seatNo;
    }

    public char getSrc() {
        return src;
    }

    public void setSrc(char src) {
        this.src = src;
    }

    public char getDest() {
        return dest;
    }

    public void setDest(char dest) {
        this.dest = dest;
    }

    public String print(){
        String summary = "PNR : " + this.pnr + "\nTickets : " + this.tickets + "\nSeatNo : " + this.seatNo + "\nSRC : " + this.src
                + "\nDest : " + this.dest;
        return summary;
    }
}
