package pl.kaczmarek.utils.dozer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StringObject {

    public String name;
    public String value;

    public StringObject(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
