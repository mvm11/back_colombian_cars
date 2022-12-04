package com.id.colombiancars.service;

import com.id.colombiancars.common.NotFoundException;
import com.id.colombiancars.entity.Invoice;
import com.id.colombiancars.entity.User;
import com.id.colombiancars.gateway.InvoiceGateway;
import com.id.colombiancars.repository.InvoiceRepository;
import com.id.colombiancars.repository.UserRepository;
import com.id.colombiancars.request.InvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService implements InvoiceGateway {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Invoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findInvoiceById(Long invoiceId) {
        return getInvoice(invoiceId);
    }

    private Invoice getInvoice(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(() -> new NotFoundException("Invoice", "Id", invoiceId));
    }

    @Override
    public Invoice payInvoice(InvoiceRequest invoiceRequest) {
        User foundUser = getUserByDni(invoiceRequest.getUserDni());
        Invoice foundInvoice = getInvoiceByReference(invoiceRequest.getReference(), foundUser);
        foundInvoice.setState("Paid");
        return invoiceRepository.save(foundInvoice);
    }

    private static Invoice getInvoiceByReference(String reference, User foundUser) {
        return foundUser.getInvoices().stream().filter(invoice -> invoice.getReference().equalsIgnoreCase(reference)).findFirst().orElseThrow(() -> new NotFoundException("Invoice", "reference", reference));
    }

    private User getUserByDni(String userDni) {
        return userRepository.findAll().stream().filter(user -> user.getDni().equalsIgnoreCase(userDni)).findFirst().orElseThrow(() -> new NotFoundException("User", "dni", userDni));
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        invoiceRepository.delete(getInvoice(invoiceId));
    }
}
