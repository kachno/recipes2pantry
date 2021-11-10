package pl.recipes2pantry.pantry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.recipes2pantry.exceptions.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional
public class PantryService {
    private final PantryRepository pantryRepository;
    private final PantryMapper pantryMapper;

    public PantryDto createPantry(PantryDto pantryDto) {
        Pantry pantry = pantryMapper.map(pantryDto);
        pantryRepository.save(pantry);
        return pantryMapper.map(pantry);
    }

    public ContentDto getAllContent(Long pantryId) {
        return pantryRepository.findByIdWithStocks(pantryId)
                .map(pantryMapper::mapToContent)
                .orElseThrow(() -> new EntityNotFoundException("Pantry not found"));
    }

    public PantryDto getPantry(Long pantryId) {
        return pantryRepository.findById(pantryId)
                .map(pantryMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Pantry not found"));
    }

    public void deletePantry(Long pantryId) {
        pantryRepository.findById(pantryId)
                .orElseThrow(() -> new EntityNotFoundException("Pantry not found"));
        pantryRepository.deleteById(pantryId);
    }
}
