package com.seanrmilligan.sitebuilder.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by sean on 6/18/17.
 */
public class Site {
    private String name;
    private String domain;
    private HashSet<String> subdomains;

    public Site(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public void addSubdomain(String subdomain) {
        this.subdomains.add(subdomain);
    }
}
