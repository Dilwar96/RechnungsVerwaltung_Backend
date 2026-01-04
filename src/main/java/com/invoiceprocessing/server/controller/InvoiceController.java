package com.invoiceprocessing.server.controller;

import com.invoiceprocessing.server.model.Invoice;
import com.invoiceprocessing.server.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
