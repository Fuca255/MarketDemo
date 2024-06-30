package com.example.mala_prodavnica.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            WebService webService = new WebService();
            LocateRegistry.createRegistry(1100);
            Naming.rebind("//localhost:1100/WebSevice", webService);

            System.out.println("Server je startovan na portu 1100");

        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}
