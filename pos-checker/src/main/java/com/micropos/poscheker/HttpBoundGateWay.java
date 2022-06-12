package com.micropos.poscheker;
import com.micropos.poscheker.service.CheckerService;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class HttpBoundGateWay {
    private final Queue<String> uid = new LinkedList<>();
    @Bean
    public IntegrationFlow inGate(){
        return IntegrationFlows.from(Http.inboundGateway("/check"))
                .headerFilter("accept-encoding", false)
                .channel("checkChannel")
                .get();
    }

    @Bean
    public IntegrationFlow outGate(){
        return IntegrationFlows.from("checkChannel")
                .handle(Http.outboundGateway("http://localhost:8085/api/delivery/Donald")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class))
                .get();

    }
}
