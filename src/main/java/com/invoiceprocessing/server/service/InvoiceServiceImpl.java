package com.invoiceprocessing.server.service;

import com.invoiceprocessing.server.dao.InvoiceRepository;
import com.invoiceprocessing.server.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice addInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice deleteInvoice(long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        invoiceRepository.delete(invoice);
        return invoice;
    }
}
