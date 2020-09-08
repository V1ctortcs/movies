package com.trimble.utils;

import com.trimble.model.LetterMetricsModel;
import com.trimble.model.MovieModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ValidatorLetters {

    private LetterMetricsModel letterMetricsModel;

    public String validatorConsonants(List<MovieModel> models, List<LetterMetricsModel> letterMetricsModels, int i,
                                       Integer cont, String l, Character c) {
        if (c.equals('b') || c.equals('c')  || c.equals('d') || c.equals('f') || c.equals('g') || c.equals('j')
                || c.equals('k') || c.equals('l') || c.equals('m')|| c.equals('n') || c.equals('p') || c.equals('q')
                || c.equals('r') || c.equals('s') || c.equals('t') || c.equals('v') || c.equals('w') || c.equals('x')
                || c.equals('y') || c.equals('z')
                && !l.contains("" + c)) {
            l = l + c;
            letterMetricsModel = LetterMetricsModel.builder()
                    .letter(models.get(0).getTitleMovie().toLowerCase().charAt(i))
                    .amount(cont)
                    .build();
            letterMetricsModels.add(letterMetricsModel);
//            if(letterMetricsModels.size()>=11){
//                letterMetricsModels.remove(0);
//            }
        }
        return l;
    }
}
