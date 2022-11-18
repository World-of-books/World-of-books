package pl.books.audiobook;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AudiobookAuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;
//    private List<AudiobookEntity> audiobooks;


}
