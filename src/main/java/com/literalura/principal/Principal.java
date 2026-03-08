package com.literalura.principal;

import com.literalura.model.Autor;
import com.literalura.model.DadosResultados;
import com.literalura.model.Livro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LivroRepository;
import com.literalura.service.ConsumoApi;
import com.literalura.util.ConverteDados;

import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    ---------------------------------------
                    Escolha o número de sua opção:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    6 - Top 10 livros mais baixados da API
                    7 - Buscar autor por nome
                    
                    0 - Sair
                    ---------------------------------------
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroNaWeb();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosNoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    listarTop10LivrosApi();
                    break;
                case 7:
                    buscarAutorPorNome();
                    break;
                case 0:
                    System.out.println("Saindo do LiterAlura...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroNaWeb() {
        System.out.println("Digite o nome do livro que você deseja buscar:");
        var titulo = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO + titulo.replace(" ", "%20"));
        var dados = conversor.obterDados(json, DadosResultados.class);

        if (dados.resultados().isEmpty()) {
            System.out.println("Livro não encontrado na base de dados do Gutendex.");
            return;
        }

        var dadosLivro = dados.resultados().get(0);

        var livroExistente = livroRepository.findByTituloIgnoreCase(dadosLivro.titulo());
        if (livroExistente != null) {
            System.out.println("Não é possível registrar o mesmo livro mais de uma vez!");
            return;
        }

        Autor autor;
        if (dadosLivro.autores().isEmpty()) {
            autor = null;
        } else {
            var dadosAutor = dadosLivro.autores().get(0);
            autor = autorRepository.findByNome(dadosAutor.nome());

            if (autor == null) {
                autor = new Autor(dadosAutor);
                autor = autorRepository.save(autor);
            }
        }

        Livro livro = new Livro(dadosLivro, autor);
        livroRepository.save(livro);

        System.out.println("Livro salvo com sucesso!");
        System.out.println("-------------------------");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + (autor != null ? autor.getNome() : "Desconhecido"));
        System.out.println("Idioma: " + livro.getIdioma());
        System.out.println("Downloads: " + livro.getNumeroDownloads());
        System.out.println("-------------------------");
    }

    private void listarLivrosRegistrados() {
        System.out.println("\n--- LIVROS REGISTRADOS ---");
        var livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no banco de dados ainda.");
        } else {
            livros.forEach(l -> {
                System.out.println("Título: " + l.getTitulo());
                System.out.println("Autor: " + (l.getAutor() != null ? l.getAutor().getNome() : "Desconhecido"));
                System.out.println("Idioma: " + l.getIdioma());
                System.out.println("Downloads: " + l.getNumeroDownloads());
                System.out.println("--------------------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n--- AUTORES REGISTRADOS ---");
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado no banco de dados ainda.");
        } else {
            autores.forEach(a -> {
                System.out.println("Nome: " + a.getNome());
                System.out.println("Ano de Nascimento: " + (a.getAnoNascimento() != null ? a.getAnoNascimento() : "Desconhecido"));
                System.out.println("Ano de Falecimento: " + (a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Desconhecido"));
                System.out.print("Livros: [ ");

                var titulos = a.getLivros().stream().map(l -> l.getTitulo()).toList();
                System.out.println(String.join(", ", titulos) + " ]");
                System.out.println("---------------------------");
            });
        }
    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
        leitura.nextLine();

        var autores = autorRepository.findAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no banco de dados para o ano de " + ano);
        } else {
            System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
            autores.forEach(a -> {
                System.out.println("Nome: " + a.getNome());
                System.out.println("Ano de Nascimento: " + a.getAnoNascimento());
                System.out.println("Ano de Falecimento: " + (a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Desconhecido"));
                System.out.println("---------------------------");
            });
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma para busca (ex: pt para Português, en para Inglês, fr para Francês):");
        var idioma = leitura.nextLine();

        var livros = livroRepository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("\n--- LIVROS NO IDIOMA '" + idioma.toUpperCase() + "' ---");
            livros.forEach(l -> {
                System.out.println("Título: " + l.getTitulo());
                System.out.println("Autor: " + (l.getAutor() != null ? l.getAutor().getNome() : "Desconhecido"));
                System.out.println("--------------------------");
            });
        }
    }

    private void buscarAutorPorNome() {
        System.out.println("Digite o nome (ou parte do nome) do autor que deseja buscar:");
        var nome = leitura.nextLine();

        var autores = autorRepository.findByNomeContainingIgnoreCase(nome);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado com o termo: " + nome);
        } else {
            System.out.println("\n--- AUTORES ENCONTRADOS ---");
            autores.forEach(a -> {
                System.out.println("Nome: " + a.getNome());
                System.out.println("Ano de Nascimento: " + (a.getAnoNascimento() != null ? a.getAnoNascimento() : "Desconhecido"));
                System.out.println("Ano de Falecimento: " + (a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Desconhecido"));
                System.out.println("---------------------------");
            });
        }
    }

    private void listarTop10LivrosApi() {
        System.out.println("Buscando os 10 livros mais populares na API do Gutendex...");

        var json = consumoApi.obterDados("https://gutendex.com/books/");
        var dados = conversor.obterDados(json, DadosResultados.class);

        System.out.println("\n--- TOP 10 LIVROS MAIS BAIXADOS (API) ---");
        dados.resultados().stream()
                .limit(10)
                .forEach(l -> {
                    System.out.println("Título: " + l.titulo());
                    System.out.println("Autor: " + (l.autores().isEmpty() ? "Desconhecido" : l.autores().get(0).nome()));
                    System.out.println("Downloads: " + l.numeroDownloads());
                    System.out.println("-----------------------------------------");
                });
    }
}