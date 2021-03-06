package com.aidoudong.common.result;

import aidoudong.common.resultview.AbstractFastJsonResultView;
import com.aidoudong.common.utils.DictionaryCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FastJsonResultView extends AbstractFastJsonResultView {

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
	private static final String jsonObjectType = "JSONObject";
	private static final String jsonArrayType = "JSONOArray";
	private static final String pageMybatisPlusType = "MybatisPlusPage";
	private static final String pageJpaType = "JpaPage";
	private static final String resultConvertCode = "code";

	public String data(ResultView data) {
		return super.data(data);
	}

	public String codeMap(ResultView data) {
		return super.codeMap(data);
	}

	public String include(ResultView data, String[] includeProperties) {
		return super.include(data, includeProperties);
	}

	public String exclude(ResultView data, String[] excludeProperties) {
		return super.exclude(data, excludeProperties);
	}

	@Override
	public String[] getIfPageInfoIncludeProperties(Object data, String[] includeProperties) {
		List<String> resultViewIncludePropertiesList;
		if(data instanceof org.springframework.data.domain.Page) {
			resultViewIncludePropertiesList = Stream.of(resultViewJpaPageIncludeProperties).collect(Collectors.toList());
		}else if(data instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page){
			resultViewIncludePropertiesList = Stream.of(resultViewMybatisPlusPageIncludeProperties).collect(Collectors.toList());
		}else{
			resultViewIncludePropertiesList = Stream.of(resultViewIncludeProperties).collect(Collectors.toList());
		}
		CollectionUtils.addAll(resultViewIncludePropertiesList, includeProperties);
		return resultViewIncludePropertiesList.toArray(new String[0]);
	}

	@Override
	public SerializeFilter[] getResultViewValueFilter(Map<String, String> codeMap) {
		if(codeMap == null || codeMap.isEmpty()) return null;
		ValueFilter valueFilter = (object, name, value) -> {
			if("data".equals(name)){
				if(null == value) {
					return null;
				}
				if(value instanceof org.springframework.data.domain.Page){
					JSONObject jsonPage = (JSONObject) JSON.toJSON(value);
					jsonPage.put("content", codeCovert(jsonPage.get("content"),codeMap));
					value = jsonPage;
				}else if(value instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page){
					JSONObject jsonPage = (JSONObject) JSON.toJSON(value);
					jsonPage.put("records", codeCovert(jsonPage.get("records"),codeMap));
					value = jsonPage;
				}else{
					value = codeCovert(value,codeMap);
				}
			}
			return value;
		};
		return new SerializeFilter[] {valueFilter};
	}

	private Object codeCovert(Object data,Map<String, String> code) {
		String type = this.validResultType(data);
		if(pageMybatisPlusType.equals(type)) { // mybatis-plus
			JSONObject pageJson = (JSONObject) JSONObject.toJSON(data);
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(pageJson.get("records"));
			for (Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
				this.handlerResultCode(jsonObject, code);
			}
			data = jsonArray;
		}else if(pageJpaType.equals(type)) { // jpa
			JSONObject pageJson = (JSONObject) JSONObject.toJSON(data);
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(pageJson.get("content"));
			for (Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
				this.handlerResultCode(jsonObject, code);
			}
			data = jsonArray;
		}else if(jsonArrayType.equals(type)) { // array
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(data);
			for (Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
				this.handlerResultCode(jsonObject, code);
			}
			data = jsonArray;
		}else if(jsonObjectType.equals(type)){ // map
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(data);
			this.handlerResultCode(jsonObject,code);
			data = jsonObject;
		}
		return data;
	}

	private void handlerResultCode(JSONObject json,Map<String, String> codeMap) {
		for (Map.Entry<String, String> entry : codeMap.entrySet()) {
			if(json.containsKey(entry.getKey())){
				if(!json.containsKey(resultConvertCode)) {
					json.put(resultConvertCode, new JSONObject());
				}
				JSONObject codeJson = (JSONObject) json.get(resultConvertCode);
				codeJson.put(entry.getKey(), DictionaryCodeUtil.getValue(entry.getValue(), json.getString(entry.getKey())));
			}
		}
	}

	private String validResultType(Object object) {
		if(object instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page) {
			return pageMybatisPlusType;
		}else if(object instanceof org.springframework.data.domain.Page) {
			return pageJpaType;
		}
		String resultStr;
		String firstStr = object.toString().trim().split("")[0];
		if("[".equals(firstStr)) {
			resultStr = jsonArrayType;
		}else if("{".equals(firstStr)) {
			resultStr = jsonObjectType;
		}else {
			resultStr = jsonObjectType;
		}
		return resultStr;
	}

}
