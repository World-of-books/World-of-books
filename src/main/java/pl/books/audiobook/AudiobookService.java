package pl.books.audiobook;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AudiobookService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AudiobookRepository audiobookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public AudiobookEntity createAudiobook(AudiobookDTO audiobook) {
        AudiobookEntity audiobookEntity = convertDtoToEntity(audiobook);
        List<AuthorEntity> authors = audiobookEntity.getAuthors();
        for(AuthorEntity author:authors){
            AuthorEntity author1 = authorRepository.findById(author.getId()).get();
            List<AudiobookEntity> audiobooks = author1.getAudiobooks();
            audiobooks.add(audiobookEntity);
        }
        return audiobookRepository.save(audiobookEntity);
    }

    public List<AudiobookEntity> createAudiobooks(List<AudiobookEntity> audiobooks) {
        return audiobookRepository.saveAll(audiobooks);
    }

    public AudiobookDTO getAudiobookById(Long id) {
        AudiobookEntity audiobook = audiobookRepository.findById(id).orElse(null);// stream + converter
        return convertEntityToDto(audiobook);
    }

    public List<AudiobookDTO> getAudiobooks() {
        return audiobookRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public AudiobookDTO updateAudiobook(Long id, AudiobookDTO audiobookDTO) {
        AudiobookEntity audiobookEntity = audiobookRepository.findById(id).get();
        AudiobookDTO audiobookDTO1 = convertEntityToDto(audiobookEntity);
        audiobookDTO1.setTitle(audiobookDTO.getTitle());
        audiobookDTO1.setDescription(audiobookDTO.getDescription());

        forTheCollectionOfAuthorsRemoveSpecificAudiobook(audiobookEntity, audiobookDTO1);

        List<AudiobookAuthorDTO> authors2 = audiobookDTO.getAuthors();
        for(AudiobookAuthorDTO author:authors2){
            AuthorEntity author1 = authorRepository.findById(author.getId()).get();
            List<AudiobookEntity> audiobooks = author1.getAudiobooks();
            audiobooks.add(audiobookEntity);
        }

        audiobookDTO1.setAuthors(audiobookDTO.getAuthors());
        audiobookDTO1.setLength(audiobookDTO.getLength());
        audiobookDTO1.setPublishedDate(audiobookDTO.getPublishedDate());
        audiobookDTO1.setIsbn(audiobookDTO.getIsbn());
        audiobookDTO1.setPublishingHouse(audiobookDTO.getPublishingHouse());
        audiobookDTO1.setQuantity(audiobookDTO.getQuantity());

        audiobookRepository.save(convertDtoToEntity(audiobookDTO1));

        return audiobookDTO1;
    }

    private void forTheCollectionOfAuthorsRemoveSpecificAudiobook(AudiobookEntity audiobookToRemove, AudiobookDTO audiobookToRemoveDTO) {

        List<AudiobookAuthorDTO> authors = audiobookToRemoveDTO.getAuthors();

        for(AudiobookAuthorDTO author:authors){
            AuthorEntity fetchedAuthor = authorRepository.findById(author.getId()).get();
            List<AudiobookEntity> audiobooks = fetchedAuthor.getAudiobooks();
            for(AudiobookEntity audiobook:audiobooks){
                if(audiobook.getId() ==  audiobookToRemove.getId()){
                    audiobooks.remove(audiobook);
                    break;
                }
            }
        }
    }

    public String deleteAudiobookById(Long id) {
        AudiobookEntity audiobookEntity = audiobookRepository.findById(id).get();

        AudiobookDTO audiobookDTO1 = convertEntityToDto(audiobookEntity);

        forTheCollectionOfAuthorsRemoveSpecificAudiobook(audiobookEntity, audiobookDTO1);

        audiobookRepository.deleteById(id);
        return "Audiobook got deleted";
    }

    private AudiobookDTO convertEntityToDto(AudiobookEntity audiobook){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AudiobookDTO audiobookDTO = new AudiobookDTO();
        audiobookDTO = modelMapper.map(audiobook, AudiobookDTO.class);
        return audiobookDTO;
    }

    private AudiobookEntity convertDtoToEntity(AudiobookDTO audiobookDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        AudiobookEntity audiobook = new AudiobookEntity();
        audiobook = modelMapper.map(audiobookDTO, AudiobookEntity.class);
        return audiobook;
    }

    private AudiobookAuthorDTO convertEntityToDtoAuthor(AuthorEntity author){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AudiobookAuthorDTO audiobookAuthorDTO = new AudiobookAuthorDTO();
        audiobookAuthorDTO = modelMapper.map(author, AudiobookAuthorDTO.class);
        return audiobookAuthorDTO;
    }

    private AuthorEntity convertDtoToEntityAuthor (AudiobookDTO audiobookAuthorDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AuthorEntity author = new AuthorEntity();
        author = modelMapper.map(audiobookAuthorDTO, AuthorEntity.class);
        return author;
    }

}













