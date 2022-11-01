package pl.books.scientific_paper;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ScientificPaperControllerNotValidArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                //Request JSON,
                // error details array for name
                // error details array for description
                // error details array for authors
                // error details array for field
                // error details array for university
                // error details array for isForAdults
                // error details array for pages
                // error details array for publishedDate
                Arguments.of(
                        """
                                                {
                                                  "description": "test",
                                                  "field": "astronomy",
                                                  "forAdults": true,
                                                  "name": "",
                                                  "pages": 123,
                                                  "publishedDate": "1999-10-10",
                                                  "university": "string"
                                                }
                                """,
                        new String[]{"Scientific paper should be at least 3 characters long"},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ),
                Arguments.of(
                        """
                                                {
                                                  "description": "test",
                                                  "field": "astronomy",
                                                  "forAdults": true,
                                                  "name": "12",
                                                  "pages": 123,
                                                  "publishedDate": "1999-10-10",
                                                  "university": "string"
                                                }
                                """,
                        new String[]{"Scientific paper should be at least 3 characters long"},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null

                ),
                Arguments.of(
                        """
                                                {
                                                  "field": "astronomy",
                                                  "forAdults": true,
                                                  "name": "test",
                                                  "pages": 123,
                                                  "publishedDate": "1999-10-10",
                                                  "university": "string"
                                                }
                                """,
                        null,
                        new String[]{"Scientific paper description cannot be empty"},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null

                ),
                Arguments.of(
                        """
                                                {
                                                  "description": "test",
                                                  "field": "astronomy",
                                                  "forAdults": true,
                                                  "name": "test",
                                                  "pages": -123,
                                                  "publishedDate": "1999-10-10",
                                                  "university": "string"
                                                }
                                """,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new String[]{"Pages cannot be negative"},
                        null

                ),
                Arguments.of(
                        """
                                                {
                                                }
                                """,
                        new String[]{"Scientific paper name cannot be empty"},
                        new String[]{"Scientific paper description cannot be empty"},
                        new String[]{"Scientific paper most certainly has any author"},
                        new String[]{"Scientific paper most certainly has any field of study"},
                        new String[]{"Scientific paper university cannot be empty"},
                        new String[]{"Scientific paper is either for adults only or not"},
                        new String[]{"Scientific paper most certainly has any pages"},
                        new String[]{"Scientific paper published date cannot be empty"}

                )
        );
    }
}
