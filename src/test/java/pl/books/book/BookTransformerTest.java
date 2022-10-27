package pl.books.book;

import org.junit.jupiter.api.Test;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookTransformerTest {

    @Test
    void toDTO() {
        BookTransformer bookTransformer = new BookTransformer();
        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle("tekst");
        BookDTO bookDTO = bookTransformer.toDTO(bookEntity);

        assertEquals(bookEntity.getTitle(),bookDTO.getTitle());

    }

    @Test
    void toEntity() {
        BookTransformer bookTransformer = new BookTransformer();
        BookDTO bookDTO = new BookDTO();

        bookDTO.setTitle("tekst");
        BookEntity entity = bookTransformer.toEntity(bookDTO);


        assertEquals(bookDTO.getTitle(), entity.getTitle());
    }
}