package com.example.demo.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.demo.model.Cliente;
import com.example.demo.model.Transacao;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.TransacaoRepository;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class TransacaoLambda implements RequestHandler<Map<String, Object>, String>{
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;
    private final SqsClient sqsClient;
    private final String queueUrl = "https://sqs.us-east-1.amazonaws.com/123456789012/TransacoesFila.fifo";

    public TransacaoLambda(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository, SqsClient sqsClient) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
        this.sqsClient = sqsClient;
    }
    private List<Message> receberMensagens(){
        return sqsClient.receiveMessage(req ->
                req.queueUrl(queueUrl).
                        maxNumberOfMessages(10).
                        waitTimeSeconds(5)
        ).messages();
    }

    private void removerMensagemDaFila(String receiptHandle){
        sqsClient.deleteMessage(req ->
                req.queueUrl(queueUrl).
                        receiptHandle(receiptHandle)
        );
    }
    private Map<String, Object> parseJson(String json){
        return new Gson().fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
    }

    @Override
    public String handleRequest(Map<String, Object> input, Context context){
        List<Message> messages = receberMensagens();
        for(Message message : messages){
            Map<String, Object> body = parseJson(message.body());
            String clienteId = (String) body.get("cliente_id");
            double valor = (double) body.get("valor");

            processarTransacao(clienteId, valor);
            removerMensagemDaFila(message.receiptHandle());
        }
        return "Processamento concluido";

    }

    private void processarTransacao(String clienteId, double valor){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        cliente.setSaldo(cliente.getSaldo() + valor);

        Transacao transacao = new Transacao();
        transacao.setClienteId(clienteId);
        transacao.setValor(valor);

        transacaoRepository.save(transacao);
        clienteRepository.save(cliente);
    }
}
