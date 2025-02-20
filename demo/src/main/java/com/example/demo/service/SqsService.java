package com.example.demo.service;


import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


import java.util.UUID;

@Service
public class SqsService {
    private final SqsClient sqsClient;
    private final String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/TransacoesFila.fifo";

    public SqsService(SqsClient sqsClient){
        this.sqsClient = sqsClient;
    }

    public void enviarTransacao(String clienteId, double valor){
        String transacaoId = UUID.randomUUID().toString();

        String mensagem = String.format("{\"transacao_id\": \"%s\", \"cliente_id\": \"%s\", \"valor\": %f}",
                transacaoId, clienteId, valor);

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(mensagem)
                .messageGroupId(clienteId)
                .messageDeduplicationId(transacaoId)
                .build();
        sqsClient.sendMessage(sendMessageRequest);
    }
}
