package ee.kregor.rentalstore.dto;

import ee.kregor.rentalstore.entity.FilmType;

public record FilmSaveDto(
        String title,
        FilmType type
) {
}
