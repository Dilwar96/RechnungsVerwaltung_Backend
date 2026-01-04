package com.invoiceprocessing.server.dao;

import com.invoiceprocessing.server.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
