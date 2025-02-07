package com.projeto.avapay.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.avapay.Dto.CadastroPixDTO;
import com.projeto.avapay.model.CadastroPix;
import com.projeto.avapay.service.CadastroPixService;

@RestController
@RequestMapping("/api/cadastroPix") // Prefixo geral para todos os endpoints
public class CadastroPixController {

    @Autowired
    private CadastroPixService cadastroPixService;

    // Endpoint para obter todos os Cadastros de Pix
    @GetMapping("/findAll")
    public ResponseEntity<List<CadastroPixDTO>> getAllCadastroPix() {
        List<CadastroPix> cadastroPixList = cadastroPixService.listarTodos();
        List<CadastroPixDTO> cadastroPixDTOs = cadastroPixList.stream().map(cadastroPix -> {
            CadastroPixDTO dto = new CadastroPixDTO();
            dto.setPixId(cadastroPix.getPixId());
            dto.setPix(cadastroPix.getPix());
            dto.setPixType(cadastroPix.getPixType());

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(cadastroPixDTOs);
    }

    // Endpoint para criar um novo Cadastro de Pix
    @PostMapping("/createCadastroPix/{accountId}")
    public ResponseEntity<CadastroPixDTO> createCadastroPix(@RequestBody CadastroPixDTO cadastroPixDto, @PathVariable Long accountId) {
        CadastroPix cadastroPix = cadastroPixService.criarCadastroPix(cadastroPixDto, accountId);

        CadastroPixDTO responseDTO = new CadastroPixDTO();
        responseDTO.setPixId(cadastroPix.getPixId());
        responseDTO.setPix(cadastroPix.getPix());
        responseDTO.setPixType(cadastroPix.getPixType());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Endpoint para obter um Cadastro de Pix específico pelo ID
    @GetMapping("/{pixId}")
    public ResponseEntity<CadastroPixDTO> getCadastroPixById(@PathVariable Long pixId) {
        CadastroPix cadastroPix = cadastroPixService.buscarPorId(pixId);
        CadastroPixDTO dto = new CadastroPixDTO();
        dto.setPixId(cadastroPix.getPixId());
        dto.setPix(cadastroPix.getPix());
        dto.setPixType(cadastroPix.getPixType());

        return ResponseEntity.ok(dto);
    }

    // Endpoint para atualizar um Cadastro de Pix
    @PutMapping("/updateCadastroPix/{pixId}")
    public ResponseEntity<CadastroPixDTO> updateCadastroPix(@PathVariable Long pixId, @RequestBody CadastroPixDTO cadastroPixDto) {
        CadastroPix cadastroPixAtualizado = cadastroPixService.atualizarCadastroPix(pixId, cadastroPixDto);

        CadastroPixDTO responseDTO = new CadastroPixDTO();
        responseDTO.setPixId(cadastroPixAtualizado.getPixId());
        responseDTO.setPix(cadastroPixAtualizado.getPix());
        responseDTO.setPixType(cadastroPixAtualizado.getPixType());

        return ResponseEntity.ok(responseDTO);
    }

    // Endpoint para excluir um Cadastro de Pix
    @DeleteMapping("/deleteCadastroPix/{pixId}")
    public ResponseEntity<Void> deleteCadastroPix(@PathVariable Long pixId) {
        cadastroPixService.deletarCadastroPix(pixId);
        return ResponseEntity.noContent().build();
    }
}
