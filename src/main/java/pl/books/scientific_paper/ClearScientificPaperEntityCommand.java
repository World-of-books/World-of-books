package pl.books.scientific_paper;

import java.util.HashSet;

class ClearScientificPaperEntityCommand {

    ScientificPaperEntity execute(ScientificPaperEntity entity) {
        entity.getAuthors().forEach(authorOpt -> authorOpt.getPublications()
                .ifPresent(author -> author.remove(entity)));
        entity.setAuthors(new HashSet<>());
        return entity;
    }
}
