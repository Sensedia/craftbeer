package com.beerhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Janaina Militão
 */
@Getter @Setter
@AllArgsConstructor
public class MessageErrorDTO {

    private String codeError;

    private String field;

    private String message;

}