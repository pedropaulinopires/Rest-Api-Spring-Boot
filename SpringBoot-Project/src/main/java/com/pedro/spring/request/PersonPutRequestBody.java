package com.pedro.spring.request;

import com.pedro.spring.domain.Person;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonPutRequestBody {

    @NotNull(message = "field id is not null!")
    private Long id;
    @NotEmpty(message = "Field name is not empty!")
    private String name;

    public Person build(){
        return Person.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
