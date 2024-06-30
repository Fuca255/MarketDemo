package com.example.mala_prodavnica.services;

import com.example.mala_prodavnica.beans.CreateReceiptBean;
import com.example.mala_prodavnica.beans.ReceiptBean;
import com.example.mala_prodavnica.beans.ReceiptItemBean;
import com.example.mala_prodavnica.interfaces.IProductRepository;
import com.example.mala_prodavnica.interfaces.IReceiptRepository;
import com.example.mala_prodavnica.interfaces.IUserRepository;
import com.example.mala_prodavnica.model.Product;
import com.example.mala_prodavnica.model.Receipt;
import com.example.mala_prodavnica.model.ReceiptItem;
import com.example.mala_prodavnica.model.User;
import com.example.mala_prodavnica.repository.ProductRepository;
import com.example.mala_prodavnica.repository.ReceiptRepository;
import com.example.mala_prodavnica.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceiptService {
    private IUserRepository userRepository;
    private IReceiptRepository receiptRepository;
    private IProductRepository productRepository;
    public ReceiptService()
    {
        receiptRepository = new ReceiptRepository();
        userRepository = new UserRepository();
        productRepository = new ProductRepository();
    }

    public List<ReceiptBean> getAllReceiptsByUser(int user_id)
    {
        List<ReceiptBean> receipts = new ArrayList<>();
        List<Receipt> res = receiptRepository.getAllUserReceipts(user_id);

        for (Receipt r : res)
        {
            ReceiptBean rb = new ReceiptBean();
            rb.setId(r.getId());
            rb.setTotal(r.getTotal());
            rb.setPurchaseDate(r.getPurchaseDate());
            List<ReceiptItemBean> items = new ArrayList<>();
            for (ReceiptItem ri: r.getReceiptItems())
            {
                ReceiptItemBean rib = new ReceiptItemBean();
                rib.setProduct_id(ri.getProduct().getId());
                rib.setName(ri.getProduct().getName());
                rib.setQuantity(ri.getQuantity());
                items.add(rib);
            }
            rb.setItems(items);
            receipts.add(rb);
        }

        return receipts;
    }
    public Receipt createReceipt(CreateReceiptBean create)
    {
        Optional<User> u = userRepository.findById(create.getUserId());
        if(u.isPresent())
        {
            List<ReceiptItem> items = new ArrayList<>();
            for (ReceiptItemBean b : create.getItems())
            {
                ReceiptItem item = new ReceiptItem();
                item.setQuantity(b.getQuantity());
                Optional<Product> p = productRepository.getProductById(b.getProduct_id());
                if(p.isEmpty())
                    return null;
                item.setProduct(p.get());
                items.add(item);
            }
//            System.out.println("Napravio je objekte");
            return receiptRepository.createReceipt(u.get(),create.getTotal(),create.getPurchaseDate(),items);
        }
        return null;
    }
    public void deleteReceipt(int rec_id)
    {
        Optional<Receipt> rec = receiptRepository.getReceiptById(rec_id);
        if(rec.isPresent())
            receiptRepository.deleteReceipt(rec.get());
    }

}
