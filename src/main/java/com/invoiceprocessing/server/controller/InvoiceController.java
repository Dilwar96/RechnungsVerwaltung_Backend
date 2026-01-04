package com.invoiceprocessing.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceprocessing.server.model.Invoice;
import com.invoiceprocessing.server.service.InvoiceService;

@RestController
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/invoices")
    public Invoice addInvoice (@RequestBody Invoice invoice) {
        return invoiceService.addInvoice(invoice);
    }

    @GetMapping("/invoices")
    public List<Invoice> getInvoices() {
        return invoiceService.getInvoices();
    }

    @DeleteMapping("invoices/{invoiceId}")
    public Invoice deleteInvoice(@PathVariable String invoiceId) {
        return invoiceService.deleteInvoice(Long.parseLong(invoiceId));
    }
}
