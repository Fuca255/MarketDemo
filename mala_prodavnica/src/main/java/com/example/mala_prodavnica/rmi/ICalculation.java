package com.example.mala_prodavnica.rmi;

import com.example.mala_prodavnica.beans.ReceiptBean;
import com.example.mala_prodavnica.beans.ReceiptItemBean;
import com.example.mala_prodavnica.model.Receipt;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ICalculation extends Remote {
    int calculateTotalSumOfReceiptTotals(List<ReceiptBean> totals) throws RemoteException;
}
