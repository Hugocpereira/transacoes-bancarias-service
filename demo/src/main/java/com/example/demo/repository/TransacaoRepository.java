package com.example.demo.repository;

import com.example.demo.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository <Transacao, String>{

}
