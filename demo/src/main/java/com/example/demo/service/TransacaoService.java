package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Transacao;
import com.example.demo.repository.TransacaoRepository;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class TransacaoService {
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public void processarTransacao(String clienteId, double valor){
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if(clienteOptional.isEmpty()){
            throw new RuntimeException("Cliente não encontrado.");
        }

        Cliente cliente = clienteOptional.get();
        double novoSaldo = cliente.getSaldo() + valor;
        cliente.setSaldo(novoSaldo);


        Transacao transacao = new Transacao();
        transacao.setClienteId(clienteId);
        transacao.setValor(valor);

        transacaoRepository.save(transacao);
        clienteRepository.save(cliente);
     }
}