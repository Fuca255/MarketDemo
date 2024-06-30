package com.example.mala_prodavnica.rmi;

import com.example.mala_prodavnica.beans.ReceiptBean;
import com.example.mala_prodavnica.beans.ReceiptItemBean;
import com.example.mala_prodavnica.model.Receipt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class WebService extends UnicastRemoteObject implements ICalculation {
    protected WebService() throws RemoteException {
    }

    @Override
    public int calculateTotalSumOfReceiptTotals(List<ReceiptBean> receipts) throws RemoteException {
        int total = 0;
        for (ReceiptBean receipt: receipts)
            total+= receipt.getTotal();
        return total;
    }
}
