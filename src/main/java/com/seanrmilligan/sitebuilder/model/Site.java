package com.seanrmilligan.sitebuilder.model;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sean on 6/18/17.
 */
public class Site {
    private String name;
    private String schema;
    private String domain;
    private int port;
    private HashSet<String> subdomains;

    public Site(String name, String domain) {
    	this(name, "http", domain, 80);
    }

    public Site(String name, String schema, String domain, int port) {
		this.name = name;
		this.schema = schema;
		this.domain = domain;
		this.port = port;
		this.subdomains = new HashSet<>();
	}

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    public void setDomain(String domain) { this.domain = domain; }
    public String getDomain() { return this.domain; }

    public void addSubdomain(String subdomain) {
        this.subdomains.add(subdomain);
    }
    public void remSubdomain(String subdomain) { this.subdomains.remove(subdomain); }
    public List<String> getSubdomains() { return this.subdomains.stream().collect(Collectors.toList()); }
    
    public boolean equals (Site site) {
    	return this.name.equals(site.name) &&
				this.domain.equals(site.domain) &&
				this.subdomains.equals(site.subdomains);
	}
}
