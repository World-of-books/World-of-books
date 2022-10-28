package pl.books;

import org.springframework.stereotype.Component;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;
import pl.books.scientific_paper.FieldOfStudy;
import pl.books.scientific_paper.ScientificPaperEntity;
import pl.books.scientific_paper.ScientificPaperRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Set;

@Component
public class Initializer {

    private final AuthorRepository authorRepository;
    private final ScientificPaperRepository scientificPaperRepository;

    public Initializer(AuthorRepository authorRepository, ScientificPaperRepository scientificPaperRepository) {
        this.authorRepository = authorRepository;
        this.scientificPaperRepository = scientificPaperRepository;
    }

    @PostConstruct
    public void run() {
        AuthorEntity hawking = AuthorEntity.of("Stephen", "Hawking");
        authorRepository.save(hawking);

        ScientificPaperEntity babyUniverses = ScientificPaperEntity.of("Baby Universes And The Nonrenormalizability Of Gravity.", "Baby Universes And The Nonrenormalizability Of Gravity.",
                Set.of(hawking), FieldOfStudy.PHYSIC, "University", false, 189, LocalDate.of(1988, 5, 5));
        scientificPaperRepository.save(babyUniverses);
        hawking.getPublications().add(babyUniverses);
        authorRepository.save(hawking);
    }
}
