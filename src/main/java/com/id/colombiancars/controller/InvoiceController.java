package com.id.colombiancars.controller;


import com.id.colombiancars.entity.Invoice;
import com.id.colombiancars.entity.User;
import com.id.colombiancars.request.InvoiceRequest;
import com.id.colombiancars.request.UserRequest;
import com.id.colombiancars.service.InvoiceService;
import com.id.colombiancars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Find by id
    @GetMapping(value = "/findById/{invoiceId}")
    public ResponseEntity<Invoice> findInvoice (@PathVariable Long invoiceId) {
        return new ResponseEntity<>(invoiceService.findInvoiceById(invoiceId), HttpStatus.OK);

    }


    // Find All
    @GetMapping(value = "/findAll")
    public List<Invoice> findAllInvoices(){
        return invoiceService.findAllInvoices();
    }


    // Pay
    @PutMapping(value = "/pay")
    public ResponseEntity<Invoice> pay (@RequestBody InvoiceRequest invoiceRequest) {
        return new ResponseEntity<>(invoiceService.payInvoice(invoiceRequest), HttpStatus.OK);

    }

    // Delete
    @DeleteMapping(value = "/delete/{invoiceId}")
    public ResponseEntity<String> delete (@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return new ResponseEntity<>("The invoice has been deleted successful", HttpStatus.OK);
    }

}
