package com.nativenote.movielist_retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
@Root(strict = false)
public class Channel {

    @Element
    String title;
    @Element
    String language;

    @Element(name = "ttl", required = false)
    int ttl;

    @Element(name = "pubDate", required = false)
    String pubDate;
    @ElementList(entry = "link", inline = true, required = false)
    public List<Link> links;

    @ElementList(name = "item", required = true, inline = true)
    public List<Item> itemList;
}

