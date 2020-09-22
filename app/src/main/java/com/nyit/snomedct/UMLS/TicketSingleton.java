package com.nyit.snomedct.UMLS;

public class TicketSingleton {

    String Ticket;
    private static final TicketSingleton ourInstance= new TicketSingleton();
    public static TicketSingleton getInstance(){
        return ourInstance;
    }
    private TicketSingleton() {}
    public void setTicket(String TGT){
        this.Ticket=TGT;
    }
    public String getTicket(){
        return Ticket;
    }

}
