package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "transacoes")
public class Transacao {
    @Id
    private String transacaoId;
    private String clienteId;
    private double valor;

}
