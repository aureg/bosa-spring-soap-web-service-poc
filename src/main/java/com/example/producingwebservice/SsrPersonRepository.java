package com.example.producingwebservice;

import be.belgium.fsb.service.ssrentity.messages.get.v1_00.Address;
import be.belgium.fsb.service.ssrentity.messages.get.v1_00.SsrPerson;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SsrPersonRepository {
    private static final Map<String, SsrPerson> ssrPersons = new HashMap<>();

    @PostConstruct
    public void initData() throws DatatypeConfigurationException, ParseException {

        Address address1 = new Address();
        address1.setStreet("Rue de la box");
        address1.setNumber("7a");
        address1.setPostalcode(new BigInteger("4800"));
        address1.setCity("Verviers");
        address1.setCountry("BE");

        SsrPerson ssrPerson1 = new SsrPerson();
        ssrPerson1.setFirstname("Mike");
        ssrPerson1.setLastname("Tyson");
        ssrPerson1.setNiss("66063000002");
        ssrPerson1.setAddress(address1);
        ssrPersons.put(ssrPerson1.getNiss(), ssrPerson1);


        Address address2 = new Address();
        address2.setStreet("Rue de la box");
        address2.setNumber("7b");
        address2.setPostalcode(new BigInteger("4800"));
        address2.setCity("Verviers");
        address2.setCountry("BE");
        SsrPerson ssrPerson2 = new SsrPerson();
        ssrPerson2.setFirstname("Mohamed");
        ssrPerson2.setLastname("Ali");
        ssrPerson2.setNiss("42011700002");
        ssrPerson2.setAddress(address2);
        ssrPersons.put(ssrPerson2.getNiss(), ssrPerson2);

        SsrPerson ssrPerson3 = new SsrPerson();
        ssrPerson3.setFirstname("John");
        ssrPerson3.setLastname("Cena");
        ssrPerson3.setNiss("77042300102");
        ssrPersons.put(ssrPerson3.getNiss(), ssrPerson3);

    }


    public SsrPerson findPersonByNiss(String niss) {
        Assert.notNull(niss, "The person's niss must not be null");
        Optional<String> key = ssrPersons.keySet().stream().filter(s -> {
            if (s.equals(niss)) return true;
            return false;
        }).findFirst();

        if (key.isEmpty()) return null;
        return ssrPersons.get(key.get());
    }

    public List<SsrPerson> findPersonsName(String name) {
        Assert.notNull(name, "The person's name must not be null");
        List<SsrPerson> personList = ssrPersons.values().stream().filter(ssrPerson -> {
            return ssrPerson.getLastname().equalsIgnoreCase(name);
        }).toList();

        if (personList.isEmpty()) return null;
        return personList;
    }

    public List<SsrPerson> findPersonsFirstname(String firstname) {
        Assert.notNull(firstname, "The person's name must not be null");
        List<SsrPerson> personList = ssrPersons.values().stream().filter(ssrPerson -> {
            return ssrPerson.getFirstname().equalsIgnoreCase(firstname);
        }).toList();

        if (personList.isEmpty()) return null;
        return personList;
    }

    private XMLGregorianCalendar getXMLDateFromString(String dateInStr) throws DatatypeConfigurationException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateInStr);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);

        return xmlGregCal;
    }
}
