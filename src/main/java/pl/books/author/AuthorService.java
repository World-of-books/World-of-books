package pl.books.author;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.books.audiobook.AudiobookDTO;
import pl.books.audiobook.AudiobookEntity;
import pl.books.audiobook.AudiobookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;



//    public List<AudiobookDTO> getAudiobooks() {
//        return audiobookRepository.findAll()
//                .stream()
//                .map(this::convertEntityToDto)
//                .collect(Collectors.toList());
//    }
    public List<AuthorDTO> getAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertAuthorEntityToAuthorDto)
                .collect(Collectors.toList());
    }


    private AuthorDTO convertAuthorEntityToAuthorDto(AuthorEntity authorEntity){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO = modelMapper.map(authorEntity, AuthorDTO.class);
        return authorDTO;
    }

    private AuthorEntity convertAuthorDtoToAuthorEntity(AuthorDTO authorDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity = modelMapper.map(authorDTO, AuthorEntity.class);
        return authorEntity;
    }


}
