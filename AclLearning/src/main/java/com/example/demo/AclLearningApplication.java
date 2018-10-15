package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.model.AclClass;
import com.example.demo.model.AclEntry;
import com.example.demo.model.AclObjectIdentity;
import com.example.demo.model.AclSid;
import com.example.demo.model.Contact;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AclClassRepository;
import com.example.demo.repository.AclEntryRepository;
import com.example.demo.repository.AclObjectIdentityRepository;
import com.example.demo.repository.AclSidRepository;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class AclLearningApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AclLearningApplication.class, args);
        AclSidRepository aclSidRepo = ctx.getBean(AclSidRepository.class);
        RoleRepository roleRepo = ctx.getBean(RoleRepository.class);
        UserRepository userRepo = ctx.getBean(UserRepository.class);
        AclClassRepository aclClassRepo = ctx.getBean(AclClassRepository.class);
        AclObjectIdentityRepository aclObjectIdentityRepo = ctx.getBean(AclObjectIdentityRepository.class);
        ContactRepository contactRepo = ctx.getBean(ContactRepository.class);
        AclEntryRepository aclEntryRepo = ctx.getBean(AclEntryRepository.class);

        Role roleAdmin = roleRepo.save(new Role("ROLE_ADMIN", "Administrator"));
        Role roleSecond = roleRepo.save(new Role("ROLE_SECOND", "Administrator"));
        Contact contact1 = contactRepo.save(new Contact("contact-1"));
        Contact contact2 = contactRepo.save(new Contact("contact-2"));
        Contact contact3 = contactRepo.save(new Contact("contact-3"));
        Contact contact4 = contactRepo.save(new Contact("contact-4"));

        Set<Role> rolesUserAdmin = new HashSet<>();
        rolesUserAdmin.add(roleAdmin);
        Set<Role> rolesUserSecond = new HashSet<>();
        rolesUserSecond.add(roleSecond);

        User userAdmin = userRepo.save(new User("userAdmin", "passwordAdmin", rolesUserAdmin));
        User userSecond = userRepo.save(new User("userSecond", "passwordSecond", rolesUserSecond));

        AclSid aclSid1 = aclSidRepo.save(new AclSid(true, userAdmin.getUsername()));
        AclSid aclSid2 = aclSidRepo.save(new AclSid(true, userSecond.getUsername()));
        AclSid aclSid3False = aclSidRepo.save(new AclSid(false, "ROLE_ADMIN"));

        AclClass aclClass1 = aclClassRepo.save(new AclClass("com.example.demo.model.Contact"));

        AclObjectIdentity aclObjectIdentity1 = aclObjectIdentityRepo.save(
                new AclObjectIdentity(
                        aclClass1,
                        contact1.getId(),
                        null,
                        aclSid1,
                        false));

        AclObjectIdentity aclObjectIdentity2 = aclObjectIdentityRepo.save(
                new AclObjectIdentity(
                        aclClass1,
                        contact2.getId(),
                        null,
                        aclSid2,
                        false));

        AclEntry aclEntry1 = aclEntryRepo.save(
                new AclEntry(
                        aclObjectIdentity1,
                        1,
                        aclSid1,
                        1,
                        true,
                        true,
                        true));

        AclEntry aclEntry2 = aclEntryRepo.save(
                new AclEntry(
                        aclObjectIdentity1,
                        2,
                        aclSid2,
                        1,
                        true,
                        true,
                        true));

    }
}
