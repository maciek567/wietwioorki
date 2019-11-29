package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Genre {
    private Long id;
    private String name;
    private String description;
}