package aidoudong.common.resultview;

import java.util.Objects;
import java.util.function.Function;

/**
 * @Author: dlus91
 * @Date: 2020/8/21 14:37
 */
public interface BaseResultView {

    String data(AbstractResultView data);

    String codeMap(AbstractResultView data);

    String include(AbstractResultView data, String[] includeProperties);

    String exclude(AbstractResultView data, String[] excludeProperties);

    String output(AbstractResultView data, Function<AbstractResultView, String> function);

}
