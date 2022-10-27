package pl.books.book;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.books.book.BookEntity;
import pl.books.book.BookEntityRepository;

import java.util.List;

@Service
public class BookEntityService {



    private final BookEntityRepository bookEntityRepository;

    public BookEntityService(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    public BookEntity save(BookEntity bookEntity){
        return bookEntityRepository.save(bookEntity);
    }

    public List<BookEntity> getAll(){
        return bookEntityRepository.findAll();
    }

//    public BookEntity update(BookEntity bookEntity){
//        return bookEntityRepository.save(bookEntity);
//    }

    public void deleteById(Integer id){
        bookEntityRepository.deleteById(id);
    }

    public BookEntity getById(Integer id){
       return bookEntityRepository.findById(id).orElse(null);
    }
}
