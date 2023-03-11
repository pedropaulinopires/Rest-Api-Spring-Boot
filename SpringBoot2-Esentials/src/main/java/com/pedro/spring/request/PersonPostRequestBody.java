package com.pedro.spring.request;

import com.pedro.spring.domain.Person;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonPostRequestBody {

    @NotEmpty(message = "field name PersonPostRequestBody not empty")
    private String name;

    public Person build() {
        return Person.builder().name(this.name).build();
    }
}
