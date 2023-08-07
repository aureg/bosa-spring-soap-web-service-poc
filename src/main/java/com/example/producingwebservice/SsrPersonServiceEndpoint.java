package com.example.producingwebservice;

import be.belgium.fsb.service.ssrentity.messages.get.v1_00.GetSsrPersonRequest;
import be.belgium.fsb.service.ssrentity.messages.get.v1_00.GetSsrPersonResponse;
import be.belgium.fsb.service.ssrentity.messages.get.v1_00.SsrPerson;
import com.example.producingwebservice.exception.ServiceFaultException;
import com.example.producingwebservice.exception.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SsrPersonServiceEndpoint {
    public static final String NAMESPACE_URI = "http://fsb.belgium.be/service/ssrEntity/messages/get/v1_00";

    @Autowired
    SsrPersonRepository ssrPersonRepository;

    public SsrPersonServiceEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSsrPersonRequest")
    @ResponsePayload
    public GetSsrPersonResponse getSsrPerson(@RequestPayload GetSsrPersonRequest request) {
        GetSsrPersonResponse response = new GetSsrPersonResponse();
        SsrPerson ssrPerson = ssrPersonRepository.findPersonByNiss(request.getNiss());
        if (ssrPerson == null) {
            throw new ServiceFaultException("SSR Person Not Found", new ServiceStatus("404", "NOT_FOUND"));
        }
        response.setSsrPerson(ssrPerson);
        return response;
    }
}
