package com.example.cameratranslator.callback;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public interface ListCallback<T> {
    void execute(List<T> list);
}
