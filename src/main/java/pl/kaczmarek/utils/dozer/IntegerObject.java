package pl.kaczmarek.utils.dozer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerObject {

    public String name;
    public Integer value;

    public IntegerObject(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
