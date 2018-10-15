package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactsRootResource {

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    @PostFilter("hasPermission(filterObject.id, 'com.example.demo.model.Contact', 'READ')")
    public List<Contact> list() {
        return contactService.list();
    }
}
