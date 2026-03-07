package com.literalura.principal;

import com.literalura.model.DadosResultados;
import com.literalura.service.ConsumoApi;
import com.literalura.util.ConverteDados;

public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/";

    public void exibeMenu() {
        System.out.println("Iniciando a requisição para a API Gutendex...");

        var json = consumoApi.obterDados(ENDERECO);

        DadosResultados dados = conversor.obterDados(json, DadosResultados.class);

        System.out.println("\n--- ALGUNS LIVROS ENCONTRADOS ---");
        dados.resultados().forEach(livro -> {
            System.out.println("Título: " + livro.titulo());
            if (!livro.autores().isEmpty()) {
                System.out.println("Autor: " + livro.autores().get(0).nome());
            }
            System.out.println("Idiomas: " + livro.idiomas());
            System.out.println("Downloads: " + livro.numeroDownloads());
            System.out.println("---------------------------------");
        });
    }
}