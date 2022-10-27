package pl.books;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.books.author.Author;
import pl.books.author.AuthorRepository;
import pl.books.book.BookEntity;
import pl.books.book.BookEntityRepository;
import pl.books.scientific_paper.ScientificPaperRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Component
public class Init {

    private final BookEntityRepository bookEntityRepository;

    private final ScientificPaperRepository scientificPaperRepository;

    private final AuthorRepository authorRepository;


    @PostConstruct
    public void runList() {
        Author henr = Author.of("Henryk", "Sienkiewicz");
        Author mick = Author.of("Adam", "Mickiewicz");
        Author herb = Author.of("Herbert", "Shild");
        Author cay = Author.of("Cay", "Horstman");
        Author rog = Author.of("Rogers", "Cadenhead");
        Author rob = Author.of("Robert", "Sedgewick");
        Author laur = Author.of("Laura", "Lemay");


        authorRepository.save(henr);
        authorRepository.save(mick);
        authorRepository.save(herb);
        authorRepository.save(cay);
        authorRepository.save(rog);
        authorRepository.save(rob);
        authorRepository.save(laur);


        BookEntity panTadeusz = new BookEntity(8, "Pan Tateusz", List.of(mick), "3424", LocalDate.of(1978, 11, 21), 700, "PWN", 20);
        bookEntityRepository.save(panTadeusz);
        mick.getBooks().add(panTadeusz);
        authorRepository.save(mick);

        BookEntity ogniemIMieczem = new BookEntity(9, "Ogniem i mieczem", List.of(henr), "982387283", LocalDate.of(1999, 11, 12), 588, "Siedmioróg", 15);
        bookEntityRepository.save(ogniemIMieczem);
        henr.getBooks().add(ogniemIMieczem);
        authorRepository.save(henr);

        BookEntity potop = new BookEntity(10, "Potop", List.of(henr), "453457283", LocalDate.of(1987, 11, 17), 564, "Siedmioróg", 19);
        bookEntityRepository.save(potop);
        henr.getBooks().add(potop);
        authorRepository.save(henr);

        BookEntity panWolodyjowski = new BookEntity(11, "Pan Wołodyjowski", List.of(henr), "8473657283", LocalDate.of(1990, 07, 27), 600, "Siedmioróg", 10);
        bookEntityRepository.save(panWolodyjowski);
        henr.getBooks().add(panWolodyjowski);
        authorRepository.save(henr);

        BookEntity javaPrzewodnik = new BookEntity(12, "Java Przewodnik dla początkujacych", List.of(herb), "3443657283", LocalDate.of(2019, 07, 20), 559, "Helion", 50);
        bookEntityRepository.save(javaPrzewodnik);
        herb.getBooks().add(javaPrzewodnik);
        authorRepository.save(herb);

        BookEntity javaPodstawy = new BookEntity(13, "Java Podstawy", List.of(cay), "45343657283", LocalDate.of(2016, 03, 10), 523, "Helion", 32);
        bookEntityRepository.save(javaPodstawy);
        cay.getBooks().add(javaPodstawy);
        authorRepository.save(cay);

        BookEntity javaW = new BookEntity(14, "Java w 21 dni", List.of(rog), "344334343", LocalDate.of(2016, 07, 20), 634, "Helion", 80);
        bookEntityRepository.save(javaW);
        rog.getBooks().add(javaW);
        authorRepository.save(rog);

        BookEntity algorytmy = new BookEntity(15, "Algorytmy", List.of(rob), "3434234283", LocalDate.of(2018, 03, 20), 658, "Helion", 43);
        bookEntityRepository.save(algorytmy);
        rob.getBooks().add(algorytmy);
        authorRepository.save(rob);

        BookEntity htmlCssJS = new BookEntity(16, "HTML,CSS I JavaScript", List.of(laur), "1823233283", LocalDate.of(2016, 02, 10), 703, "Helion", 11);
        bookEntityRepository.save(htmlCssJS);
        laur.getBooks().add(htmlCssJS);
        authorRepository.save(laur);


    }


}
