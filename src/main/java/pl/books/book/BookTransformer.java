package pl.books.book;

import org.springframework.stereotype.Component;
import pl.books.author.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookTransformer {

    BookDTO toDTO(BookEntity entity){
        List<BookAuthorDTO> authors = entity.getAuthor().stream()
                .map(author -> new BookAuthorDTO(author.getId(), author.getFirstName(), author.getLastName()) )
                .toList();
        return new BookDTO(
                entity.getId(),
                entity.getTitle(),
                authors,
                entity.getIsbn(),
                entity.getReleaseDate(),
                entity.getNumberOfPages(),
                entity.getPublishingHouse(),
                entity.getQuantity());
    }
    // napisz metode do przetlumaczenia dto na entity

    BookEntity toEntity(BookDTO bookDTO){
        BookEntity entity = new BookEntity();
        entity.setTitle(bookDTO.getTitle());
        entity.setAuthor(bookDTO.getAuthors().stream().map(m->new Author(m.getFirstName(),
                m.getLastName())).collect(Collectors.toList()));
        entity.setIsbn(bookDTO.getIsbn());
        entity.setReleaseDate(bookDTO.getReleaseDate());
        entity.setNumberOfPages(bookDTO.getNumberOfPages());
        entity.setPublishingHouse(bookDTO.getPublishingHouse());
        entity.setQuantity(bookDTO.getQuantity());
        return entity;

    }






}
