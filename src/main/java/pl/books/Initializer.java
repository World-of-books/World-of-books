package pl.books;

import org.springframework.stereotype.Component;
import pl.books.audiobook.AudiobookEntity;
import pl.books.audiobook.AudiobookRepository;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;
import pl.books.scientific_paper.FieldOfStudy;
import pl.books.scientific_paper.ScientificPaperEntity;
import pl.books.scientific_paper.ScientificPaperRepository;


import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Initializer {

    private final AuthorRepository authorRepository;
    private final ScientificPaperRepository scientificPaperRepository;
    private final AudiobookRepository audiobookRepository;


    public Initializer(AuthorRepository authorRepository, ScientificPaperRepository scientificPaperRepository, AudiobookRepository audiobookRepository) {
        this.authorRepository = authorRepository;
        this.scientificPaperRepository = scientificPaperRepository;
        this.audiobookRepository = audiobookRepository;
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
                new HashSet<>(), FieldOfStudy.PHYSIC, "University", false, 189, LocalDate.of(1988, 5, 5), 10);
        hawking.getPublications().add(babyUniverses);
        babyUniverses.getAuthors().add(hawking);
        scientificPaperRepository.save(babyUniverses);

        ScientificPaperEntity absolute_radiometry = new ScientificPaperEntity(8L, "Absolute Radiometry", "Electrically Calibrated Thermal Detectors of Optical Radiation",
                Set.of(hengstberger), FieldOfStudy.PHYSIC, "National Physical Research Laboratory, Council for Scientific and Industrial Research, Pretoria, South Africa", false, 117, LocalDate.of(1989, 1, 1), 10);
        hengstberger.getPublications().add(absolute_radiometry);
        scientificPaperRepository.save(absolute_radiometry);

        ScientificPaperEntity accelerator_health_physics = new ScientificPaperEntity(9L, "Accelerator Health Physics", "Accelerator Health Physics tackles the importance of health physics in the field of nuclear physics, especially to those involved with the use of particle accelerators.",
                Set.of(patterson, thomas), FieldOfStudy.PHYSIC, "LAWRENCE BERKELEY LABORATORY, UNIVERSITY OF CALIFORNIA, BERKELEY, CALIFORNIA", false, 92, LocalDate.of(1971, 1, 1), 10);
        patterson.getPublications().add(accelerator_health_physics);
        thomas.getPublications().add(accelerator_health_physics);
        scientificPaperRepository.save(accelerator_health_physics);

        ScientificPaperEntity nanoarchitectonics_in_biomedicine = new ScientificPaperEntity(10L, "Nanoarchitectonics in Biomedicine", "The book brings together recent applications and discusses the advantages and disadvantages of each process, offering international perspectives on the technologies based on these findings",
                Set.of(grumezescu), FieldOfStudy.HEALTH, "Faculty of Applied Chemistry and Materials Science, University Politehnica of Bucharest, Bucharest, Romani", false, 54, LocalDate.of(2019, 1, 1), 10);
        grumezescu.getPublications().add(nanoarchitectonics_in_biomedicine);
        scientificPaperRepository.save(nanoarchitectonics_in_biomedicine);

        ScientificPaperEntity wages_and_earnings = new ScientificPaperEntity(11L, "Wages and Earnings", "Wages and Earnings is a review of statistical sources, both official and non-official, on wages and earnings in Britain.",
                Set.of(dean), FieldOfStudy.MATHEMATICS, "Organisation for Economic Cooperation and Development formerly at the National Institute of Economic and Social Research", false, 161, LocalDate.of(1980, 1, 1), 10);
        dean.getPublications().add(wages_and_earnings);
        scientificPaperRepository.save(wages_and_earnings);


        List<AuthorEntity> auth = new ArrayList<>();
        auth.add(hawking);
        AudiobookEntity panTadeuszAudio = new AudiobookEntity("Pan Tateusz","lektura szkolna", auth, false,43200, LocalDate.of(2005, 02, 14), "3424", "PWN", 5);
        audiobookRepository.save(panTadeuszAudio);
        hawking.getAudiobooks().add(panTadeuszAudio);
        authorRepository.save(hawking);
    }
}
