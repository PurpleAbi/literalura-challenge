package com.alura.challenges.literalura.service;

import com.alura.challenges.literalura.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Main {
    private final BookService bookService;
    Menu menu = new Menu();
    private final Scanner userInput = new Scanner(System.in);

    @Autowired
    public Main(BookService service) {
        this.bookService = service;
    }

    public void menuChoices(){
        var choice = -1;
        while (choice != 0 ){
            menu.showMenu();
            choice = userInput.nextInt();
            userInput.nextLine();

            switch (choice) {
                case 1:
                    bookService.saveBook();
                    break;
                case 2:
                    bookService.showHistory();
                    break;
                case 3:
                    bookService.showAuthors();
                    break;
                case 4:
                    System.out.println("Escriba el año para ver los autores que estaban vivos en ese momento:");
                    bookService.showAuthorsAliveInYear();
                    break;
                case 5:
                    bookService.showBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...\nGracias por usar Literalura! \n");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }

    }

}
