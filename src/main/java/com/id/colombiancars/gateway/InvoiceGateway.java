package com.id.colombiancars.gateway;

import com.id.colombiancars.entity.Invoice;
import com.id.colombiancars.entity.User;
import com.id.colombiancars.request.InvoiceRequest;

import java.util.List;

public interface InvoiceGateway {

    List<Invoice> findAllInvoices();
    Invoice findInvoiceById(Long invoiceId);
    Invoice payInvoice(InvoiceRequest invoiceRequest);
    void deleteInvoice(Long invoiceId);
}
