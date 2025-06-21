package com.alura.challenges.literalura.service;

import com.alura.challenges.literalura.integration.bookapi.BooksDataFetcher;
import com.alura.challenges.literalura.integration.bookapi.JsonConverter;
import com.alura.challenges.literalura.model.AuthorEntity;
import com.alura.challenges.literalura.dto.BookData;
import com.alura.challenges.literalura.model.BookEntity;
import com.alura.challenges.literalura.dto.ResultsDataApi;
import com.alura.challenges.literalura.repository.AuthorsRepository;
import com.alura.challenges.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class BookService {
    private final BooksDataFetcher API_CLIENT = new BooksDataFetcher();
    private final JsonConverter CONVERTER = new JsonConverter();
    private ResultsDataApi results = null;
    private List<BookEntity> userListOfBooks;
    private List<AuthorEntity> userListOfAuthors;
    private final Scanner userInput = new Scanner(System.in);
    private final BookRepository repositoryBooks;
    private final AuthorsRepository repositoryAuthors;
    @Autowired
    public BookService(BookRepository repositoryB, AuthorsRepository repositoryA) {
        this.repositoryBooks = repositoryB;
        this.repositoryAuthors = repositoryA;
    }
    
    public ResultsDataApi searchResultsData(){
        var clientBook = (userInput.nextLine()).replace(" ", "%20");
        System.out.println("Buscando ... \nA veces tarda un poco, espere por favor...");
        String URL = "https://gutendex.com/books/";
        var jsonResponse = API_CLIENT.getData(URL + "?search="+ clientBook);
        return CONVERTER.fromJson(jsonResponse, ResultsDataApi.class);
    }

    public void saveBook() {
        userListOfBooks = repositoryBooks.findAll();
        userListOfAuthors = repositoryAuthors.findAll();
        System.out.println("""
                           
                            Si no recuerda el nombre exacto del autor o del libro
                            puede intentar con palabras clave:
                            """);
        while (results == null) {
            ResultsDataApi resultsData = searchResultsData();
            if (resultsData.results().isEmpty()) {
                System.out.println("\nNo se encontraron resultados para su busqueda. " +
                        "\nIntente con otro libro o autor:");
                results = null;
            }else {
                Optional<BookEntity> existingBook = userListOfBooks.stream()
                        .filter(book -> book.getTitle().equalsIgnoreCase(resultsData.results().getFirst().title()))
                        .findFirst();
                if (existingBook.isPresent()) {
                    List<BookData> otherBooks = resultsData.results().stream()
                            .filter(book -> userListOfBooks.stream()
                                    .noneMatch(existing -> existing.getTitle().equalsIgnoreCase(book.title())))
                            .limit(3)
                            .toList();
                        System.out.println("\nEl libro \"" + existingBook.get().getTitle() + "\" de " + existingBook.get().getAuthor()
                                + " ya se encuentra en su biblioteca personal.");
                    if (!otherBooks.isEmpty()) {
                        System.out.println("\nPuede que le interese alguno de estos libros:");
                        otherBooks.forEach(book -> System.out.println("- " + book.title() + " de " + book.authors().getFirst().name()));
                    }
                System.out.println("\nPuede intentar con otro libro o autor:");
                        results = null;
                } else {
                    BookData bookData = resultsData.results().getFirst();
                    Optional<AuthorEntity> existingAuthor = userListOfAuthors.stream()
                            .filter(a -> a.getName().equalsIgnoreCase(bookData.authors().getFirst().name()))
                            .findFirst();
                    AuthorEntity author = existingAuthor.orElseGet(() -> new AuthorEntity(bookData.authors().getFirst().name(), bookData.authors().getFirst().birth(), bookData.authors().getFirst().death()));
                    BookEntity newBook = new BookEntity(bookData.title(), author,
                            bookData.languages().getFirst(), Long.valueOf(bookData.downloads()));
                    System.out.println(newBook);
                    author.getBooks().add(newBook);
                    newBook.setAuthor(author);
                    repositoryAuthors.save(author);
                    System.out.println("***Libro guardado en su biblioteca personal***");
                    results = resultsData;
                }

            }
        }

    }

    public void showHistory() {
        userListOfBooks = repositoryBooks.findAll();
        if (userListOfBooks.isEmpty()) {
            System.out.println("***Su biblioteca personal esta vacia. Empiece a buscar libros con la opcion 1***");
        } else {
            System.out.println("Lista de libros en su biblioteca personal:");
            userListOfBooks.stream()
                    .sorted(Comparator.comparing(BookEntity::getAuthor).reversed())
                    .forEach(System.out::println);
        }
    }

    public void showAuthors() {
        userListOfAuthors = repositoryAuthors.findAll();
       if (userListOfAuthors.isEmpty()) {
            System.out.println("***No hay autores en su biblioteca personal. Empiece a buscar libros con la opcion 1***");
       }else {
           System.out.println("Lista de autores de su biblioteca personal:\n");
           userListOfAuthors.stream()
                   .sorted(Comparator.comparing(AuthorEntity::getName).reversed())
                   .forEach(System.out::println);
       }
    }

    public void showAuthorsAliveInYear() {
    int year = userInput.nextInt();
    List<AuthorEntity> aliveAuthors = repositoryAuthors.findAuthorsAliveInYear(year);
    if (aliveAuthors.isEmpty()) {
        System.out.println("***No hay autores en su bibliotecas vivos en el año " + year + "***");
    } else {
        System.out.println("Autores vivos en el año " + year + ":\n");
        aliveAuthors.stream()
                .sorted(Comparator.comparing(AuthorEntity::getName).reversed())
                .forEach(System.out::println);
    }
    }

    public void showBooksByLanguage() {
        String choice = "";
        String language = "";
        var options = """
                1. Ingles
                2. Espanol
                3. Frances
                4. Aleman
                5. Italiano
                6. Portugues
                """;
        System.out.println("Escriba el numero de la opcion de idioma de los libros que desea filtrar. \n" +
                options);
        int userChoice= userInput.nextInt();
        switch (userChoice) {
            case 1 -> {
                choice = "Ingles";
                language = "en";
            }
            case 2 -> {
                choice = "Espanol";
                language = "es";
            }
            case 3 -> {
                choice = "Frances";
                language = "fr";
            }
            case 4 -> {
                choice = "Aleman";
                language = "de";
            }
            case 5 -> {
                choice ="Italiano";
                language = "it";
            }
            case 6 -> {
                choice = "Portugues";
                language = "pt";
            }
            default -> System.out.println("***Opcion invalida. Intente de nuevo.***");
        }
        List<BookEntity> booksByLanguage = repositoryBooks.findAllByLanguageContaining(language);
        if (booksByLanguage.isEmpty()) {
            System.out.println("***No hay libros en su biblioteca personal en ese idioma***");
        } else {
            System.out.println("Lista de libros en su biblioteca personal en \"" + choice + "\":");
            booksByLanguage.stream()
                    .sorted(Comparator.comparing(BookEntity::getTitle).reversed())
                    .forEach(System.out::println);
        }
    }
}
