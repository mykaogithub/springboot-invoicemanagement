package com.kyanja.invoicemanagement.controller;


import com.kyanja.invoicemanagement.exception.InvoiceNotFoundException;
import com.kyanja.invoicemanagement.model.Invoice;
import com.kyanja.invoicemanagement.service.IInvoiceService;
import com.kyanja.invoicemanagement.utils.InvoiceDataExcelExport;
import com.kyanja.invoicemanagement.utils.InvoiceDataPdfExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/invoice")
public class InvoiceController   {



    @Autowired
    private IInvoiceService service;






    @GetMapping("/")
    public String showHomePage() {
        return "homePage";
    }

    @GetMapping("/register")
    public String showRegistration() {
        return "registerInvoicePage";
    }

    @PostMapping("/save")
    public String saveInvoice(
            @ModelAttribute Invoice invoice,
            Model model
    ) {
        service.saveInvoice(invoice);
        Long id = service.saveInvoice(invoice).getId();
        String message = "Record with id : '"+id+"' is saved successfully !";
        model.addAttribute("message", message);
        return "registerInvoicePage";
    }

    @GetMapping("/getAllInvoices")
    public String getAllInvoices(
            @RequestParam(value = "message", required = false) String message,
            Model model
    ) {
        List<Invoice> invoices= service.getAllInvoices();
        model.addAttribute("list", invoices);
        model.addAttribute("message", message);
        return "allInvoicesPage";
    }

    @GetMapping("/edit")
    public String getEditPage(
            Model model,
            RedirectAttributes attributes,
            @RequestParam Long id
    ) {
        String page = null;
        try {
            Invoice invoice = service.getInvoiceById(id);
            model.addAttribute("invoice", invoice);
            page="editInvoicePage";
        } catch (InvoiceNotFoundException e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
            page="redirect:getAllInvoices";
        }
        return page;
    }

    @PostMapping("/update")
    public String updateInvoice(
            @ModelAttribute Invoice invoice,
            RedirectAttributes attributes
    ) {
        service.updateInvoice(invoice);
        Long id = invoice.getId();
        attributes.addAttribute("message", "Invoice with id: '"+id+"' is updated successfully !");
        return "redirect:getAllInvoices";
    }

    @GetMapping("/delete")
    public String deleteInvoice(
            @RequestParam Long id,
            RedirectAttributes attributes
    ) {
        try {
            service.deleteInvoiceById(id);
            attributes.addAttribute("message", "Invoice with Id : '"+id+"' is removed successfully!");
        } catch (InvoiceNotFoundException e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:getAllInvoices";
    }

    /***
     * Export data to excel file
     */
    @GetMapping("/excelExport")
    public ModelAndView exportToExcel() {
        ModelAndView mav = new ModelAndView();
        mav.setView(new InvoiceDataExcelExport());
        //read data from DB
        List<Invoice> list= service.getAllInvoices();
        //send to excelImpl class
        mav.addObject("list", list);
        return mav;
    }

    /***
     * Export data to pdf file
     */
    @GetMapping("/pdf")
    public ModelAndView exportToPdf() {
        ModelAndView mav = new ModelAndView();
        mav.setView(new InvoiceDataPdfExport());
        //read data from DB
        List<Invoice> list= service.getAllInvoices();
        //send to pdfImpl class
        mav.addObject("list", list);
        return mav;
    }
}