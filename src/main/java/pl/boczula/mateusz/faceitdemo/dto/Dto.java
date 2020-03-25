package pl.boczula.mateusz.faceitdemo.dto;

import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;

public interface Dto {

    Long getId();

    ValidationResult getValidationResult();

}
