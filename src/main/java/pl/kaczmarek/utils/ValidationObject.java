package pl.kaczmarek.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ValidationObject {

    public List<String> errorList = new ArrayList<>();
    public String reason = "no reason";
}
