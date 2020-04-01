package com.cdfive.common.tool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Holder<T> {

    private T value;
}
