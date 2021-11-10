package pl.recipes2pantry.pantry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.recipes2pantry.exceptions.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PantryServiceTest {
    @Mock
    PantryRepository pantryRepository;
    @Mock
    PantryMapper pantryMapper;
    @InjectMocks
    PantryService pantryService;

    @BeforeEach
    void SetUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("deletePantry should throw exception when pantry does not exists")
    void deletePantryShouldThrowEntityNotFoundException() {
        //given
        when(pantryRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when + then
        assertThatThrownBy(() -> pantryService.deletePantry(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Pantry");
    }

    @Test
    @DisplayName("createPantry should create an empty pantry")
    void createPantryShouldCreateEmptyPantry() {
        //given
        PantryDto pantryDto = new PantryDto();
        pantryDto.setId(1L);
        pantryDto.setStockSet(null);

        //when
        when(pantryMapper.map(any(Pantry.class))).thenReturn(pantryDto);
        when(pantryService.createPantry(any(PantryDto.class))).thenReturn(pantryDto);
        PantryDto newPantryDto = pantryService.createPantry(pantryDto);

        //then
        assertNull(newPantryDto.getStockSet());
    }
}