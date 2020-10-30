package com.aidoudong.common.result;

import aidoudong.common.resultview.AbstractFastJsonResultView;
import aidoudong.common.resultview.AbstractResultView;
import com.aidoudong.common.utils.DictionaryCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@Component
public class FastJsonResultView extends AbstractFastJsonResultView {

	@Autowired
	private DictionaryCodeUtil dictionaryCodeUtil;

//	private static final String[] resultViewMybatisPageIncludeProperties = new String[] {
//		"code","message","data","list","pageNum","pageSize","size","startRow","endRow","pages","prePage",
//		"nextPage","isFirstPage","isLastPage","hasPreviousPage","hasNextPage","navigatePages","navigatepageNums",
//		"navigateFirstPage","navigateLastPage"
//	};

	private static final String[] resultViewIncludeProperties = new String[] {
			"code","message","data"
	};
	private static final String[] resultViewMybatisPlusPageIncludeProperties = new String[] {
			"code","message","data","records","total","size","current","searchCount","pages"
	};
	private static final String[] resultViewJpaPageIncludeProperties = new String[] {
			"code","message","data","content","first","last","number",
			"numberOfElements","pageable","offset","pageNumber","pageSize","paged","sort","sorted","unsorted","unpaged","size","sort","$ref","totalElements","totalPages"
	};

	@Override
	public String ok(AbstractResultView data) {
		return super.ok(data);
	}

	@Override
	public String include(AbstractResultView data, String[] includeProperties) {
		return super.include(data, includeProperties);
	}

	@Override
	public String exclude(AbstractResultView data, String[] excludeProperties) {
		return super.exclude(data, excludeProperties);
	}

	@Override
	public String fail(AbstractResultView data) {
		return super.fail(data);
	}

	@Override
	public String[] getIfPageInfoIncludeProperties(Object data, String[] includeProperties) {
		List<String> resultViewIncludePropertiesList = new ArrayList<String>();
		if(data instanceof org.springframework.data.domain.Page) {
			resultViewIncludePropertiesList = Stream.of(resultViewJpaPageIncludeProperties).collect(Collectors.toList());
		}else if(data instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page){
			resultViewIncludePropertiesList = Stream.of(resultViewMybatisPlusPageIncludeProperties).collect(Collectors.toList());
		}else{
			resultViewIncludePropertiesList = Stream.of(resultViewIncludeProperties).collect(Collectors.toList());
		}
		CollectionUtils.addAll(resultViewIncludePropertiesList, includeProperties);
		return resultViewIncludePropertiesList.stream().toArray(String[]::new);
	}

	@Override
	public SerializeFilter[] getResultViewValueFilter(Map<String, String> codeMap) {
		if(codeMap == null || codeMap.isEmpty()) return null;
		ValueFilter valueFilter = new ValueFilter() {
			@Override
            public Object process(Object object, String name, Object value) {
                if("data".equals(name)){
                	if(null == value) {
                		return value;
                	}
                	if(value instanceof org.springframework.data.domain.Page){
            			JSONObject jsonPage = (JSONObject) JSON.toJSON(value);
            			jsonPage.put("content", (List<?>) dictionaryCodeUtil.codeCovert(jsonPage.get("content"),codeMap));
            			value = jsonPage;
            		}else if(value instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page){
            			JSONObject jsonPage = (JSONObject) JSON.toJSON(value);
            			jsonPage.put("records", (List<?>) dictionaryCodeUtil.codeCovert(jsonPage.get("records"),codeMap));
            			value = jsonPage;
                	}else{
            			value = dictionaryCodeUtil.codeCovert(value,codeMap);
            		}
                }
                return value;
            }
        };
		return new SerializeFilter[] {valueFilter};
	}
}
