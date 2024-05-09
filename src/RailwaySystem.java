import java.util.*;

public class RailwaySystem {
    static Scanner scanner = new Scanner(System.in);
    static int pnr;
    static HashMap<Integer,List<SrcDest>> seats;
    static HashMap<Integer, Booked> booked;
    static List<Integer> waitingList;
    static List<String> log;
    RailwaySystem(){
        pnr = 1;
        seats = new HashMap<>();
        booked = new HashMap<>();
        waitingList = new ArrayList<>();
        log = new ArrayList<>();
        for(int i=1; i<=8; i++){
            List<SrcDest> li = new ArrayList<>();
            li.add(new SrcDest('0','0'));
            seats.put(i,li);
        }
//        List<SrcDest> li = new ArrayList<>();
//        li.add(new SrcDest('0','0'));
//        List<SrcDest> li2 = new ArrayList<>();
//        li.add(new SrcDest('0','0'));
//        seats.put(9,li);
//        seats.put(10,li2);
    }
    static String book(int tickets,char src,char dest,List<String> bookedSeats){
        int tempTic = tickets;
        for(int i=1; i<=8 && tempTic > 0; i++){
            if(seats.get(i).size() < 4){
                List<SrcDest> li = seats.get(i);
                if(li.get(li.size()-1).dest <= src) tempTic--;
            }
        }
        if(tempTic > 0){
            tempTic = 2-waitingList.size()-tempTic;
        }
        if(tempTic < 0) {
            System.out.println("Insufficient ticket");
            return "failed";
        }
        tempTic = tickets;
        for(int i=1; i<=8 && tempTic>0; i++){
            if(seats.get(i).size() <= 4){
                List<SrcDest> li = seats.get(i);
                if(li.get(li.size()-1).dest <= src){
                    tempTic--;
                    bookedSeats.add(i+"");
                    //List<SrcDest> liT = new ArrayList<>();
                    li.add(new SrcDest(src,dest));
                    seats.put(i,li);
                }
            }
        }
        int wl = waitingList.size()+1;
        while(tempTic > 0){
            bookedSeats.add("WL"+wl);
            waitingList.add(pnr);
            wl++;
            tempTic--;
        }
        for(Integer i : seats.keySet()){
            List<SrcDest> temp = seats.get(i);
            System.out.print(i + " : ");
            for(SrcDest j : temp) System.out.print(j.src + "#" + j.dest + " ");
            System.out.println();
        }
        System.out.println(waitingList);
        Booked booked1 = new Booked(pnr,tickets,bookedSeats,src,dest);
//        booked1.setPnr(pnr);
//        booked1.setSrc(src);
//        booked1.setDest(dest);
//        booked1.setSeatNo(bookedSeats);
//        booked1.setTickets(tickets);
        booked.put(pnr,booked1);
        System.out.println(booked1.print());
        String log1 = "PNR : " + pnr + " - Seats Booked : " + booked1.seatNo;
        log.add(log1);
        pnr++;
        return "booked";
    }
    static void cancel(){
        System.out.println("Enter pnr number");
        int pnrNo  = scanner.nextInt();
        int tickets = scanner.nextInt();
        int tempTick = tickets;
        if(!booked.containsKey(pnrNo)){
            System.out.println("No PNR found");
            return;
        }
        if(booked.get(pnrNo).seatNo.size() < tickets){
            System.out.println("Tickets booked for PNR is : " + booked.get(pnrNo).seatNo.size());
            return;
        }
        Booked pnrBooked = booked.get(pnrNo);
        List<String> pnrSeats = pnrBooked.seatNo;
        List<String> cancelled = new ArrayList<>();
        int p = 0;
        for(int i=pnrSeats.size()-1; i>=0 && tickets > 0; i--,tickets--){
            if(pnrSeats.get(i).charAt(0) == 'W'){
                int j = 0;
                while (waitingList.get(j) != pnrNo){
                    j++;
                }
                waitingList.remove(j);
            }
            else{
                List<SrcDest> srcDest = seats.get(Integer.parseInt(pnrSeats.get(i)));
                for(int k=0; k<srcDest.size(); k++){
                    SrcDest j = srcDest.get(k);
                    if(j.src == pnrBooked.src && j.dest == pnrBooked.dest){
                        srcDest.remove(k);
                        break;
                    }
                }
            }
            p++;
            cancelled.add(pnrSeats.get(i));
        }
        System.out.println("Seats Cancelled : " + cancelled);
        while (p>0){
            pnrSeats.remove(0);
            p--;
        }
        pnrBooked.setTickets(pnrBooked.seatNo.size());
        log.add("PNR : " + pnrNo + " - Seats Cancelled : " + cancelled);
        //Move waiting list to confirmed
        confirmTickets();
    }
    static void confirmTickets(){
        int j = 0;
        while(j<waitingList.size()){
            Booked booked1 = booked.get(waitingList.get(j));
            for(int i=1; i<=8; i++){
                boolean seatConfirmed = false;
                if(seats.get(i).size() < 4){
                    List<SrcDest> li = seats.get(i);
                    if(li.get(li.size()-1).dest <= booked1.src){
                        List<String> bookedSeats = booked1.seatNo;
                        for (int k=0; k<bookedSeats.size(); k++){
                            String st = bookedSeats.get(k);
                            if(st.charAt(0) == 'W'){
                                bookedSeats.remove(k);
                                bookedSeats.add(i+"");
                                waitingList.remove(j);
                                seatConfirmed = true;
                                li.add(new SrcDest(booked1.src,booked1.dest));
                                j--;
                                break;
                            }
                        }
                    }
                }
                if(seatConfirmed) break;
            }
            j++;
        }
    }
    static void printSummary(){
        System.out.println(log);
    }
    static void ticketSummary(){
        System.out.println("Enter PNR number");
        int ticket = scanner.nextInt();
        System.out.println(booked.get(ticket).print());
    }
    void start(){
        System.out.println("1.Book \n2.Cancel \n3.Print \n4.Ticket Summary");
        int option = scanner.nextInt();
        while(option < 5){
            if(option == 1){
                System.out.println("Enter tickets");
                int tickets = scanner.nextInt();
                System.out.println("Enter source");
                String src = scanner.next();
                System.out.println("Enter destination");
                String dest = scanner.next();
                book(tickets,src.charAt(0),dest.charAt(0),new ArrayList<>());
            }
            else if (option == 2) {
                cancel();
            } else if(option == 3){
                printSummary();
            }
            else{
                ticketSummary();
            }
            System.out.println("1.Book \n2.Cancel \n3.Print \n4.Ticket Summary");
            option = scanner.nextInt();
        }
    }
}
