package aidoudong.common.resultview;

import java.util.Objects;
import java.util.function.Function;

/**
 * @Author: dlus91
 * @Date: 2020/8/21 14:37
 */
public interface BaseResultView {

    String ok(AbstractResultView data);

    String include(AbstractResultView data, String[] includeProperties);

    String exclude(AbstractResultView data, String[] excludeProperties);

    String fail(AbstractResultView data);

    default String test(AbstractResultView data, Function<AbstractResultView, String> function){
        Objects.requireNonNull(data);
        return function.apply(data);
    }

}
