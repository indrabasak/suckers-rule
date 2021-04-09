package com.basaki.rules.model.biz;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RuleEvent {

    private Payload payload;

    private Map<String, Map<String, String>> ruleToParamMap =  new HashMap<>();

    public String getParamValue(String rule, String paramName) {
        if (ruleToParamMap.containsKey(rule)) {
            return ruleToParamMap.get(rule).get(paramName);
        }

        return null;
    }
}
