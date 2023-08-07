package com.example.producingwebservice;

import be.belgium.fsb.service.nrentity.messages.get.v1_00.GetNrPersonRequest;
import be.belgium.fsb.service.nrentity.messages.get.v1_00.GetNrPersonResponse;
import be.belgium.fsb.service.nrentity.messages.get.v1_00.NrPerson;
import com.example.producingwebservice.exception.ServiceFaultException;
import com.example.producingwebservice.exception.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NrPersonServiceEndpoint {
    public static final String NAMESPACE_URI = "http://fsb.belgium.be/service/nrEntity/messages/get/v1_00";

    @Autowired
    NrPersonRepository nrPersonRepository;

    public NrPersonServiceEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNrPersonRequest")
    @ResponsePayload
    public GetNrPersonResponse getNrPerson(@RequestPayload GetNrPersonRequest request) {
        GetNrPersonResponse response = new GetNrPersonResponse();
        NrPerson nrPerson = nrPersonRepository.findPersonByNiss(request.getNiss());
        if (nrPerson == null) {
            throw new ServiceFaultException("Person Not Found", new ServiceStatus("404", "NOT_FOUND"));
        }
        response.setNrPerson(nrPerson);
        return response;
    }
}
