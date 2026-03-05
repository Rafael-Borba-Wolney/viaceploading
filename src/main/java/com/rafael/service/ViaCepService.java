package com.rafael.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.model.Endereco;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepService {

    private static final String URL_VIACEP = "https://viacep.com.br/ws/%s/json/";

    public Endereco buscarCep(String cep) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String url = String.format(URL_VIACEP, cep);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 400) {
            throw new IllegalArgumentException("CEP inválido para consulta.");
        }

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao consultar a API. Status HTTP: " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Endereco endereco = objectMapper.readValue(response.body(), Endereco.class);

        if (Boolean.TRUE.equals(endereco.getErro())) {
            throw new IllegalArgumentException("CEP não encontrado.");
        }

        return endereco;
    }
}