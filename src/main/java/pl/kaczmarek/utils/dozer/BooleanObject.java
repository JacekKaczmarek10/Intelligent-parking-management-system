package pl.kaczmarek.utils.dozer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanObject {

    public String name;
    public Boolean value;

    public BooleanObject(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }
}
