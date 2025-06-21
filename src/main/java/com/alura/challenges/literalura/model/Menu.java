package com.alura.challenges.literalura.model;


public class Menu {

     public void showMenu(){
            System.out.println("""
                    
                    ****************************************************
                    Biblioteca de ebooks del Proyecto Gutenberg.
                    Escriba el numero de la opcion que desea ejecutar...
                    
                    1 - Buscar libro nuevo
                    
                    2 - Lista de libros que ya he buscado 
                    3 - Lista de autores de los libros buscados
                    
                    4 - Listar autores vivos dependiendo del anio
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    ****************************************************                            
                    """);
    }

}
