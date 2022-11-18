package pl.books.scientific_paper;

import org.springframework.stereotype.Component;

@Component
class ScientificPaperCommands {

    ScientificPaperEntity clearScientificPaperEntity(ScientificPaperEntity entity) {
        return new ClearScientificPaperEntityCommand().execute(entity);
    }
}
