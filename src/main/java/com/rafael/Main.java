package com.rafael;

import com.rafael.model.Endereco;
import com.rafael.service.ViaCepService;
import com.rafael.util.CepUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ViaCepService viaCepService = new ViaCepService();

        System.out.println("=== CONSULTA DE CEP ===");
        System.out.print("Digite o CEP: ");

        String cepInformado = scanner.nextLine();
        String cepLimpo = CepUtils.limparCep(cepInformado);

        if (!CepUtils.cepValido(cepInformado)) {
            System.out.println("CEP inválido. Digite um CEP com exatamente 8 números.");
            scanner.close();
            return;
        }

        AtomicBoolean carregando = new AtomicBoolean(true);
        AtomicReference<Endereco> enderecoRef = new AtomicReference<>();
        AtomicReference<String> erroRef = new AtomicReference<>();

        Thread threadAnimacao = new Thread(() -> {
            String[] frames = {"|", "/", "-", "\\"};
            int i = 0;

            while (carregando.get()) {
                System.out.print("\rBuscando CEP " + frames[i % frames.length]);
                i++;

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });

        Thread threadBusca = new Thread(() -> {
            try {
                Endereco endereco = viaCepService.buscarCep(cepLimpo);
                enderecoRef.set(endereco);

            } catch (IllegalArgumentException e) {
                erroRef.set("Erro: " + e.getMessage());

            } catch (IOException e) {
                erroRef.set("Erro de comunicação com a API.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                erroRef.set("A execução foi interrompida.");

            } catch (Exception e) {
                erroRef.set("Erro inesperado: " + e.getMessage());

            } finally {
                carregando.set(false);
            }
        });

        threadAnimacao.start();
        threadBusca.start();

        try {
            threadBusca.join();
            threadAnimacao.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\nA execução principal foi interrompida.");
            scanner.close();
            return;
        }

        System.out.print("\r                              \r");

        if (erroRef.get() != null) {
            System.out.println(erroRef.get());
        } else {
            Endereco endereco = enderecoRef.get();
            System.out.println("Consulta finalizada com sucesso!");
            System.out.println("Logradouro: " + endereco.getLogradouro());
            System.out.println("Bairro: " + endereco.getBairro());
            System.out.println("Cidade: " + endereco.getLocalidade());
            System.out.println("UF: " + endereco.getUf());
        }

        scanner.close();
    }
}