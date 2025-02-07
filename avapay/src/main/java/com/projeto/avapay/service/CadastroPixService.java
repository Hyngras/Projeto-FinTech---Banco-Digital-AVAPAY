package com.projeto.avapay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.avapay.Dto.CadastroPixDTO;
import com.projeto.avapay.model.Account;
import com.projeto.avapay.model.CadastroPix;
import com.projeto.avapay.repository.AccountRepository;
import com.projeto.avapay.repository.CadastroPixRepository;

@Service
public class CadastroPixService {

    @Autowired
    private CadastroPixRepository cadastroPixRepository;

    @Autowired
    private AccountRepository accountRepository;

    // Listar todos os Cadastros de Pix
    public List<CadastroPix> listarTodos() {
        return cadastroPixRepository.findAll();
    }

    // Buscar Cadastro de Pix por ID
    public CadastroPix buscarPorId(Long id) {
        return cadastroPixRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CadastroPix não encontrado com o ID: " + id));
    }

    // Criar um novo Cadastro de Pix
    public CadastroPix criarCadastroPix(CadastroPixDTO cadastroPixDto, Long accountId) {
        // Verificar se a conta associada existe
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada com o ID: " + accountId));

        // Criar o CadastroPix e associar à conta
        CadastroPix cadastroPix = new CadastroPix();
        cadastroPix.setPix(cadastroPixDto.getPix());
        cadastroPix.setPixType(cadastroPixDto.getPixType());
        cadastroPix.setAccount(account);  // Associar a conta ao cadastro Pix

        return cadastroPixRepository.save(cadastroPix);
    }

    // Atualizar um Cadastro de Pix
    public CadastroPix atualizarCadastroPix(Long id, CadastroPixDTO cadastroPixDto) {
        CadastroPix cadastroPixExistente = cadastroPixRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CadastroPix não encontrado com o ID: " + id));

        cadastroPixExistente.setPix(cadastroPixDto.getPix());
        cadastroPixExistente.setPixType(cadastroPixDto.getPixType());

        return cadastroPixRepository.save(cadastroPixExistente);
    }

    // Deletar um Cadastro de Pix
    public void deletarCadastroPix(Long id) {
        cadastroPixRepository.deleteById(id);
    }
}
