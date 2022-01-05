package pl.kaczmarek.utils.dozer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloatObject {

    public String name;
    public Float value;

    public FloatObject(String name, Float value) {
        this.name = name;
        this.value = value;
    }
}
