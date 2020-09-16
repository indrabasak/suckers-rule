package com.basaki.rules.filter;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.HashSet;
import java.util.Set;

public class RuleFilter implements AgendaFilter {

    private Set<String> rules = new HashSet<>();

    public RuleFilter(Set<String> rules) {
        this.rules.addAll(rules);
    }

    @Override
    public boolean accept(Match match) {
        return rules.contains(match.getRule().getName());
    }
}
