package com.example.demo.controller;

import com.example.demo.service.SqsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    private final SqsService sqsService;

    public TransacaoController(SqsService sqsService, SqsService sqsService1){

        this.sqsService = sqsService1;
    }

    @PostMapping
    public String enviarTransacao(@RequestParam String clienteId, @RequestParam double valor){
        sqsService.enviarTransacao(clienteId, valor);
        return "Transação enviada com sucesso";
    }

}
