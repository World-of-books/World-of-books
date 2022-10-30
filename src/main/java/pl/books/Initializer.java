package pl.books;

import org.springframework.stereotype.Component;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;
import pl.books.scientific_paper.FieldOfStudy;
import pl.books.scientific_paper.ScientificPaperEntity;
import pl.books.scientific_paper.ScientificPaperRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashSet;
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
        AuthorEntity hawking = new AuthorEntity(1L, "Stephen", "Hawking");
        authorRepository.save(hawking);

        AuthorEntity hengstberger = new AuthorEntity(2L, "Franz", "Hengstberger");
        authorRepository.save(hengstberger);

        AuthorEntity patterson = new AuthorEntity(3L, "Wade", "Patterson");
        authorRepository.save(patterson);

        AuthorEntity thomas = new AuthorEntity(4L, "Ralph", "Thomas");
        authorRepository.save(thomas);

        AuthorEntity grumezescu = new AuthorEntity(5L, "Alexandru", "Grumezescu");
        authorRepository.save(grumezescu);

        AuthorEntity dean = new AuthorEntity(6L, "Andrew", "Dean");
        authorRepository.save(dean);

        ScientificPaperEntity babyUniverses = new ScientificPaperEntity(7L, "Baby Universes And The Nonrenormalizability Of Gravity.", "Baby Universes And The Nonrenormalizability Of Gravity.",
                new HashSet<>(), FieldOfStudy.PHYSIC, "University", false, 189, LocalDate.of(1988, 5, 5));
        hawking.getPublications().ifPresent(publications -> publications.add(babyUniverses));
        babyUniverses.getAuthors().get().add(hawking);
        scientificPaperRepository.save(babyUniverses);
        authorRepository.save(hawking);


        ScientificPaperEntity absolute_radiometry = new ScientificPaperEntity(8L, "Absolute Radiometry", "Electrically Calibrated Thermal Detectors of Optical Radiation",
                Set.of(hengstberger), FieldOfStudy.PHYSIC, "National Physical Research Laboratory, Council for Scientific and Industrial Research, Pretoria, South Africa", false, 117, LocalDate.of(1989, 1, 1));
        scientificPaperRepository.save(absolute_radiometry);
        hengstberger.getPublications().get().add(absolute_radiometry);
        authorRepository.save(hengstberger);

        ScientificPaperEntity accelerator_health_physics = new ScientificPaperEntity(9L, "Accelerator Health Physics", "Accelerator Health Physics tackles the importance of health physics in the field of nuclear physics, especially to those involved with the use of particle accelerators.",
                Set.of(patterson, thomas), FieldOfStudy.PHYSIC, "LAWRENCE BERKELEY LABORATORY, UNIVERSITY OF CALIFORNIA, BERKELEY, CALIFORNIA", false, 92, LocalDate.of(1971, 1, 1));
        scientificPaperRepository.save(accelerator_health_physics);
        patterson.getPublications().get().add(accelerator_health_physics);
        thomas.getPublications().get().add(accelerator_health_physics);
        authorRepository.save(patterson);
        authorRepository.save(thomas);

        ScientificPaperEntity nanoarchitectonics_in_biomedicine = new ScientificPaperEntity(10L, "Nanoarchitectonics in Biomedicine", "The book brings together recent applications and discusses the advantages and disadvantages of each process, offering international perspectives on the technologies based on these findings",
                Set.of(grumezescu), FieldOfStudy.HEALTH, "Faculty of Applied Chemistry and Materials Science, University Politehnica of Bucharest, Bucharest, Romani", false, 54, LocalDate.of(2019, 1, 1));
        scientificPaperRepository.save(nanoarchitectonics_in_biomedicine);
        grumezescu.getPublications().get().add(nanoarchitectonics_in_biomedicine);
        authorRepository.save(grumezescu);

        ScientificPaperEntity wages_and_earnings = new ScientificPaperEntity(11L, "Wages and Earnings", "Wages and Earnings is a review of statistical sources, both official and non-official, on wages and earnings in Britain.",
                Set.of(dean), FieldOfStudy.MATHEMATICS, "Organisation for Economic Cooperation and Development formerly at the National Institute of Economic and Social Research", false, 161, LocalDate.of(1980, 1, 1));
        scientificPaperRepository.save(wages_and_earnings);
        dean.getPublications().get().add(wages_and_earnings);
        authorRepository.save(dean);

    }
}
