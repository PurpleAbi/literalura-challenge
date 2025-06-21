package com.alura.challenges.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
    private String language;
    private Long downloads;

    public BookEntity(String title, AuthorEntity author, String language, Long downloads) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.downloads = downloads;
    }

    public BookEntity() {

    }

    @Override
    public String toString() {
        return
                "\n Libro: '" + title + "'" +
                        "\n Escrito por: " + (author != null ? author.getName() : "Desconocido") +
                        "\n Idioma: " + language +
                        "\n Numero de descargas: " + downloads;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author.getName();
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }
}
