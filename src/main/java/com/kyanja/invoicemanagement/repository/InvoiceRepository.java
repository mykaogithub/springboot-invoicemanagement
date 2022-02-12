package com.kyanja.invoicemanagement.repository;


import com.kyanja.invoicemanagement.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
