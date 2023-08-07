package com.example.producingwebservice;

import be.belgium.fsb.service.nrentity.messages.get.v1_00.Gender;
import be.belgium.fsb.service.nrentity.messages.get.v1_00.NrPerson;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class NrPersonRepository {
    private static final Map<String, NrPerson> nrPersons = new HashMap<>();

    @PostConstruct
    public void initData() throws DatatypeConfigurationException, ParseException {
        NrPerson nrPerson1 = new NrPerson();
        nrPerson1.setFirstname("Aurélien");
        nrPerson1.setLastname("Geromboux");
        nrPerson1.setBirthdate(getXMLDateFromString("1988-12-28"));
        nrPerson1.setGender(Gender.MALE);
        nrPerson1.setNiss("88122800002");
        nrPersons.put(nrPerson1.getNiss(), nrPerson1);

        NrPerson nrPerson2 = new NrPerson();
        nrPerson2.setFirstname("Michel");
        nrPerson2.setLastname("Mich");
        nrPerson2.setBirthdate(getXMLDateFromString("2023-02-14"));
        nrPerson2.setGender(Gender.MALE);
        nrPerson2.setNiss("23021400002");
        nrPersons.put(nrPerson2.getNiss(), nrPerson2);

        NrPerson nrPerson3 = new NrPerson();
        nrPerson3.setFirstname("Jean-Michel");
        nrPerson3.setLastname("Mich");
        nrPerson3.setBirthdate(getXMLDateFromString("2023-02-14"));
        nrPerson3.setGender(Gender.MALE);
        nrPerson3.setNiss("23021400102");
        nrPersons.put(nrPerson3.getNiss(), nrPerson3);


    }


    public NrPerson findPersonByNiss(String niss) {
        Assert.notNull(niss, "The person's niss must not be null");
        Optional<String> key = nrPersons.keySet().stream().filter(s -> {
            if (s.equals(niss)) return true;
            return false;
        }).findFirst();

        if (key.isEmpty()) return null;
        return nrPersons.get(key.get());
    }

    public List<NrPerson> findPersonsName(String name) {
        Assert.notNull(name, "The person's name must not be null");
        List<NrPerson> personList = nrPersons.values().stream().filter(nrPerson -> {
            return nrPerson.getLastname().equalsIgnoreCase(name);
        }).toList();

        if (personList.isEmpty()) return null;
        return personList;
    }

    public List<NrPerson> findPersonsFirstname(String firstname) {
        Assert.notNull(firstname, "The person's name must not be null");
        List<NrPerson> personList = nrPersons.values().stream().filter(nrPerson -> {
            return nrPerson.getFirstname().equalsIgnoreCase(firstname);
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
