package aidoudong.common.resultview;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/8/21 17:21
 */
public abstract class AbstractFastJsonResultView implements BaseResultView {

	private static final SerializerFeature resultWriteDateUserDateFormat = SerializerFeature.WriteDateUseDateFormat;
	private static final SerializerFeature resultNullFormat = SerializerFeature.WriteMapNullValue;
	private static final SerializerFeature prettyFormat = SerializerFeature.PrettyFormat;

	@Override
	public String ok(AbstractResultView data) {
		SerializeFilter[] serializeFilters = null;
		if(data != null){
			serializeFilters = validCodeMap(data.getCodeMap());
		}
		return jsonFormater(data, serializeFilters);
	}
	
	@Override
	public String include(AbstractResultView data, String[] includeProperties) {
		if(data == null){
			throw new NullPointerException("ResultView is null ...");
		}
		SerializeFilter[] serializeFilters = validResultViewFilter(data, true, includeProperties);
		return jsonFormater(data, serializeFilters);
	}
	
	@Override
	public String exclude(AbstractResultView data, String[] excludeProperties) {
		if(data == null){
			throw new NullPointerException("ResultView is null ...");
		}
		SerializeFilter[] serializeFilters = validResultViewFilter(data, false, excludeProperties);
		return jsonFormater(data,serializeFilters);
	}

	@Override
	public String fail(AbstractResultView data) {
		return jsonFormater(data,null);
	}

	private String jsonFormater(AbstractResultView data, SerializeFilter[] serializeFilters){
		return JSONObject.toJSONString(
				data,
				SerializeConfig.globalInstance,
				serializeFilters,
				data.getDateFormatter(),
				JSONObject.DEFAULT_GENERATE_FEATURE,
				resultWriteDateUserDateFormat,
				prettyFormat,
				resultNullFormat);
	}

	private SerializeFilter[] validCodeMap(Map<String, String> codeMap){
		SerializeFilter[] resultViewFilter = null;
		if(codeMap != null){
			resultViewFilter = getResultViewValueFilter(codeMap);
		}
		return resultViewFilter;
	}

	//includeType : true 包含类型
	//includeType : false 排除类型
	private SerializeFilter[] validResultViewFilter(AbstractResultView resultView, boolean includeType, String[] properties){
		SerializeFilter[] resultViewFilter = null;
		if(properties == null || properties.length == 0){
			if(resultView.getCodeMap() != null) {
				resultViewFilter = getResultViewValueFilter(resultView.getCodeMap());
			}
			return resultViewFilter;
		}
		if(resultView.getCodeMap() != null){
			if(includeType){
				resultViewFilter = getResultViewIncludeFilter(getIfPageInfoIncludeProperties(resultView.getData(), properties), resultView.getCodeMap());
			}else{
				resultViewFilter = getResultViewExcludeFilter(properties, resultView.getCodeMap());
			}
		}else{
			if(includeType){
				resultViewFilter = getResultViewIncludeFilter(getIfPageInfoIncludeProperties(resultView.getData(), properties));
			}else{
				resultViewFilter = getResultViewExcludeFilter(properties);
			}
		}
		return resultViewFilter;
	}
	
	private static SerializeFilter[] getResultViewIncludeFilter(String[] includeProperties) {
		PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter includeFilter = filters.addFilter();
        includeFilter.addIncludes(includeProperties);
        return new SerializeFilter[] {includeFilter};
	}
	
	private static SerializeFilter[] getResultViewExcludeFilter(String[] excludeProperties) {
		PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        return new SerializeFilter[] {excludeFilter};
	}
	
	private SerializeFilter[] getResultViewIncludeFilter(String[] includeProperties, Map<String, String> codeMap) {
		return ArrayUtils.addAll(getResultViewIncludeFilter(includeProperties),
				getResultViewValueFilter(codeMap));
	}
	
	private SerializeFilter[] getResultViewExcludeFilter(String[] excludeProperties, Map<String, String> codeMap) {
		return ArrayUtils.addAll(getResultViewExcludeFilter(excludeProperties),
				getResultViewValueFilter(codeMap));
	}

	public abstract String[] getIfPageInfoIncludeProperties(Object data, String[] includeProperties);

	public abstract SerializeFilter[] getResultViewValueFilter(Map<String, String> codeMap);
	
}
